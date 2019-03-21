package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        itemName.setText(item.getName());
        currentPrice.setText(String.format("Current Price: $%.02f", item.getCurPrice()));
        initialPrice.setText(String.format("Initial Price: $%.02f", item.getInitPrice()));
        percentageOff.setText(String.format("%.02f%% off!", item.calcPercentageOff()));
        itemUrl.setText(item.getUrl());

        backButton.setOnClickListener(view ->{
            finish();
        });

        editButton.setOnClickListener(view ->{

        });
        removeButton.setOnClickListener(view ->{
            Intent result = new Intent();
            result.setData(Uri.parse("LIST_CHANGE"));
            setResult(RESULT_OK, result);
            list.remove(item);
            finish();
        });
    }
}
