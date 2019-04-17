/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date April 17, 2019
 **
 **   My Price Watcher
 **
 **   This item class is the class that will hold the information of an item. Aside from the
 **   getters and setters, there is a method for finding the new price of the item that uses the
 **   PriceFinder class.
 **/

package edu.utep.cs.cs4330.mypricewatcher;

import android.widget.Button;
import android.widget.TextView;


public class Item {
    protected String name;
    protected double currentPrice;
    protected double initialPrice;
    protected String URL;
    protected double percentageOff;

    /**
     * Default Constructor
     */
    public Item(){
        this.name = "Broom";
        this.currentPrice = 29.97;
        this.initialPrice = 29.97;
        this.URL = "https://www.amazon.com/Cedar-Heavy-Commercial-Broom-Handle/dp/B0106FW42U/ref=sr_1_8_a_it?ie=UTF8&qid=1550354047&sr=8-8&keywords=broom&th=1";
    }

    /**
     * Constructor that receives the name and url of the item. Price will now be found using a PriceFinder.
     * @param n name of the item
     * @param url url of the item
     */
    public Item(String n, String url){
        this.name = n;
        this.URL = url;

        PriceFinder finder = new PriceFinder();

        initialPrice = finder.findPrice(URL);
        if(initialPrice < 0){
            initialPrice = Double.POSITIVE_INFINITY;
        }
        currentPrice = initialPrice;
        percentageOff = Double.POSITIVE_INFINITY;

    }
    /**
     * Constructor that receives the name, initial price, and url of the item. Automatically calculates percentage off.
     * @param n name of the item
     * @param i initial price of the item
     * @param url url of the item
     */
    public Item(String n, double i, String url){
        this.name = n;
        this.initialPrice = i;
        this.currentPrice = this.initialPrice;
        this.URL = url;
        this.percentageOff = calcPercentageOff();
    }

    public Item(String n, double i, double c, String url){
        this.name = n;
        this.initialPrice = i;
        this.currentPrice = c;
        this.URL = url;
        this.percentageOff = calcPercentageOff();
    }

    public String getName(){
        return this.name;
    }
    public double getCurPrice(){
        return this.currentPrice;
    }
    public double getInitPrice(){
        return this.initialPrice;
    }
    public String getUrl(){
        return this.URL;
    }
    public double getPercentageOff(){
        return this.percentageOff;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setInitPrice(double newPrice){
        this.initialPrice = newPrice;
    }
    public void setCurPrice(double newPrice){
        this.currentPrice = newPrice;
    }
    public void setURL(String newUrl){
        this.URL = newUrl;
    }
    public void setPercentageOff(){
        this.percentageOff = calcPercentageOff();
    }

    /**
     * Calculates the percentage difference between the initial price and the current price.
     * @return the percentage off
     */
    public double calcPercentageOff(){
        return (100.00 - (this.currentPrice/this.initialPrice) * 100);
    }

    /**
     * Find the current price of the item. Uses a PriceFinder to get the price. If the given price
     * was less than 0, then no price was found, in which case, no new price is set.
     */
    public void findNewPrice(){
        PriceFinder finder = new PriceFinder();
        double newPrice = finder.findPrice(URL);
        if(newPrice >= 0){
            currentPrice = newPrice;
            setPercentageOff();
        }

    }
}
