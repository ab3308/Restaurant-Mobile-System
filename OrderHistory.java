package com.example.foodorderingsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history_layout);

        final SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);
        //opens db if exists, if not creates new

        //defining textviews
        final TextView costTV = (TextView)findViewById(R.id.totalTxtView);
        final TextView staffTV = (TextView)findViewById(R.id.staffIdTxtView);
        final TextView foodTV = (TextView)findViewById(R.id.foodTxtView);
        final TextView dateTV = (TextView)findViewById(R.id.dateTxtView);
        final TextView idTV = (TextView)findViewById(R.id.orderIdTxtView);
        final TextView tableTV = (TextView)findViewById(R.id.tableNumberTxtView);


        final ListView orderLV = (ListView)findViewById(R.id.orderListview);
        //initializing listview
        final ArrayList<String> ordersArray = new ArrayList<String>();
        //initializing stockArray
        final ArrayAdapter<String> ordersArrayAdapter = new ArrayAdapter<String>(OrderHistory.this, android.R.layout.simple_list_item_1, ordersArray);
        //passing array stockArray into array adapter

        try {
            Cursor ordersCursor = database.rawQuery("SELECT * FROM orders", null);
            //finds all items of the category: starter
            ordersCursor.moveToFirst();//moves to 1st item in db
            int orderIdIndex = ordersCursor.getColumnIndex("id");
            //finds index of ID
            int dateIndex = ordersCursor.getColumnIndex("dateTime");
            //finds index of date
            int costIndex = ordersCursor.getColumnIndex("totalCost");
            //finds index of cost

            while (!ordersCursor.isAfterLast()) {
                //loops through all items

                String idStr = ordersCursor.getString(orderIdIndex);
                //gets id and converts to string
                String dateStr = ordersCursor.getString(dateIndex);
                //gets date and converts to string
                String costStr = ordersCursor.getString(costIndex);
                //gets cost

                String mergedString = idStr + " - " + dateStr + " - £" + costStr;
                //combines item and stock into

                ordersArray.add(mergedString);
                //adds merged string to array
                ordersCursor.moveToNext();
                //moves cursor to next item
            }
            orderLV.setAdapter(ordersArrayAdapter);
            //sets array adapter to listview
        }catch(Exception e){
            Toast.makeText(OrderHistory.this, "Error: unable to complete your request", Toast.LENGTH_SHORT).show();
        }

        orderLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String selection = ordersArray.get(i);
                int index = selection.indexOf(" - ");
                //finds first occurrence of " - ", so that I can extract the order ID
                String orderID = selection.substring(0, index);
                //creates a string containing the order ID only
                Cursor selectedOrderCursor = database.rawQuery("SELECT * FROM orders WHERE id ='"+orderID+"'", null);
                //finds records with corresponding order ID
                selectedOrderCursor.moveToFirst();
                //moves to first matching record

                int orderIdIndex = selectedOrderCursor.getColumnIndex("id");
                //finds index of ID
                String idStr = selectedOrderCursor.getString(orderIdIndex);
                //gets id and converts to string
                idTV.setText(idStr);
                //sets string to corresponding textview

                int dateIndex = selectedOrderCursor.getColumnIndex("dateTime");
                //finds index of date
                String dateStr = selectedOrderCursor.getString(dateIndex);
                //gets date and converts to string
                dateTV.setText(dateStr);
                //sets string to corresponding textview

                int costIndex = selectedOrderCursor.getColumnIndex("totalCost");
                //finds index of cost
                String costStr = selectedOrderCursor.getString(costIndex);
                //gets cost
                costTV.setText(costStr);
                //sets string to corresponding textview

                int staffIndex = selectedOrderCursor.getColumnIndex("staffMember");
                //finds index of staff member
                String staffStr = selectedOrderCursor.getString(staffIndex);
                //gets staff to string
                staffTV.setText(staffStr);
                //sets string to corresponding textview

                int foodIndex = selectedOrderCursor.getColumnIndex("foodOrdered");
                //finds index of food ordered
                String foodStr = selectedOrderCursor.getString(foodIndex);
                //gets food to string
                foodTV.setText(foodStr);
                //sets string to corresponding textview

                int tableIndex = selectedOrderCursor.getColumnIndex("tableNumber");
                //finds index of table number
                String tableStr = selectedOrderCursor.getString(tableIndex);
                //gets table to string
                tableTV.setText(tableStr);
                //sets string to corresponding textview
            }
        });
    }
}
