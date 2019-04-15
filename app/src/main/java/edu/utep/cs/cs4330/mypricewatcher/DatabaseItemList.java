package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class DatabaseItemList extends ItemList{

    final static String TAG = "DatabaseList";

    private static DatabaseItemList listInstance;
    private static ItemDatabaseHelper itemDatabaseHelper;

    //private static ItemList listInstance = null;
    //private ArrayList<Item> list;
    //private ArrayList<DatabaseItem> list;

    private DatabaseItemList(Context ctx) {
        //super();
        list = new ArrayList<>();
        Log.d(TAG, "Constructor called");

        //ItemList.getInstance();
        if(itemDatabaseHelper == null)
            itemDatabaseHelper = new ItemDatabaseHelper(ctx);
        //if(ItemList.getInstance().getList().size() <= 0)
            list = itemDatabaseHelper.allItems();
        //itemDatabaseHelper.allItems();
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


    public static DatabaseItemList getInstance(Context ctx){
        if(listInstance == null){
            listInstance = new DatabaseItemList(ctx);
            //itemDatabaseHelper = new ItemDatabaseHelper(ctx);
        }
        return listInstance;

    }


    /*public ArrayList<Item> getList(){
        return super.getList();//ItemList.getInstance().getList();
    }*/


    @Override
    public void add(Item i){
        //super.add(i);//list.add(i);

        DatabaseItem item = itemDatabaseHelper.addItem(i.getName(), i.getInitPrice(),
                i.getCurPrice(), i.getUrl());

        list.add(item);
        //if (item != null) {
        //    super.addItem(item);
        //}
        //return item;
    }


    public Item get(int pos){
        return list.get(pos);//list.get(pos);
    }

    //@Override
    public void remove(int pos){
        DatabaseItem item = (DatabaseItem) list.get(pos);

        //super.remove(item);
        list.remove(item);

        itemDatabaseHelper.removeItem(item);
    }


    public void findNewPrices(){
        super.findNewPrices();
        for(int i = 0; i < this.list.size(); i++){
            DatabaseItem item = (DatabaseItem) this.list.get(i);
            itemDatabaseHelper.updateItem(item);
            item.findNewPrice();
            item.setPercentageOff();
        }
    }


    public void sortByName(){
        super.sortByName();//Collections.sort(list, (o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
    }


    public void sortByCurrentPrice(){
        super.sortByCurrentPrice();
        /*Collections.sort(list, (o1, o2) -> {
            if(o1.getCurPrice() > o2.getCurPrice())
                return 1;
            else if(o1.getCurPrice() == o2.getCurPrice())
                return 0;
            return -1;
        });*/
    }


    public void sortByPercentage(){
        super.sortByPercentage();
        /*Collections.sort(list, (o1, o2) -> {
            if(o1.getPercentageOff() > o2.getPercentageOff())
                return -1;
            else if(o1.getPercentageOff() == o2.getPercentageOff())
                return 0;
            return 1;
        });*/
    }


}
