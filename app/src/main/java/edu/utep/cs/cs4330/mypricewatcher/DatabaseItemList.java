/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date April 17, 2019
 **
 **   My Price Watcher
 **
 **   This DatabaseItemList class is an extension of ItemList. It provides necessary features
 **   to be able to use a SQLiteDatabase with this app. Mostly, the new methods will interact
 **   with a ItemDatabaseHelper in some way. The singleton aspect of ItemList is retained in this
 **   class.
 **/

package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseItemList extends ItemList{

    final static String TAG = "DatabaseList";

    private static DatabaseItemList listInstance;
    private static ItemDatabaseHelper itemDatabaseHelper;

    /**
     * Private constructor for this singleton class. Initializes 5 sample items to begin with.
     * @param ctx Context needed for the ItemDatabaseHelper.
     */
    private DatabaseItemList(Context ctx) {
        list = new ArrayList<>();
        Log.d(TAG, "Constructor called");

        if(itemDatabaseHelper == null)
            itemDatabaseHelper = new ItemDatabaseHelper(ctx);

        ///// Get all items already in the database
        list = itemDatabaseHelper.allItems();

        if(this.list.size() <= 0) {
            Log.d(TAG, "Constructing new list");
            Item broom = new Item("Broom", 29.97, "https://www.amazon.com/Cedar-Heavy-Commercial-Broom-Handle/dp/B0106FW42U/?th=1");
            Item mop = new Item("Mop", 24.99, "https://www.amazon.com/Cedar-Commercial-Grade-Heavy-Looped-End-String/dp/B01BX7JKRC/");
            Item table = new Item("Table", 20.87, "https://www.amazon.com/Furinno-11180GYW-BK-Simple-Design/dp/B01COV5A20/");
            Item chair = new Item("Chair", 34.85, "https://www.amazon.com/Bathroom-Safety-Shower-Bench-Chair/dp/B002VWK0WI/");
            Item tv = new Item("Television", 896.99, "https://www.amazon.com/LG-Electronics-65SK8000PUA-65-Inch-Ultra/dp/B079TT1RM1/");

            add(broom);
            add(mop);
            add(table);
            add(chair);
            add(tv);
        }

    }

    /**
     * Gets the single object instance of this class. (Singleton). Creates it if it isn't already.
     * @param ctx Context for initializing the ItemDatabaseHelper
     * @return Singleton DatabaseItemList
     */
    public static DatabaseItemList getInstance(Context ctx){
        if(listInstance == null){
            listInstance = new DatabaseItemList(ctx);
        }
        return listInstance;

    }

    /**
     * Adds item to the list as well as to the database
     * @param i item to add
     */
    @Override
    public void add(Item i){
        DatabaseItem item = itemDatabaseHelper.addItem(i.getName(), i.getInitPrice(),
                i.getCurPrice(), i.getUrl());

        list.add(item);
    }

    /**
     * Gets item at the given position
     * @param pos position of the item desired
     * @return Item at the given position
     */
    public Item get(int pos){
        return list.get(pos);//list.get(pos);
    }

    /**
     * Removes the item from the list as well as from the database
     * @param pos Position of the item to be removed
     */
    public void remove(int pos){
        DatabaseItem item = (DatabaseItem) list.get(pos);

        list.remove(item);

        itemDatabaseHelper.removeItem(item);
    }

    /**
     * Update the item in the database
     * @param i Item to be updated
     */
    public void updateItem(Item i){
        itemDatabaseHelper.updateItem((DatabaseItem) i);
    }

    /**
     * Finds new prices for all of the items on the list. It also updates their values in the
     * database.
     */
    public void findNewPrices(){
        super.findNewPrices();
        for(int i = 0; i < this.list.size(); i++){
            DatabaseItem item = (DatabaseItem) this.list.get(i);
            itemDatabaseHelper.updateItem(item);

        }
    }




}
