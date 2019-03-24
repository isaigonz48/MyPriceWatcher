/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date March 24, 2019
 **
 **   My Price Watcher
 **
 **   DetailedItemActivity class is an activity class that displays the details of a chosen item.
 **   This activity displays information that would be too much to display on the MainActivity.
 **   Also includes a web view of the URL of the item.
 **/

package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class DetailedItemActivity extends AppCompatActivity {

    private TextView itemName;
    private TextView currentPrice;
    private TextView initialPrice;
    private TextView percentageOff;
    private TextView itemUrl;

    private Button backButton;
    private Button editButton;
    private Button removeButton;
    private Button itemLinkButton;

    private WebView itemWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item);

        Intent i = getIntent();
        int pos = i.getIntExtra("itemPos", 0);
        ItemList list = ItemList.getInstance();
        Item item = list.get(pos);

        itemName = findViewById(R.id.itemName);
        currentPrice = findViewById(R.id.curPrice);
        initialPrice = findViewById(R.id.initPrice);
        percentageOff = findViewById(R.id.percentageOff);
        itemUrl = findViewById(R.id.itemLink);

        backButton = findViewById(R.id.backButton);
        editButton = findViewById(R.id.editButton);
        removeButton = findViewById(R.id.removeButton);
        itemLinkButton = findViewById(R.id.linkButton);

        itemWebView = findViewById(R.id.itemWebView);


        itemName.setText(item.getName());
        currentPrice.setText(String.format("Current Price: $%.02f", item.getCurPrice()));
        initialPrice.setText(String.format("Initial Price: $%.02f", item.getInitPrice()));
        percentageOff.setText(String.format("%.02f%% off!", item.calcPercentageOff()));
        itemUrl.setText(item.getUrl());
        itemWebView.setWebViewClient(new WebViewClient());
        itemWebView.loadUrl(item.getUrl());

        backButton.setOnClickListener(view ->{
            finish();
        });

        ///// Calls edit activity and finishes so that it skips this activity on the way back.
        editButton.setOnClickListener(view ->{
            Intent editI = new Intent(this, addEditItemActivity.class);
            editI.putExtra("adding", false);
            editI.putExtra("itemPosition", pos);

            startActivity(editI);

            Intent result = new Intent();
            result.setData(Uri.parse("LIST_CHANGE"));
            setResult(RESULT_OK, result);
            finish();
        });

        removeButton.setOnClickListener(view ->{
            Intent result = new Intent();
            result.setData(Uri.parse("ITEM_REMOVE"));
            setResult(RESULT_OK, result);
            list.remove(item);
            finish();
        });

        itemLinkButton.setOnClickListener(view ->{
            String website = item.getUrl();
            Intent webI = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
            startActivity(webI);
        });
    }
}
