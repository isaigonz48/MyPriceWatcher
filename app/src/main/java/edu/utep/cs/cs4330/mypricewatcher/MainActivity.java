/**
**   @author Isai Gonzalez
**  CS 4350: Mobile Application Development
**   @date March 24, 2019
**
**   My Price Watcher
**
**   Application for android that has a list of items attained from the web. Each item has its own
**   name, initial price, current price, and a percentage off. The user will be able to add, remove,
**   and edit items on the list. The user will be able to simulate new prices for the items on the
**   list by clicking on the refresh button. There is also a sorting feature. The user can visit
**   the web page of any item by clicking on the visit link button. Clicking on any item will also
**   display more details of the item.
**
**   MainActivity class is an activity class that displays the list of items and handles events
**   on the list.
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
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button refreshButton;
    private Button addButton;
    private Button sortButton;
    private static ItemList wishList;
    private ItemListAdapter itemAdapter;
    private PopupMenu sortMenu;

    /**
     * Item list adapter nested class makes an adapter that works with the item list.
     */
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

            ///// Prints item information
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

            ///// Opends addEditItem activity
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
                Toast.makeText(this.context, "Item removed!", Toast.LENGTH_SHORT).show();
            });

            itemView.setOnClickListener(view ->{
                Intent i = new Intent(this.context, DetailedItemActivity.class);
                i.putExtra("itemPos", position);

                startActivityForResult(i,1);
            });

            return itemView;
        }
    }

    /**
     * Gets results from called activities. Needs to know what kind of change happened and refresh
     * the list adapter.
     * @param requestCode
     * @param resultCode
     * @param result
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent result){
        if(requestCode == 1 && resultCode == RESULT_OK){
            String resultString = result.getData().toString();

            if(resultString.equals("ITEM_EDIT")){
                itemAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Item changed!", Toast.LENGTH_SHORT).show();
            }else if(resultString.equals("ITEM_ADD")){
                itemAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Item added!", Toast.LENGTH_SHORT).show();
            }else if(resultString.equals("ITEM_REMOVE")){
                itemAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Item removed!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshButton = findViewById(R.id.refreshButton);
        addButton = findViewById(R.id.addButton);
        sortButton = findViewById(R.id.sortButton);

        wishList = ItemList.getInstance();

        ListView listview = findViewById(R.id.itemList);

        itemAdapter = new ItemListAdapter(this, wishList);

        listview.setAdapter(itemAdapter);

        refreshButton.setOnClickListener(view -> {
            wishList.findNewPrices();
            itemAdapter.notifyDataSetChanged();
        });

        addButton.setOnClickListener(view -> {
            Intent i = new Intent(this, addEditItemActivity.class);
            i.putExtra("adding", true);
            startActivityForResult(i,1);
        });


        ///// Popup menu for sorting items
        sortMenu = new PopupMenu(this,sortButton);
        sortMenu.inflate(R.menu.sort_menu);
        sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sort_name:
                        wishList.sortByName();
                        itemAdapter.notifyDataSetChanged();
                        return true;

                    case R.id.sort_currentPrice:
                        wishList.sortByCurrentPrice();
                        itemAdapter.notifyDataSetChanged();
                        return true;

                    case R.id.sort_percentage:
                        wishList.sortByPercentage();
                        itemAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        sortButton.setOnClickListener(view -> sortMenu.show());


        ///// If url is shared with app and the app was not open, it opens addEditItem Activity
        String action = getIntent().getAction();
        String type = getIntent().getType();
        if (Intent.ACTION_SEND.equalsIgnoreCase(action)
                && type != null && ("text/plain".equals(type))) {
            String url = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            Intent i = new Intent(this, addEditItemActivity.class);
            i.putExtra("sharedUrl", url);
            i.putExtra("adding", true);
            startActivityForResult(i, 1);
        }

    }

    /**
     * If a url is shared while the app is open, it will open addEditItem Activity
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equalsIgnoreCase(action)
                && type != null && ("text/plain".equals(type))) {
            String url = intent.getStringExtra(Intent.EXTRA_TEXT);
            Intent i = new Intent(this, addEditItemActivity.class);
            i.putExtra("sharedUrl", url);
            i.putExtra("adding", true);
            startActivityForResult(i, 1);
        }
    }


}
