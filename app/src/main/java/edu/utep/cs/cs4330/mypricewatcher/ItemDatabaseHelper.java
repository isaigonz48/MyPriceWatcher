package edu.utep.cs.cs4330.mypricewatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        public DatabaseItem addItem(String name, double initialPrice, double currentPrice, String url){
            DatabaseItem item;
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, name);
            values.put(KEY_INITIAL_PRICE, initialPrice);

            values.put(KEY_CURRENT_PRICE, currentPrice);
            values.put(KEY_URL, url);

            long id = db.insert(ITEM_TABLE, null, values);
            db.close();

            item = new DatabaseItem(id, name, initialPrice,url);
            return item;
        }

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

                    Item task = new Item(name, initialPrice, currentPrice, url);
                    itemList.add(task);
                } while (cursor.moveToNext());
            }
            return itemList;
        }
}
