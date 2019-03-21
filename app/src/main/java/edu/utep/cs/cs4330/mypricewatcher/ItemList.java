package edu.utep.cs.cs4330.mypricewatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemList{
    //private static ItemList list = null;
    //private static ItemList items;

    private static ItemList listInstance = null;
    private ArrayList<Item> list;

    private ItemList(){
        list = new ArrayList<>();
    }

    public static ItemList getInstance(){
        if(listInstance == null){
            listInstance = new ItemList();
        }
        return listInstance;
    }

    public ArrayList<Item> getList(){
        return this.list;
    }

    public void add(Item i){
        list.add(i);
    }

    public Item get(int pos){
        return list.get(pos);
    }

    public void remove(Item i){
        list.remove(i);
    }

    public void findNewPrices(){
        for(int i = 0; i < this.list.size(); i++){
            Item item = this.list.get(i);
            item.findNewPrice();
            item.setPercentageOff();
        }
    }

    public void sortByName(){
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {

                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void sortByCurrentPrice(){
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.getCurPrice() > o2.getCurPrice())
                    return 1;
                return -1;
            }
        });
    }

    public void sortByPercentage(){
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.getPercentageOff() > o2.getPercentageOff())
                    return -1;
                return 1;
            }
        });
    }


}
