/**
**   @author Isai Gonzalez
**  CS 4350: Mobile Application Development
**   @date Feb 20, 2019
**
**   My Price Watcher
**
**   Application for android that has the price of an item from Amazon. The app can refresh
**   the price of the item, this will only return a new simulated price for the item. The user
**   can also visit the webpage for the item by clicking on the button specified. Future
**   versions of this app will be able to contain several items and update the prices with
**   actual prices from stores.
**  MainActivity class is an activity class that manages the way the screen looks and events.
**/

package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ///// For now, the item is hardcoded into the app. Later, this will be a list of items
    ///// and will not be hard coded.
    private Button refreshButton;
    private ItemList wishList;
    //private Item item1;
    //private TextView itemName;
    //private TextView curPrice;
    //private TextView initPrice;
    //private TextView percentOff;
    //private Button itemLink;

    private static class ItemListAdapter extends ArrayAdapter<Item> {

        private final List<Item> list;// = ItemList.getInstance().getList();

        public ItemListAdapter(Context ctx, List<Item> itemlist) {
            super(ctx, -1, itemlist);
            this.list = itemlist;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView != null ? convertView
                    : LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            //Grade.Score score = scores.get(position);
            Item item = list.get(position);
            TextView view = itemView.findViewById(R.id.itemName);
            view.setText(item.getName());
            view = itemView.findViewById(R.id.curPrice);
            view.setText(Double.toString(item.getCurPrice()));
            view = itemView.findViewById(R.id.initPrice);
            view.setText(Double.toString(item.getInitPrice()));
            return itemView;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshButton = findViewById(R.id.refreshButton);
        wishList = ItemList.getInstance();

        ArrayAdapter<Item> itemAdapter = new ItemListAdapter(this, wishList.getList());


        ListView listview = findViewById(R.id.itemList);


        //item1 = new Item();
        //itemName = findViewById(R.id.itemName);
        //curPrice = findViewById(R.id.curPrice);
        //initPrice = findViewById(R.id.initPrice);
        //percentOff = findViewById(R.id.percentageOff);
        //itemLink = findViewById(R.id.itemLink);

        //itemName.setText(item1.getName());
        //curPrice.setText(String.format("Current Price: $%.02f", item1.getCurPrice()));
        //initPrice.setText(String.format("Initial Price: $%.02f", item1.getInitPrice()));
        //percentOff.setText(String.format("%.02f%% off!", item1.calcPercentageOff()));

        ///// Simulates new price for the item.
        refreshButton.setOnClickListener(view -> {
            /*item1.findNewPrice();
            //double percentageOff = item1.calcPercentageOff();
            curPrice.setText(String.format("Current Price: $%.02f", item1.getCurPrice()));
            percentOff.setText(String.format("%.02f%% off!", item1.calcPercentageOff()));
            */
        });

        ///// Opens website where I found the idea for the item.
        /*itemLink.setOnClickListener(view ->{
            String website = item1.getUrl();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
            startActivity(i);
        });*/
        ///// Gets the url that was shared and sets it as new url
        String action = getIntent().getAction();
        String type = getIntent().getType();
        if (Intent.ACTION_SEND.equalsIgnoreCase(action)
                && type != null && ("text/plain".equals(type))){
            String url = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            //item1.setURL(url);

        }

    }
}
