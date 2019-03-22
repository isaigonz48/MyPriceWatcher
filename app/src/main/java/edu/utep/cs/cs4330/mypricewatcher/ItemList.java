package edu.utep.cs.cs4330.mypricewatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemList{

    private static ItemList listInstance = null;
    private ArrayList<Item> list;

    private ItemList(){
        list = new ArrayList<>();
        Item broom = new Item("Broom", 29.97, "https://www.amazon.com/Cedar-Heavy-Commercial-Broom-Handle/dp/B0106FW42U/?th=1");
        Item mop = new Item("Mop", 24.99, "https://www.amazon.com/Cedar-Commercial-Grade-Heavy-Looped-End-String/dp/B01BX7JKRC/");
        Item table = new Item("Table", 20.87, "https://www.amazon.com/Furinno-11180GYW-BK-Simple-Design/dp/B01COV5A20/");
        Item chair = new Item("Chair", 34.85, "https://www.amazon.com/Bathroom-Safety-Shower-Bench-Chair/dp/B002VWK0WI/");
        Item tv = new Item("Television", 896.99, "https://www.amazon.com/LG-Electronics-65SK8000PUA-65-Inch-Ultra/dp/B079TT1RM1/");

        list.add(broom);
        list.add(mop);
        list.add(table);
        list.add(chair);
        list.add(tv);
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
        Collections.sort(list, (o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
    }

    public void sortByCurrentPrice(){
        Collections.sort(list, (o1, o2) -> {
            if(o1.getCurPrice() > o2.getCurPrice())
                return 1;
            else if(o1.getCurPrice() == o2.getCurPrice())
                return 0;
            return -1;
        });
    }

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
