package edu.utep.cs.cs4330.mypricewatcher;

import java.util.ArrayList;
import java.util.List;

public class ItemList extends ArrayList<Item>{
    //private static ItemList listInstance = null;
    //private static ItemList items;

    public void findNewPrices(){
        for(int i = 0; i < this.size(); i++){
            Item item = this.get(i);
            item.findNewPrice();
            item.setPercentageOff();
        }
    }


}
