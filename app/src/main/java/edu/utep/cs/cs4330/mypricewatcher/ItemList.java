package edu.utep.cs.cs4330.mypricewatcher;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    private static ItemList listInstance = null;
    private static List<Item> items;


    private ItemList(){
        items = new ArrayList<>();
    }

    public static ItemList getInstance(){
        if(listInstance == null)
            listInstance = new ItemList();
        return listInstance;
    }

    public List<Item> getList(){
        if(listInstance == null)
            listInstance = new ItemList();
        return listInstance.getList();
    }

}
