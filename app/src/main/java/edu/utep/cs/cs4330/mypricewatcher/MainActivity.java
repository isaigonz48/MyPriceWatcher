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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button refreshButton;
    private Button addButton;
    private static ItemList wishList;
    private ItemListAdapter itemAdapter;
    private PopupMenu addEditMenu;

    private class ItemListAdapter extends ArrayAdapter<Item> {

        private final ItemList list;
        private Context context;

        public ItemListAdapter(Context ctx, ItemList itemlist) {
            super(ctx, -1, itemlist.getList());
            this.context = ctx;
            this.list = itemlist;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView != null ? convertView
                    : LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);

            Item item = list.get(position);
            TextView itemNameView = itemView.findViewById(R.id.itemName);
            TextView curPriceView = itemView.findViewById(R.id.curPrice);
            TextView initPriceView = itemView.findViewById(R.id.initPrice);
            TextView percentageOffView = itemView.findViewById(R.id.percentageOff);

            itemNameView.setText(item.getName());
            curPriceView.setText(String.format("Current Price: $%.02f", item.getCurPrice()));
            initPriceView.setText(String.format("Initial Price: $%.02f", item.getInitPrice()));
            percentageOffView.setText(String.format("%.02f%% off!", item.calcPercentageOff()));

            Button itemLinkButton = itemView.findViewById(R.id.itemLink);
            itemLinkButton.setOnClickListener(view ->{
                String website = item.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                startActivity(i);
            });



            Button editButton = itemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(view ->{
                Intent i = new Intent(this.context, addEditItemActivity.class);
                i.putExtra("adding", false);
                i.putExtra("itemPosition", position);

                startActivityForResult(i, 1);
            });

            Button removeButton = itemView.findViewById(R.id.removeButton);
            removeButton.setOnClickListener(view ->{
                wishList.remove(item);
                this.notifyDataSetChanged();
            });

            itemView.setOnClickListener(view ->{
                Intent i = new Intent(this.context, DetailedItemActivity.class);
                i.putExtra("itemPos", position);

                startActivityForResult(i,1);
            });



            return itemView;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent result){
        if(requestCode == 1 && resultCode == RESULT_OK){
            String resultString = result.getData().toString();

            if(resultString.equals("LIST_CHANGE")){
                itemAdapter.notifyDataSetChanged();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        refreshButton = findViewById(R.id.refreshButton);
        addButton = findViewById(R.id.addButton);
        wishList = ItemList.getInstance();

        ListView listview = findViewById(R.id.itemList);

        Item broom = new Item("Broom", 29.97, "https://www.amazon.com/Cedar-Heavy-Commercial-Broom-Handle/dp/B0106FW42U/?th=1");
        Item mop = new Item("Mop", 24.99, "https://www.amazon.com/Cedar-Commercial-Grade-Heavy-Looped-End-String/dp/B01BX7JKRC/");
        Item table = new Item("Table", 20.87, "https://www.amazon.com/Furinno-11180GYW-BK-Simple-Design/dp/B01COV5A20/");
        Item chair = new Item("Chair", 34.85, "https://www.amazon.com/Bathroom-Safety-Shower-Bench-Chair/dp/B002VWK0WI/");
        Item tv = new Item("Television", 896.99, "https://www.amazon.com/LG-Electronics-65SK8000PUA-65-Inch-Ultra/dp/B079TT1RM1/");

        wishList.getList().add(broom);
        wishList.add(mop);
        wishList.add(table);
        wishList.add(chair);
        wishList.add(tv);

        itemAdapter = new ItemListAdapter(this, wishList);

        listview.setAdapter(itemAdapter);

        refreshButton.setOnClickListener(view -> {
            wishList.findNewPrices();
            itemAdapter.notifyDataSetChanged();
        });

        /*addEditMenu = new PopupMenu(this, addButton);
        addEditMenu.inflate(R.menu.addedit_menu);

        addEditMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });*/

        addButton.setOnClickListener(view -> {
            Intent i = new Intent(this, addEditItemActivity.class);
            i.putExtra("adding", true);
            startActivityForResult(i,1);
        });

        ///// Gets the url that was shared and sets it as new url
        String action = getIntent().getAction();
        String type = getIntent().getType();
        if (Intent.ACTION_SEND.equalsIgnoreCase(action)
                && type != null && ("text/plain".equals(type))){
            String url = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            Intent i = new Intent(this, addEditItemActivity.class);
            i.putExtra("sharedUrl", url);
            i.putExtra("adding", true);
            startActivityForResult(i, 1);

        }

    }
}
