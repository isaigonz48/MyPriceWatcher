/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date March 24, 2019
 **
 **   My Price Watcher
 **
 **   The item list class is a Singleton class that holds a list of items. It is a singleton because there is only one list per phone for now.
 **   The standard add, get, and remove functions can be used with this class and then get applied to the actual list.
 **   In addition to those functions, there are sorting functions that use the Collection utility, and a method that finds the new price
 **   for every item in the list. (This only simulates prices for this version)
 **/

package edu.utep.cs.cs4330.mypricewatcher;

import java.util.ArrayList;
import java.util.Collections;

public class ItemList{

    private static ItemList listInstance = null;
    protected ArrayList<Item> list;

    /**
     * Private constructor for the singleton class. When called, it also initializes five items as samples.
     */
    protected ItemList(){
        list = new ArrayList<>();
    }

    /**
     * Gets the single object instance of this class. (Singleton)
     * @return Singleton ItemList
     */
    public static ItemList getInstance(){
        if(listInstance == null){
            listInstance = new ItemList();
        }
        return listInstance;
    }

    /**
     * Gets the actual list of items
     * @return list of items
     */
    public ArrayList<Item> getList(){
        return this.list;
    }

    /**
     * Adds an item to the list
     * @param i item to add
     */
    public void add(Item i){
        list.add(i);
    }

    /**
     * Gets the item in the given position
     * @param pos position of the item desired
     * @return item at the position
     */
    public Item get(int pos){
        return list.get(pos);
    }

    /**
     * Remove the given item from the list
     * @param i item to be removed
     */
    public void remove(Item i){
        list.remove(i);
    }

    /**
     * Finds a new price for all the items in the list. For now only simulates new price.
     */
    public void findNewPrices(){
        for(int i = 0; i < this.list.size(); i++){
            Item item = this.list.get(i);
            item.findNewPrice();
        }
    }

    /**
     * Sort list of items by name
     */
    public void sortByName(){
        Collections.sort(list, (o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
    }

    /**
     * Sort list of items by current price
     */
    public void sortByCurrentPrice(){
        Collections.sort(list, (o1, o2) -> {
            if(o1.getCurPrice() > o2.getCurPrice())
                return 1;
            else if(o1.getCurPrice() == o2.getCurPrice())
                return 0;
            return -1;
        });
    }

    /**
     * Sort list of items by percentage off
     */
    public void sortByPercentage(){
        Collections.sort(list, (o1, o2) -> {
            if(o1.getPercentageOff() > o2.getPercentageOff())
                return -1;
            else if(o1.getPercentageOff() == o2.getPercentageOff())
                return 0;
            return 1;
        });
    }


}
