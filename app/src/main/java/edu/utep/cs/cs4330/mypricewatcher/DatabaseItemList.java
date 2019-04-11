package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

public class DatabaseItemList extends ItemList{

    private ItemDatabaseHelper itemDatabaseHelper;

    //private static ItemList listInstance = null;
    //private ArrayList<Item> list;


    public DatabaseItemList(){
        ItemList.getInstance();
    }

    public DatabaseItemList(Context ctx) {
        super.getInstance();
        itemDatabaseHelper = new ItemDatabaseHelper(ctx);
        this.list = itemDatabaseHelper.allItems();
        //itemDatabaseHelper.allItems();
    }


    public static ItemList getInstance(){

        return ItemList.getInstance();

    }


    public ArrayList<Item> getList(){
        return super.getList();//ItemList.getInstance().getList();
    }


    @Override
    public void add(Item i){
        super.add(i);//list.add(i);

        DatabaseItem item = itemDatabaseHelper.addItem(i.getName(), i.getInitPrice(),
                i.getCurPrice(), i.getUrl());
        //if (item != null) {
        //    super.addItem(item);
        //}
        //return item;
    }


    public Item get(int pos){
        return super.get(pos);//list.get(pos);
    }


    public void remove(Item i){
        super.remove(i);//list.remove(i);
    }


    public void findNewPrices(){
        super.findNewPrices();
        /*for(int i = 0; i < this.list.size(); i++){
            Item item = this.list.get(i);
            item.findNewPrice();
            item.setPercentageOff();
        }*/
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
