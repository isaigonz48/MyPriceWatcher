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
        double price = -1;

        /*URL url = new URL("url");
        URLConnection conn = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setAllowUserInteraction(false);
        httpConn.setInstanceFollowRedirects(true);
        httpConn.setRequestMethod("GET");
        httpConn.connect();
        if (HttpURLConnection.HTTP_OK == httpConn.getResponseCode()) {
            InputStream in = httpConn.getInputStream();
            String line;
            while(line = in.read() != null){

            }
            //... read from in ...
        }*/
        String uurl = "https://www.amazon.com/gp/product/B077ZGRY9V/ref=ppx_yo_dt_b_asin_title_o00_s00?ie=UTF8&psc=1";
        //String uurl = "https://www.homedepot.com/p/Weber-Spirit-II-E-310-3-Burner-Propane-Gas-Grill-in-Black-45010001/302996388";
        //Main main = new Main();
        String text = "";
        try {
            Document doc = Jsoup.connect(uurl).get();
            text = doc.text();


        }catch(IOException e){ e.printStackTrace();}

        Log.d("NETWORKTEST", text);

        /*try (BufferedReader reader = openUrl(uurl, USER_AGENT)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d("NETWORKTEST", line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/


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
        if (userAgent != null) {
            con.setRequestProperty("User-Agent", userAgent);
        }
        // required by BestBuy website.
        String encoding = con.getContentEncoding();
        if (encoding == null) {
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
}


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button2;
    private Button button;
    private String text;
    private static final String url = "https://www.amazon.com/dp/B07CLPC2B9/ref=sspa_dk_detail_1?psc=1";
    //private static final String url = "https://www.amazon.com/gp/product/B077ZGRY9V/ref=ppx_yo_dt_b_asin_title_o00_s00?ie=UTF8&psc=1";
    //private static final String url = "https://www.homedepot.com/p/Weber-Spirit-II-E-310-3-Burner-Propane-Gas-Grill-in-Black-45010001/302996388";


    protected static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        String uurl = "https://www.homedepot.com/p/Weber-Spirit-II-E-310-3-Burner-Propane-Gas-Grill-in-Black-45010001/302996388";

        /*
        URL url = new URL("http://www.cs.utep.edu/cheon/cs4330");
        URLConnection conn = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setAllowUserInteraction(false);
        httpConn.setInstanceFollowRedirects(true);
        httpConn.setRequestMethod("GET");
        httpConn.connect();
        String textt = "";
        if (HttpURLConnection.HTTP_OK == httpConn.getResponseCode()) {
            InputStream in = httpConn.getInputStream();
            in.
        }*/


        //try (BufferedReader reader = openUrl(uurl, USER_AGENT)) {
        //   String line;
        //while ((line = reader.readLine()) != null) {
        //    Log.d("TEST", line);
        //}
        //} catch (IOException e) {
        //  e.printStackTrace();
        //}


        button2.setOnClickListener(view ->{
            textView.setText("chill");
        });

        /*text = "";
        try {
            Document doc = Jsoup.connect(url).get();
            text = doc.text();

        }catch (IOException e){e.printStackTrace();}
*/
        button.setOnClickListener(view ->{
            new TestSyncTask().execute();
        });
    }



    private class TestSyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            text = "";
            /*Log.d("AGGGGG", "starting");

            try {
                Document doc = Jsoup.connect(url).get();
                //InputStream input = new InputStreamReader(doc);
                //BufferedReader reader = new BufferedReader(input);
                String html = doc.html();
                //doc = Jsoup.parse(html);
                //Element link = doc.select("body").first();//html();

                //String linkPrice = link.attr("href");
                //Element tet = doc.getElementById("icItemPrice");
                //String test = tet.id();
                text = html;
                //text = html;
                //doc.htm

            }catch (IOException e){e.printStackTrace();
                Log.d("AGGGGG", "failure");
            }
            text = "";

            try {
                JSONArray JA = new JSONArray(text);
                text = "";
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject JO = (JSONObject) JA.get(i);
                    //text = "Name: " + JO.get("icItemPrice");
                }
            }catch (JSONException e){e.printStackTrace();
            Log.d("AGGGGG", "failure");
            }*/
            Log.d("AGGGGGG", "goinnnnnng");

            try (BufferedReader reader = openUrl(url, USER_AGENT)) {
                Log.d("AGGGGGG", "made it out aliiiiiiiiive");

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] lineSplit = line.split("\"");
                    for(int i = 0; i < lineSplit.length; i++){
                        boolean foundPrice = false;
                        if(lineSplit[i].equals("priceblock_ourprice")) {
                            text += lineSplit[i];
                            foundPrice = true;
                            Log.d("AGGGGG", "wow");
                        }

                        if(foundPrice){
                            Document doc = Jsoup.parse(line);
                            text = doc.body().text();
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

            if(!text.equals("")){

                text = text.substring(1);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("AGGGGGG", "finish");

            Log.d("AGGGGGG", text);

            textView.setText(text);
        }
    }



}