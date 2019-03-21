package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class addEditItemActivity extends AppCompatActivity {

    EditText itemName;
    EditText initPrice;
    EditText curPrice;
    EditText itemUrl;

    Button cancelButton;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        Intent i = getIntent();
        boolean adding = i.getBooleanExtra("adding", true);

        itemName = findViewById(R.id.itemName);
        initPrice = findViewById(R.id.initPrice);
        curPrice = findViewById(R.id.curPrice);
        itemUrl = findViewById(R.id.itemUrl);

        int pos;
        Item item;
        if(adding == false){
            pos = i.getIntExtra("itemPosition", 0);
            ItemList list = ItemList.getInstance();
            item = list.get(pos);

            itemName.setText(item.getName());
            initPrice.setText(String.format("%.02f", item.getInitPrice()));
            curPrice.setText(String.format("%.02f", item.getCurPrice()));
            itemUrl.setText(item.getUrl());
        }

        cancelButton = findViewById(R.id.cancelButton);
        confirmButton = findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(view ->{
            finish();
        });
    }
}
