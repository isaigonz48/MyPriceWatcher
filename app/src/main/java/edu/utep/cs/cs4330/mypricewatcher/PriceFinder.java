/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date Feb 20, 2019
 **
 **   My Price Watcher
 **
 **   This pricefinder class is in charge of calculating the price of a given item.
 **   In this version of the app, it just returns a simulated price for the item. In later
 **   versions, the pricefinder will find the price through the url of an item.
 **/

package edu.utep.cs.cs4330.mypricewatcher;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PriceFinder {

    private double price;
    private String url;
    private Item item;

    private int website;

    protected static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";


    /**
     * Simulates a new price for the item given the initial price of the item.
     * @param initialPrice The initial price given
     * @return a simulated price
     */
    public static double simulatePrice(double initialPrice){
        return Math.random() * initialPrice;
    }


    public double findPrice(String url) {
        //DatabaseItemList list = DatabaseItemList.getInstance();

        //this.item = item;
        price = -1;

        this.url = url;
        String site = url.substring(url.indexOf('.')+1);
        site = site.substring(0,site.indexOf('.'));
        if(site.length()>0) {
            Log.d("ONETIME", site);
        }else{
            Log.d("ONETIME", "rip");
            Log.d("ONETIME", url);
        }

        switch(site){
            case "amazon":
                website = 1;
                break;
            case "lowes":
                website = 2;
                break;

        }

        FindPriceThread test = new FindPriceThread();
        Thread thread = new Thread(test);
        thread.start();

        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //Log.d("AGGGG", Double.toString(price));
        return price;
    }
    protected BufferedReader openUrl(String urlString, String userAgent) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //con.setConnectTimeout(timeout);
        con.setRequestMethod("GET");
        con.setInstanceFollowRedirects(true);
        con.setRequestProperty("Accept-Encoding", "gzip");
        con.setRequestProperty("Upgrade-Insecure-Requests", "1");
        Log.d("AGGGGGG", "ezz get it");

        if (userAgent != null) {
            con.setRequestProperty("User-Agent", userAgent);
        }
        Log.d("AGGGGGG", "almost");

        // required by BestBuy website.
        String encoding = con.getContentEncoding();
        if (encoding == null) {
            encoding = "utf-8";
        }
        Log.d("AGGGGGG", "oh foss");

        //con.connect();
        // Amazon sends gzipped documents even if not requested
        //InputStream inputStream = con.getInputStream();

        InputStreamReader reader = null;
        if ("gzip".equals(encoding)) {
            reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));
        } else {
            reader = new InputStreamReader(con.getInputStream(), "utf-8");//encoding);
        }
        Log.d("AGGGGGG", "wooo");

        return new BufferedReader(reader);
    }

    /*private class FindPriceSyncTask extends AsyncTask<Void,Void,Void> {

        private String text;
        @Override
        protected Void doInBackground(Void... voids) {
            text = "";

            Log.d("AGGGGGG", "goinnnnnng");

            try (BufferedReader reader = openUrl(url, USER_AGENT)) {
                Log.d("AGGGGGG", "made it out aliiiiiiiiive");

                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d("AGGGGGG", line);
                    String[] lineSplit = line.split("\"");
                    for(int i = 0; i < lineSplit.length; i++){
                        boolean foundPrice = false;
                        if(website == 1) {

                            if(lineSplit[i].equals("priceblock_ourprice")){
                                //text += lineSplit[i];

                                Document doc = Jsoup.parse(line);
                                text = doc.body().text();
                                if (!text.equals("")) {

                                    text = text.substring(1);
                                }
                                //foundPrice = true;
                                Log.d("AGGGGG", "wow");
                            }
                            if(lineSplit[i].equals("priceblock_ourprice")) {
                                Document doc = Jsoup.parse(line);
                                text = doc.body().text();
                                if (!text.equals("")) {

                                    text = text.substring(1);
                                }
                                //foundPrice = true;
                                Log.d("AGGGGG", "wow");
                            }

                        }else if(website == 2){
                            if(lineSplit[i].equals("price")) {
                                text += lineSplit[i + 2];//foundPrice = true;
                                Log.d("AGGGGG", "wow");
                            }
                        }

                        /*if(foundPrice){
                            Document doc = Jsoup.parse(line);
                            text = doc.body().text();
                            break;
                        }
                    }
                    //Log.d("AGGGGGG","waiiiit");

                    //Log.d("AGGGGGG", line);
                    // break;
                }
            } catch (IOException e) {
                Log.d("AGGGGGG", "uh oh");

                e.printStackTrace();
            }

            /*if(!text.equals("")){

                text = text.substring(1);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("AGGGGGG", "finish");

            Log.d("AGGGGGG", text);

            price = 0;
            if(!text.equals("")){
                price = Double.parseDouble(text);
            }
            //DatabaseItemList list = DatabaseItemList.getInstance();
            //item.setCurPrice(price);
            //list.updateItem(item);

            Log.d("AGGGGGG", Double.toString(price));

            //return price;
            //textView.setText(text);
        }
    }
*/
    private class FindPriceThread implements Runnable{

        @Override
        public void run() {
            String text = "";
            text = "";

            Log.d("AGGGGGG", "goinnnnnng");

            try (BufferedReader reader = openUrl(url, USER_AGENT)) {
                Log.d("AGGGGGG", "made it out aliiiiiiiiive");

                String line;
                boolean done = false;
                while ((line = reader.readLine()) != null && !done) {
                    //Log.d("AGGGGGG", line);
                    String[] lineSplit = line.split("\"");
                    for(int i = 0; i < lineSplit.length; i++){
                        boolean foundPrice = false;
                        if(website == 1) {

                            if(lineSplit[i].equals("priceblock_ourprice")){
                                //text += lineSplit[i];

                                Document doc = Jsoup.parse(line);
                                text = doc.body().text();
                                if (!text.equals("")) {

                                    text = text.substring(1);
                                }
                                //foundPrice = true;
                                Log.d("AGGGGG", "wow");
                            }else if(lineSplit[i].equals("priceblock_dealprice")) {
                                Document doc = Jsoup.parse(line);
                                text = doc.body().text();
                                if (!text.equals("")) {

                                    text = text.substring(1);
                                }
                                //foundPrice = true;
                                Log.d("AGGGGG", "wow");
                            }

                        }else if(website == 2){
                            if(lineSplit[i].equals("price")) {
                                text += lineSplit[i + 2];//foundPrice = true;
                                Log.d("AGGGGG", "wow");
                            }
                        }

                        /*if(foundPrice){
                            Document doc = Jsoup.parse(line);
                            text = doc.body().text();
                            break;
                        }*/
                    }
                    //Log.d("AGGGGGG","waiiiit");

                    //Log.d("AGGGGGG", line);
                    // break;
                }
            } catch (IOException e) {
                Log.d("AGGGGGG", "uh oh");

                e.printStackTrace();
            }

            /*if(!text.equals("")){

                text = text.substring(1);
            }*/
            price = 0;
            if(!text.equals("")){
                price = Double.parseDouble(text);
            }
            Log.d("AGGGGGG", Double.toString(price));

        }
    }
}




