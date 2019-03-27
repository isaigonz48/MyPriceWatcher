/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date March 24, 2019
 **
 **   My Price Watcher
 **
 **   AddEditItemActivity class is an activity class that will display the editing or adding
 **   interface. Both of those functions are essentially the same, but, when editing an item, you
 **   cannot change the initial price. Whatever changes are made will return to the MainActivity
 **   class.
 **/

package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditItemActivity extends AppCompatActivity {

    private EditText itemName;
    //private EditText initPrice;
    //private EditText curPrice;
    private EditText itemUrl;

    private Button cancelButton;
    private Button confirmButton;

    private int pos;
    private Item editItem;
    private ItemList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        Intent i = getIntent();

        ///// Adding an item if true, editing if false
        boolean adding = i.getBooleanExtra("adding", true);

        itemName = findViewById(R.id.itemName);
        //initPrice = findViewById(R.id.initPrice);
        //curPrice = findViewById(R.id.curPrice);
        itemUrl = findViewById(R.id.itemUrl);

        list = ItemList.getInstance();

        ///// If editing an item then the item must be gotten from the list.
        ///// The initial price is also not able to be edited.
        if(!adding){
            pos = i.getIntExtra("itemPosition", 0);
            editItem = list.get(pos);

            itemName.setText(editItem.getName());
            //initPrice.setText(String.format("%.02f", editItem.getInitPrice()));
            //curPrice.setText(String.format("%.02f", editItem.getCurPrice()));
            itemUrl.setText(editItem.getUrl());

            //initPrice.setEnabled(false);
        }else{
            ///// Check if a url was shared and set it if yes.
            if(i.getStringExtra("sharedUrl") != null){
                String sharedUrl = i.getStringExtra("sharedUrl");
                itemUrl.setText(sharedUrl);
            }
        }

        ///// Automatically change the current price as the initial price is being set.
        /*initPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                curPrice.setText(initPrice.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        cancelButton = findViewById(R.id.cancelButton);
        confirmButton = findViewById(R.id.confirmButton);

        ///// Nothing happens
        cancelButton.setOnClickListener(view ->{
            finish();
        });

        confirmButton.setOnClickListener(view ->{
            ///// Price cannot be set to 0
            if(Double.parseDouble(curPrice.getText().toString()) <= 0 || Double.parseDouble(initPrice.getText().toString()) <= 0){
                Toast.makeText(this, "Price cannot be 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            ///// If adding the item, create the new item first
            if(adding){
                double initPrice = 100*Math.random();
                Item newItem = new Item(itemName.getText().toString(), initPrice, itemUrl.getText().toString());
                newItem.setCurPrice(initPrice);
                list.add(newItem);



                Intent result = new Intent();
                result.setData(Uri.parse("ITEM_ADD"));
                setResult(RESULT_OK, result);
                finish();
            }else{
                editItem.setName(itemName.getText().toString());
                //editItem.setCurPrice(Double.parseDouble(curPrice.getText().toString()));
                editItem.setURL(itemUrl.getText().toString());

                Intent result = new Intent();
                result.setData(Uri.parse("ITEM_EDIT"));
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
