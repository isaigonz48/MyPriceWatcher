package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addEditItemActivity extends AppCompatActivity {

    EditText itemName;
    EditText initPrice;
    EditText curPrice;
    EditText itemUrl;

    Button cancelButton;
    Button confirmButton;

    int pos;
    Item editItem;
    ItemList list;

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

        list = ItemList.getInstance();

        if(!adding){
            pos = i.getIntExtra("itemPosition", 0);
            editItem = list.get(pos);

            itemName.setText(editItem.getName());
            initPrice.setText(String.format("%.02f", editItem.getInitPrice()));
            curPrice.setText(String.format("%.02f", editItem.getCurPrice()));
            itemUrl.setText(editItem.getUrl());

            initPrice.setEnabled(false);
        }else{
            if(i.getStringExtra("sharedUrl") != null){
                String sharedUrl = i.getStringExtra("sharedUrl");
                itemUrl.setText(sharedUrl);
            }
        }

        /*String action = getIntent().getAction();
        String type = getIntent().getType();
        if (Intent.ACTION_SEND.equalsIgnoreCase(action)
                && type != null && ("text/plain".equals(type))){
            String url = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            itemUrl.setText(url);
        }*/

        initPrice.addTextChangedListener(new TextWatcher() {
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
        });

        cancelButton = findViewById(R.id.cancelButton);
        confirmButton = findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(view ->{
            finish();
        });

        confirmButton.setOnClickListener(view ->{

            if(Double.parseDouble(curPrice.getText().toString()) <= 0 || Double.parseDouble(initPrice.getText().toString()) <= 0){
                Toast.makeText(this, "Price cannot be 0!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(adding){
                Item newItem = new Item(itemName.getText().toString(), Double.parseDouble(initPrice.getText().toString()), itemUrl.getText().toString());
                newItem.setCurPrice(Double.parseDouble(curPrice.getText().toString()));
                list.add(newItem);

                Intent result = new Intent();
                result.setData(Uri.parse("ITEM_ADD"));
                setResult(RESULT_OK, result);
                finish();
            }else{
                //int pos = i.getIntExtra("itemPosition", 0);
                //Item item = list.get(pos);
                editItem.setName(itemName.getText().toString());
                editItem.setCurPrice(Double.parseDouble(curPrice.getText().toString()));
                editItem.setURL(itemUrl.getText().toString());

                Intent result = new Intent();
                result.setData(Uri.parse("ITEM_EDIT"));
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
