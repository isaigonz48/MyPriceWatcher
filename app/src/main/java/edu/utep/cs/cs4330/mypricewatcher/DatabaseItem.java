/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date April 17, 2019
 **
 **   My Price Watcher
 **
 **   This database item class is an extension of the item class. The only new thing this class
 **   has is an id variable. This variable can be used to access its spot in the SQLiteDatabase
 **/

package edu.utep.cs.cs4330.mypricewatcher;

public class DatabaseItem extends Item{
    private long id;

    /**
     * Default Constructor
     */
    public DatabaseItem(){
        super();
    }

    /**
     * Constructor that receives the database id, name, initial price, and url of the item. Calls its super constructor.
     * @param id database id of the item
     * @param n name of the item
     * @param i initial price of the item
     * @param url url of the item
     */
    public DatabaseItem(long id, String n, double i, String url){
        super(n, i, url);
        this.id = id;
    }

    /**
     * Constructor that receives the database id, name, initial price, and url of the item. Calls its super constructor.
     * @param id database id of the item
     * @param n name of the item
     * @param i initial price of the item
     * @param c current price of the item
     * @param url url of the item
     */
    public DatabaseItem(long id, String n, double i, double c, String url){
        super(n, i, c, url);
        this.id = id;
    }

    public long getId(){
        return this.id;
    }
}
