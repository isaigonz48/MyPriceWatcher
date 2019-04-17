/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date April 17, 2019
 **
 **   My Price Watcher
 **
 **   This pricefinder class is in charge of finding the new price of an item by using its url.
 **   there is also a method for checking if the url given is valid and supported by the app.
 **   In this version of the app. Only Amazon, HomeDepot, and Walmart are supported.
 **   Disclaimer: Not all Amazon items seem to work. Some special brands or something have different
 **   formatting.
 **/

package edu.utep.cs.cs4330.mypricewatcher;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PriceFinder {

    private static final String TAG = "PriceFinder";

    private double price;
    private String url;

    private int website;

    ///// From code given by Dr. Cheon
    protected static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";


    /**
     * Checks the given url to see if it is valid and if it is supported by this app.
     * @param url Url to check
     * @return Less than 0 for invalid, 0 for not supported. Otherwise the assigned number of the website.
     */
    public static int validateUrl(String url){

        ///// Only https:// was given
        if(url.length() < 5){
            Log.d(TAG, "Url invalid");
            return -1;
        }
        ///// Gibberish after the https://
        if(!url.contains(".")){
            Log.d(TAG, "Url invalid");

            return -1;
        }
        String site = url.substring(url.indexOf('.')+1);

        ///// Only one period in the url
        if(!site.contains(".")){
            Log.d(TAG, "Url invalid");

            return -1;
        }

        site = site.substring(0,site.indexOf('.'));

        ///// Nothing in between periods
        if(site.length()>0) {
            Log.d(TAG, site);
        }else{
            Log.d(TAG, "Url invalid");
            return -1;
        }

        ///// Supported sites
        switch(site){
            case "amazon":
                return 1;
            case "homedepot":
                return 2;
            case "walmart":
                return 3;
        }
        return 0;
    }

    /**
     * Simulates a new price for the item given the initial price of the item.
     * @param initialPrice The initial price given
     * @return a simulated price
     */
    public static double simulatePrice(double initialPrice){
        return Math.random() * initialPrice;
    }


    /**
     * Finds the price of an item by searching through the url. Creates a background thread to do so.
     * @param u Url of the item
     * @return Price found
     */
    public double findPrice(String u) {

        price = -1;
        website = -1;
        this.url = u;
        website = validateUrl(url);

        if(website <= 0){
            Log.d(TAG, "Url not supported");
            return - 1;
        }

        ///// New thread for finding the price
        FindPriceThread test = new FindPriceThread();
        Thread thread = new Thread(test);
        thread.start();

        ///// Pauses the main thread until a price is found
        ///// Very inefficient. Can and should be improved
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return price;
    }

    /**
     * Method given by Dr. Cheon. Creates a BufferedReader to go through the html of a website
     * @param urlString Url to connect to
     * @param userAgent
     * @return BufferedReader for the website
     * @throws IOException
     */
    protected BufferedReader openUrl(String urlString, String userAgent) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //con.setConnectTimeout(timeout);
        con.setRequestMethod("GET");
        con.setInstanceFollowRedirects(true);
        con.setRequestProperty("Accept-Encoding", "gzip");
        con.setRequestProperty("Upgrade-Insecure-Requests", "1");

        ///// Seemed to not work for HomeDepot
        if (userAgent != null && website != 2) {
            con.setRequestProperty("User-Agent", userAgent);
        }

        // required by BestBuy website.
        ///// In case a later added website does not support this. Like the above code was not
        ///// supported by HomeDepot
        String encoding;
        if(website == 1 || website == 2 || website == 3) {
            encoding = con.getContentEncoding();
            if (encoding == null) {
                encoding = "utf-8";
            }
        }else{
            encoding = "utf-8";

        }

        // Amazon sends gzipped documents even if not requested

        InputStreamReader reader = null;
        if ("gzip".equals(encoding)) {
            reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));
        } else {
            reader = new InputStreamReader(con.getInputStream(), encoding);
        }

        return new BufferedReader(reader);
    }

    /**
     * Private Thread class for finding the price of an item.
     */
    private class FindPriceThread implements Runnable{

        @Override
        public void run() {
            String text = "";
            text = "";

            ///// Get a BufferedReader for the website
            try (BufferedReader reader = openUrl(url, USER_AGENT)) {
                Log.d(TAG, "Buffered Reader successful");

                String line;
                boolean done = false;

                ///// Go until BufferedReader is empty or the price has been found
                while ((line = reader.readLine()) != null && !done) {

                    ///// Used different techniques depending on the website
                    String[] lineSplit = null;
                    ///// Amazon
                    if(website == 1)
                        lineSplit = line.split("\"");
                    ///// HomeDepot and Walmart
                    else if(website == 2 || website == 3)
                        lineSplit = line.split(" ");
                    for(int i = 0; i < lineSplit.length; i++){
                        if(website == 1) {
                            ///// Found that a lot of items in Amazon have this for their price
                            if(lineSplit[i].equals("priceblock_ourprice")){
                                //text += lineSplit[i];

                                ///// Used Jsoup to parse through the line of html to find what I needed.
                                Document doc = Jsoup.parse(line);
                                String[] docBody = doc.body().text().split(" ");
                                text = docBody[0];
                                if (!text.equals("")) {

                                    text = text.substring(1);
                                }
                                //foundPrice = true;
                                done = true;
                                Log.d(TAG, "Found price");
                                break;
                                ///// Or this
                            }else if(lineSplit[i].equals("priceblock_dealprice")) {
                                Document doc = Jsoup.parse(line);
                                text = doc.body().text();
                                if (!text.equals("")) {

                                    text = text.substring(1);
                                }
                                done = true;
                                Log.d(TAG, "Found price");
                                break;
                            }

                        }else if(website == 2 || website == 3){
                            ///// HomeDepot and Walmart were somewhat simpler and similar
                            if(lineSplit[i].equals("itemprop=\"price\"")) {
                                if(i < lineSplit.length - 1) {
                                    text = lineSplit[i + 1];
                                    text = text.substring(text.indexOf('\"')+1);
                                    text = text.substring(0,text.indexOf('\"'));
                                    Log.d(TAG, "Found price");
                                    done = true;
                                }else{
                                    ///// In case some item in these sites have different format
                                    Log.d(TAG, "Unexpected format");

                                }
                                break;
                            }

                        }

                    }

                }
            } catch (IOException e) {
                Log.d(TAG, "Error");

                e.printStackTrace();
            }

            ///// If a valid price was found return it, otherwise return -1
            price = -1;
            if(!text.equals("")){
                try {
                    price = Double.parseDouble(text);
                }catch(NumberFormatException e){
                    price = -1;
                    return;
                }
            }
            Log.d(TAG, ("Price is " + Double.toString(price)));
        }
    }
}




