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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class PriceFinder {

    /**
     * Simulates a new price for the item given the initial price of the item.
     * @param initialPrice The initial price given
     * @return a simulated price
     */
    public static double simulatePrice(double initialPrice){
        return Math.random() * initialPrice;
    }

    public double findPrice(String url){
        double price = -1;

        URL url = new URL("url");
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
        }



        return price;
    }
}
