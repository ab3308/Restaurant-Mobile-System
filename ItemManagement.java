package com.example.foodorderingsystem;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemManagement extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_management_layout);//generates layout

        final SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);//opens db

        //initializing EditTexts
        final EditText itemInput = (EditText)findViewById(R.id.foodInput);
        final EditText priceInput = (EditText)findViewById(R.id.priceInput);
        final EditText typeInput = (EditText)findViewById(R.id.itemTypeInput);
        final EditText stockInput = (EditText)findViewById(R.id.stockInput);

        Button addBtn = (Button)findViewById(R.id.addButton);
        addBtn.setOnClickListener(new View.OnClickListener() {//adds item, using onclicklistener
            @Override
            public void onClick(View v) {
                //validation no input in an EditText
                try {
                    String itemStr = itemInput.getText().toString();
                    String priceStr = priceInput.getText().toString();
                    String typeStr = typeInput.getText().toString();
                    String stockStr = stockInput.getText().toString();
                    //retrieve input values and assign to strings

                    String sqlStatement = "INSERT INTO menuItems(foodItem, price, itemType, stock) " +
                            "VALUES('" + itemStr + "', " + priceStr + ", '" + typeStr + "', " + stockStr + ")";
                    database.execSQL(sqlStatement);
                    //insert values into database for menu items

                    itemInput.setText("");
                    priceInput.setText("");
                    typeInput.setText("");
                    stockInput.setText("");
                    //clearing the edit texts

                    Toast.makeText(ItemManagement.this, "Item added", Toast.LENGTH_SHORT).show();
                    //confirmation for user
                }catch (Exception e){
                    Toast.makeText(ItemManagement.this, "Ensure all boxes filled in", Toast.LENGTH_SHORT).show();
                    //error message
                }
            }
        });


        Button removeBtn = (Button)findViewById(R.id.removeButton);
        //defining button
        removeBtn.setOnClickListener(new View.OnClickListener() {//removing item from menu
            @Override
            public void onClick(View v) {
                String itemStr = itemInput.getText().toString();
                //gets item name to be removed
                String sqlStatement = "DELETE FROM menuItems WHERE foodItem ='" + itemStr + "'";
                //query to remove input value
                database.execSQL(sqlStatement);
                //executes previous statement

                Toast.makeText(ItemManagement.this, "Item removed", Toast.LENGTH_SHORT).show();
                //informs user of completion
                itemInput.setText("");
                //clears EditText
            }
        });
    }
}
