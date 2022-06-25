package com.example.foodorderingsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Stock extends AppCompatActivity {

    public static Boolean increaseStock;//for increasing/decreasing stock

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_layout);//layout

        final TextView statusTxt = (TextView)findViewById(R.id.statusTxtView);

        Button increaseButton = (Button)findViewById(R.id.increaseBtn);
        //defining button
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //increase option
                increaseStock = true;
                statusTxt.setText("Increase");
            }
        });

        Button decreaseButton = (Button)findViewById(R.id.decreaseBtn);
        //defining button
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //decrease option
                increaseStock = false;
                statusTxt.setText("Decrease");
            }
        });

        final SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);//opens db if exists, if not creates new

        final ListView stockLV = (ListView)findViewById(R.id.stockListview);//initializing listview
        final ArrayList<String> stockArray = new ArrayList<String>();//initializing stockArray
        final ArrayAdapter<String> stockArrayAdapter = new ArrayAdapter<String>(Stock.this, android.R.layout.simple_list_item_1, stockArray);//passing array stockArray into array adapter

        try {
            Cursor stockCursor = database.rawQuery("SELECT * FROM menuItems", null);//finds all items of the category: starter
            stockCursor.moveToFirst();//moves to 1st item in db
            int foodIndex = stockCursor.getColumnIndex("foodItem");//finds index of item name
            int stockIndex = stockCursor.getColumnIndex("stock");//finds index of stock level
            int typeIndex = stockCursor.getColumnIndex("itemType"); //finds index of item type

            while (!stockCursor.isAfterLast()) {//loops through all items
                String item = stockCursor.getString(foodIndex);//gets item from cursor and converts to string
                String stock = stockCursor.getString(stockIndex);//gets corresponding stock and converts to string
                String type = stockCursor.getString(typeIndex);//gets item type

                String mergedString = item + " - " + stock + " - " + type;//combines item and stock into

                stockArray.add(mergedString);//adds merged string to array
                stockCursor.moveToNext();//moves cursor to next item
            }
            stockLV.setAdapter(stockArrayAdapter);//sets array adapter to listview
        }catch(Exception e){
            Toast.makeText(this, "Error: unable to complete your request", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }//fill listview with all items and corresponding stock

        stockLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {//onClickListener for modifying stock
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {//onClick to change stock level
                try {

                    String clickedListItem = stockArray.get(i);//finds clicked item in array

                    int initialIndex = clickedListItem.indexOf(" - "); //finds first occurrence of " - ", so that I can extract the item name
                    int lastIndex = clickedListItem.lastIndexOf(" - "); //finds last occurrence of " - ", so that I can extract the item name
                    int length = clickedListItem.length();//finds length of the clicked item string

                    String itemName = clickedListItem.substring(0, initialIndex);//creates a string containing the food name only
                    String itemType = clickedListItem.substring(lastIndex+3, length);//finds item type, +3 to remove " - "
                    String originalStock = clickedListItem.substring(initialIndex+3, lastIndex);//+3 to remove " - "

                    int originalStockInt = Integer.parseInt(originalStock);
                    //defining stock integer as value extracted from listview
                    int newStockInt;
                    String newStock;

                    if (increaseStock) {
                        //increase stock
                        database.execSQL("UPDATE menuItems SET stock = stock+1 WHERE foodItem ='"+itemName+"'");//change stock in DB
                        newStockInt = originalStockInt + 1;//change stock value

                    } else {
                        //decrease stock
                        database.execSQL("UPDATE menuItems SET stock = stock-1 WHERE foodItem ='"+itemName+"'");//change stock in DB
                        newStockInt = originalStockInt - 1;//changes stock value

                    }
                    newStock = Integer.toString(newStockInt);
                    String newItemString = itemName + " - " + newStock + " - " + itemType;
                    stockArray.set(i, newItemString);//replace old array item with updated stock level item
                    stockLV.setAdapter(stockArrayAdapter);
                }catch(Exception e){
                   Toast.makeText(Stock.this, "Error: please select increase/decrease", Toast.LENGTH_SHORT).show();
                   //error message when error caught
                }
            }
        });
    }
}
