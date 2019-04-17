/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date April 17, 2019
 **
 **   My Price Watcher
 **
 **   This ItemDatabaseHelper class is the class that will help my app use a SQLiteDatabase. The
 **   database is created in this class. This class also provides an add, remove, and update methods
 **   for items. There is also a method, allItems, that returns all the items currently in the
 **   database.
 **/

package edu.utep.cs.cs4330.mypricewatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class ItemDatabaseHelper extends SQLiteOpenHelper {
        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "pricewatcherDB";
        private static final String ITEM_TABLE = "items";

        private static final String KEY_ID = "_id";
        private static final String KEY_NAME = "name";
        private static final String KEY_INITIAL_PRICE = "initial_price";
        private static final String KEY_CURRENT_PRICE = "current_price";
        private static final String KEY_URL = "url";

        public ItemDatabaseHelper(Context context) {
            super (context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE " + ITEM_TABLE + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME + " TEXT, "
                    + KEY_INITIAL_PRICE + " FLOAT, "
                    + KEY_CURRENT_PRICE + " FLOAT, "
                    + KEY_URL + " TEXT" + ")";
            db.execSQL(sql);
        }

        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            database.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE);
            onCreate(database);
        }

    /**
     * Adds an item to the database.
     * @param name Name of the item
     * @param initialPrice Initial price of the item
     * @param currentPrice Current price of the item
     * @param url Url of the item
     * @return A DatabaseItem made from the item added. This returned value will include the id.
     */
        public DatabaseItem addItem(String name, double initialPrice, double currentPrice, String url){
            DatabaseItem item;

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, name);
            values.put(KEY_INITIAL_PRICE, initialPrice);

            values.put(KEY_CURRENT_PRICE, currentPrice);
            values.put(KEY_URL, url);

            long id = db.insert(ITEM_TABLE, null, values);

            //item = new DatabaseItem(id, name, initialPrice, url);

            db.close();

            item = new DatabaseItem(id, name, initialPrice,url);
            return item;
        }

    /**
     * Remove the given item from the database. Done by using the item's id.
     * @param item Item to remove
     */
    public void removeItem(DatabaseItem item){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(ITEM_TABLE,
                    KEY_ID + " = ?",
                    new String[]{Long.toString(item.getId())});
            db.close();
        }

    /**
     * Update the item values in the database.
     * @param item
     */
    public void updateItem(DatabaseItem item){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, item.name);
            values.put(KEY_CURRENT_PRICE, item.getCurPrice());
            values.put(KEY_INITIAL_PRICE, item.getInitPrice());
            values.put(KEY_URL, item.getUrl());

            db.update(ITEM_TABLE,
                    values,
                    KEY_ID + " = ?",
                    new String[] {Long.toString(item.getId())});
            db.close();
        }

    /**
     * Get all items currently in the database and return them as an ItemList.
     * @return A list of items
     */
    public ArrayList<Item> allItems(){
            ArrayList<Item> itemList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + ITEM_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    double initialPrice = cursor.getDouble(2);
                    double currentPrice = cursor.getDouble(3);
                    String url = cursor.getString(4);

                    DatabaseItem task = new DatabaseItem(id, name, initialPrice, currentPrice, url);
                    itemList.add(task);
                } while (cursor.moveToNext());
            }
            return itemList;
        }
}
