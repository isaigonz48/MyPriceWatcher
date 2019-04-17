/**
 **   @author Isai Gonzalez
 **  CS 4350: Mobile Application Development
 **   @date April 17, 2019
 **
 **   My Price Watcher
 **
 **   AddEditItemActivity class is an activity class that will display the editing or adding
 **   interface. Both of those functions are essentially the same. Whatever changes are made will return to the MainActivity
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
    private EditText itemUrl;

    private Button cancelButton;
    private Button confirmButton;

    private int pos;
    private Item editItem;
    private DatabaseItemList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        Intent i = getIntent();

        ///// Adding an item if true, editing if false
        boolean adding = i.getBooleanExtra("adding", true);

        itemName = findViewById(R.id.itemName);

        itemUrl = findViewById(R.id.itemUrl);

        list = DatabaseItemList.getInstance(this);
        ///// If editing an item then the item must be gotten from the list.
        if(!adding){
            pos = i.getIntExtra("itemPosition", 0);
            editItem = list.get(pos);

            itemName.setText(editItem.getName());
            itemUrl.setText(editItem.getUrl());

        }else{
            ///// Check if a url was shared and set it if yes.
            if(i.getStringExtra("sharedUrl") != null){
                String sharedUrl = i.getStringExtra("sharedUrl");
                itemUrl.setText(sharedUrl);
            }
        }

        cancelButton = findViewById(R.id.cancelButton);
        confirmButton = findViewById(R.id.confirmButton);

        ///// Nothing happens
        cancelButton.setOnClickListener(view ->{
            finish();
        });

        confirmButton.setOnClickListener(view ->{
            ///// Url must include https:// or http://
            if(itemUrl.getText().toString().length() < 4 || !itemUrl.getText().toString().substring(0,4).equals("http")){
                Toast.makeText(this, "Include \"https://\" or \"http://\"", Toast.LENGTH_LONG).show();
                return;
            }

            ///// If adding the item, create the new item first
            if(adding){
                Item newItem = new Item(itemName.getText().toString(), itemUrl.getText().toString());

                list.add(newItem);

                ///// Depending on validity of URL, return different result
                int validWebsite = PriceFinder.validateUrl(newItem.getUrl());
                if(validWebsite < 0){
                    Intent result = new Intent();
                    result.setData(Uri.parse("ITEM_ADD_URL_INVALID"));
                    setResult(RESULT_OK, result);
                    finish();
                }else if(validWebsite == 0){
                    Intent result = new Intent();
                    result.setData(Uri.parse("ITEM_ADD_URL_UNSUPPORTED"));
                    setResult(RESULT_OK, result);
                    finish();
                }else{
                    Intent result = new Intent();
                    result.setData(Uri.parse("ITEM_ADD"));
                    setResult(RESULT_OK, result);
                    finish();
                }
            }else{
                editItem.setName(itemName.getText().toString());
                editItem.setURL(itemUrl.getText().toString());
                editItem.findNewPrice();
                editItem.setInitPrice(editItem.getCurPrice());
                editItem.setPercentageOff();

                list.updateItem(editItem);

                int validWebsite = PriceFinder.validateUrl(editItem.getUrl());
                if(validWebsite < 0){
                    Intent result = new Intent();
                    result.setData(Uri.parse("ITEM_EDIT_URL_INVALID"));
                    setResult(RESULT_OK, result);
                    finish();
                }else if(validWebsite == 0){
                    Intent result = new Intent();
                    result.setData(Uri.parse("ITEM_EDIT_URL_UNSUPPORTED"));
                    setResult(RESULT_OK, result);
                    finish();
                }else{
                    Intent result = new Intent();
                    result.setData(Uri.parse("ITEM_ADD"));
                    setResult(RESULT_OK, result);
                    finish();
                }

            }
        });
    }
}
