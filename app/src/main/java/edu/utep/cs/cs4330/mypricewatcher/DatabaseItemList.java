package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

public class DatabaseItemList extends ItemList{

    private ItemDatabaseHelper itemDatabaseHelper;
    //private static ItemList listInstance = null;
    //private ArrayList<Item> list;

    /**
     * Private constructor for the singleton class. When called, it also initializes five items as samples.
     */
    public DatabaseItemList(){
        ItemList.getInstance();
    }
    public DatabaseItemManager(Context ctx) {
        super.getInstance();
        itemDatabaseHelper = new ItemDatabaseHelper(ctx);
        //itemDatabaseHelper.allItems();
    }

    /**
     * Gets the single object instance of this class. (Singleton)
     * @return Singleton ItemList
     */
    public static ItemList getInstance(){

        return ItemList.getInstance();
    }

    /**
     * Gets the actual list of items
     * @return list of items
     */
    public ArrayList<Item> getList(){
        return super.getList();//ItemList.getInstance().getList();
    }

    /**
     * Adds an item to the list
     * @param i item to add
     */
    @Override
    public void add(Item i){
        super.add(i);//list.add(i);
        DatabaseItem item = itemDatabaseHelper.addItem(i.getName(), i.getInitPrice(),
                i.getCurPrice(), i.getUrl());
        //if (item != null) {
        //    super.addItem(item);
        //}
        return item;
    }

    /**
     * Gets the item in the given position
     * @param pos position of the item desired
     * @return item at the position
     */
    public Item get(int pos){
        return super.get(pos);//list.get(pos);
    }

    /**
     * Remove the given item from the list
     * @param i item to be removed
     */
    public void remove(Item i){
        super.remove(i);//list.remove(i);
    }

    /**
     * Finds a new price for all the items in the list. For now only simulates new price.
     */
    public void findNewPrices(){
        super.findNewPrices();
        /*for(int i = 0; i < this.list.size(); i++){
            Item item = this.list.get(i);
            item.findNewPrice();
            item.setPercentageOff();
        }*/
    }

    /**
     * Sort list of items by name
     */
    public void sortByName(){
        super.sortByName();//Collections.sort(list, (o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
    }

    /**
     * Sort list of items by current price
     */
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

    /**
     * Sort list of items by percentage off
     */
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
