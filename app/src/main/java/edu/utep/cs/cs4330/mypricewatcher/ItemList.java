package edu.utep.cs.cs4330.mypricewatcher;

import java.util.ArrayList;
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


}
