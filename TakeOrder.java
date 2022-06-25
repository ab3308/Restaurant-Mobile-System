package com.example.foodorderingsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TakeOrder extends AppCompatActivity {

    //declaring listviews
    ListView starterListView;
    ListView mainsListView;
    ListView dessertsListView;
    ListView drinksListView;
    ListView orderListView;
    ListView itemCostsListView;

    ArrayList<String> orderItemsArray;

    public boolean onCreateOptionsMenu(Menu menu){//adds menu options to order layout
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.order_menu_layout, menu);//displays option menu
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){//adds functionality to menu options
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()){//handles possible selections on options menu
            case R.id.completeOption://when complete option is selected

                SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);
                //opens db if exists, if not creates new

                TextView tableNumberTxt = (TextView)findViewById(R.id.tableNumberInput);
                TextView totalCost = (TextView)findViewById(R.id.orderTotalTxt);//initializing relevant textviews

                try {
                    int tableNumber = Integer.parseInt(tableNumberTxt.getText().toString());//gets table number input to integer
                    if(0<tableNumber && tableNumber<15){//checks that table number is valid
                        //do nothing - allow code to continue running this case
                    }else{
                        Toast.makeText(this, "Invalid table number", Toast.LENGTH_SHORT).show();//informs of invalid table number
                        return false;
                    }
                }catch(Exception e){//catches errors to prevent crashing
                    Toast.makeText(this, "Error - unable to complete your request: Please enter a table number", Toast.LENGTH_LONG).show();
                    //toast to inform of no table number input
                    return false;
                }

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");//defining date format
                String date = dateFormat.format(calendar.getTime());//gets current date into specific format

                String orderSummary = orderItemsArray.toString();//converts order array into a string

                String sqlStatement = "INSERT INTO orders(foodOrdered, tableNumber, totalCost, staffMember, dateTime)" +
                        "VALUES('" + orderSummary + "', '" + tableNumberTxt.getText().toString() + "'," + totalCost.getText().toString() + ", '"
                        + MainActivity.userID + "', '" + date + "')";
                //statement to insert order into table

                database.execSQL(sqlStatement);//executes SQL statement above

                String updateStaffTotalSQL = "UPDATE staff SET sales = sales+" + totalCost.getText().toString() + " WHERE login='" + MainActivity.userID + "'";
                //adds order total to the staff's sales

                String updateSalesNumSQL = "UPDATE staff SET numberOfSales=numberOfSales+1 WHERE login='" + MainActivity.userID + "'";
                //increments number of sales

                database.execSQL(updateStaffTotalSQL);//executes update
                database.execSQL(updateSalesNumSQL);//executes update

                orderListView.setAdapter(null);//clears items listview
                itemCostsListView.setAdapter(null);//clears costs listview
                totalCost.setText("00.00");//sets total cost back to 0

                startActivity(new Intent(TakeOrder.this, Homepage.class));//return to homepage

                Toast.makeText(this, "Order Complete", Toast.LENGTH_SHORT).show();//gives user confirmation of order completion

            return true;

            case R.id.removeHelp://when 'remove item from order selected'

                Toast.makeText(this, "To remove an item from the order simply click on it.", Toast.LENGTH_LONG).show();
                return true;

            default:
                return false;//when program doesn't perform as expected
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        starterListView = (ListView)findViewById(R.id.starterItems);//initializing listviews
        mainsListView = (ListView)findViewById(R.id.mainItems);
        dessertsListView = (ListView)findViewById(R.id.dessertItems);
        drinksListView = (ListView)findViewById(R.id.drinkItems);
        orderListView = (ListView)findViewById(R.id.orderSummary);
        itemCostsListView = (ListView)findViewById(R.id.orderItemCosts);

        final SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);//opens db if exists, if not creates new

        Cursor starterCursor;//initializing cursors
        Cursor mainsCursor;
        Cursor dessertsCursor;
        Cursor drinksCursor;

        final float orderTotal=0;

        orderItemsArray = new ArrayList<String>();//defining order Array
        final ArrayAdapter<String> orderArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orderItemsArray);
        //passes orderItems into array adapter

        final ArrayList<String> itemCostsArray = new ArrayList<String>();//initializing and defining costs array
        final ArrayAdapter<String> itemCostsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemCostsArray);
        //passes item costs into array adapter


        //starter listview manipulation
        final ArrayList<String> startersArray = new ArrayList<String>();
        try {
            starterCursor = database.rawQuery("SELECT * FROM menuItems WHERE itemType='starter'", null);
            //finds all items of the category: starter

            starterCursor.moveToFirst();//moves to 1st item in db

            int foodIndex = starterCursor.getColumnIndex("foodItem");//index to find name of item of food

            while (!starterCursor.isAfterLast()) {//loops through all items
                String item = starterCursor.getString(foodIndex);//gets item from cursor and converts to string
                startersArray.add(item);//adds item to array
                starterCursor.moveToNext();//moves cursor to next item
            }
        }catch(Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        ArrayAdapter<String> starterArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, startersArray);//sets starters list to array adapter
        starterListView.setAdapter(starterArrayAdapter);//sets array adapter to listview
        starterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//onclick listener for items in listview
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                //cost(starters.get(i));

                String sqlQuery="SELECT * FROM menuItems WHERE foodItem ='"+ startersArray.get(i)+"'";//creates query searching database for selected item
                Cursor selectedItemCursor=database.rawQuery(sqlQuery, null);//cursor used to execute query
                selectedItemCursor.moveToFirst();

                database.execSQL("UPDATE menuItems SET stock = stock-1 WHERE foodItem ='"+startersArray.get(i)+"'");//lowers stock by 1

                int selectedItemIndex = selectedItemCursor.getColumnIndex("price");//finds columnIndex of price of item
                float selectedItemCost = selectedItemCursor.getFloat(selectedItemIndex);//finds cost of item

                itemCostsArray.add(Float.toString(selectedItemCost));//adds cost to array

                TextView totalTxt=(TextView)findViewById(R.id.orderTotalTxt);//defining total TextView
                float orderTotal = Float.parseFloat(totalTxt.getText().toString());//getting total from textview
                orderTotal = orderTotal + selectedItemCost;//adding item cost to total
                totalTxt.setText(Float.toString(orderTotal));//setting new cost to TextView for total

                orderItemsArray.add(startersArray.get(i));//adds whichever item clicked to an array of all items on order

                itemCostsListView.setAdapter(itemCostsArrayAdapter);
                //set costs array adapter to costs ListView, visibly adding costs to ListView

                orderListView.setAdapter(orderArrayAdapter);//adds all items from array of order items to the order listview



            }
        });


        //mains listview manipulation
        final ArrayList<String> mainsArray = new ArrayList<String>();//defining array

        try {
            mainsCursor = database.rawQuery("SELECT * FROM menuItems WHERE itemType='main'", null);//finds all items of the category: mains
            mainsCursor.moveToFirst();//moves to 1st item in db
            int foodIndex = mainsCursor.getColumnIndex("foodItem");//finds name of item of food
            int priceIndex = mainsCursor.getColumnIndex("price");//finds corresponding price

            while (!mainsCursor.isAfterLast()) {//loops through all items
                String item = mainsCursor.getString(foodIndex);//gets item from cursor and converts to string
                mainsArray.add(item);//adds item to array
                mainsCursor.moveToNext();//moves cursor to next item
            }
        }catch(Exception e){//catches error to prevent crash
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        ArrayAdapter<String> mainsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mainsArray);
        mainsListView.setAdapter(mainsArrayAdapter);
        mainsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String sqlQuery="SELECT * FROM menuItems WHERE foodItem ='"+ mainsArray.get(i)+"'";//creates query searching database for selected item
                Cursor selectedItemCursor=database.rawQuery(sqlQuery, null);//cursor used to execute query
                selectedItemCursor.moveToFirst();

                int selectedItemIndex = selectedItemCursor.getColumnIndex("price");//finds columnIndex of price of item
                float selectedItemCost = selectedItemCursor.getFloat(selectedItemIndex);//finds cost of item

                itemCostsArray.add(Float.toString(selectedItemCost));//adds cost to array

                TextView totalTxt=(TextView)findViewById(R.id.orderTotalTxt);
                float orderTotal = Float.parseFloat(totalTxt.getText().toString());
                orderTotal = orderTotal + selectedItemCost;
                totalTxt.setText(Float.toString(orderTotal));

                database.execSQL("UPDATE menuItems SET stock = stock-1 WHERE foodItem ='"+mainsArray.get(i)+"'");//lowers stock by 1

                orderItemsArray.add(mainsArray.get(i));//adds whichever item clicked to an array of all items on order
                itemCostsListView.setAdapter(itemCostsArrayAdapter);
                orderListView.setAdapter(orderArrayAdapter);//adds all items from array of order items to the order listview
            }
        });


        //dessert listview manipulation
        final ArrayList<String> dessertsArray = new ArrayList<String>();
        try {
            dessertsCursor = database.rawQuery("SELECT * FROM menuItems WHERE itemType='dessert'", null);//finds all items of the category: desserts
            dessertsCursor.moveToFirst();//moves to 1st item in db
            int foodIndex = dessertsCursor.getColumnIndex("foodItem");//finds name of item of food

            while (!dessertsCursor.isAfterLast()) {//loops through all items
                String item = dessertsCursor.getString(foodIndex);//gets item from cursor and converts to string
                dessertsArray.add(item);//adds item to array
                dessertsCursor.moveToNext();//moves cursor to next item
            }
        }catch(Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        ArrayAdapter<String> dessertArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dessertsArray);
        dessertsListView.setAdapter(dessertArrayAdapter);
        dessertsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String sqlQuery="SELECT * FROM menuItems WHERE foodItem ='"+ dessertsArray.get(i)+"'";//creates query searching database for selected item
                Cursor selectedItemCursor=database.rawQuery(sqlQuery, null);//cursor used to execute query
                selectedItemCursor.moveToFirst();

                int selectedItemIndex = selectedItemCursor.getColumnIndex("price");//finds columnIndex of price of item
                float selectedItemCost = selectedItemCursor.getFloat(selectedItemIndex);//finds cost of item

                itemCostsArray.add(Float.toString(selectedItemCost));//adds cost to array

                TextView totalTxt=(TextView)findViewById(R.id.orderTotalTxt);
                float orderTotal = Float.parseFloat(totalTxt.getText().toString());
                orderTotal = orderTotal + selectedItemCost;
                totalTxt.setText(Float.toString(orderTotal));

                database.execSQL("UPDATE menuItems SET stock = stock-1 WHERE foodItem ='"+dessertsArray.get(i)+"'");//lowers stock by 1

                orderItemsArray.add(dessertsArray.get(i));//adds whichever item clicked to an array of all items on order
                itemCostsListView.setAdapter(itemCostsArrayAdapter);
                orderListView.setAdapter(orderArrayAdapter);//adds all items from array of order items to the order listview
            }
        });

        //drinks listview manipulation
        final ArrayList<String> drinksArray = new ArrayList<String>();
        try {
            drinksCursor = database.rawQuery("SELECT * FROM menuItems WHERE itemType='drink'", null);//finds all items of the category: mains
            drinksCursor.moveToFirst();//moves to 1st item in db
            int foodIndex = drinksCursor.getColumnIndex("foodItem");//finds name of item of food
            int priceIndex = drinksCursor.getColumnIndex("price");//finds corresponding price

            while (!drinksCursor.isAfterLast()) {//loops through all items
                String item = drinksCursor.getString(foodIndex);//gets item from cursor and converts to string
                drinksArray.add(item);//adds item to array
                drinksCursor.moveToNext();//moves cursor to next item
            }
        }catch(Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        ArrayAdapter<String> drinksArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drinksArray);
        drinksListView.setAdapter(drinksArrayAdapter);
        drinksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String sqlQuery="SELECT * FROM menuItems WHERE foodItem ='"+ drinksArray.get(i)+"'";//creates query searching database for selected item
                Cursor selectedItemCursor=database.rawQuery(sqlQuery, null);//cursor used to execute query
                selectedItemCursor.moveToFirst();

                int selectedItemIndex = selectedItemCursor.getColumnIndex("price");//finds columnIndex of price of item
                float selectedItemCost = selectedItemCursor.getFloat(selectedItemIndex);//finds cost of item

                itemCostsArray.add(Float.toString(selectedItemCost));//adds cost to array

                TextView totalTxt=(TextView)findViewById(R.id.orderTotalTxt);
                float orderTotal = Float.parseFloat(totalTxt.getText().toString());
                orderTotal = orderTotal + selectedItemCost;
                totalTxt.setText(Float.toString(orderTotal));

                database.execSQL("UPDATE menuItems SET stock = stock-1 WHERE foodItem ='"+drinksArray.get(i)+"'");//lowers stock by 1

                orderItemsArray.add(drinksArray.get(i));//adds whichever item clicked to an array of all items on order
                itemCostsListView.setAdapter(itemCostsArrayAdapter);
                orderListView.setAdapter(orderArrayAdapter);//adds all items from array of order items to the order listview
            }
        });


        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//removes item from order when clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {


                String sqlQuery="SELECT * FROM menuItems WHERE foodItem ='"+orderItemsArray.get(i)+"'";//creates query searching database for selected item
                Cursor removeItemCursor=database.rawQuery(sqlQuery, null);//cursor used to execute query
                removeItemCursor.moveToFirst();//moves cursor to first item found from query

                int removeItemIndex = removeItemCursor.getColumnIndex("price");//finds columnIndex of price of item
                float removedItemCost = removeItemCursor.getFloat(removeItemIndex);//finds cost of item

                TextView totalTxt=(TextView)findViewById(R.id.orderTotalTxt);//initialise textview
                float orderTotal = Float.parseFloat(totalTxt.getText().toString());//retrieves txtview content and converts to float
                orderTotal = orderTotal - removedItemCost;//find new sum total, after removing item
                totalTxt.setText(Float.toString(orderTotal));//set new total to textview

                database.execSQL("UPDATE menuItems SET stock = stock-1 WHERE foodItem ='"+orderItemsArray.get(i)+"'");//increases stock by 1

                itemCostsArray.remove(itemCostsArray.get(i));//item cost added simultaneously with item itself, so has same index
                orderItemsArray.remove(orderItemsArray.get(i));//remove clicked item from order

                itemCostsListView.setAdapter(itemCostsArrayAdapter);
                orderListView.setAdapter(orderArrayAdapter);//sets array adapter to listview in order to update it
            }
        });
    }
    public void starterClick(View view){
        starterListView.setVisibility(View.VISIBLE);//sets only starter listview to be visible
        mainsListView.setVisibility(View.INVISIBLE);
        dessertsListView.setVisibility(View.INVISIBLE);
        drinksListView.setVisibility(View.INVISIBLE);
    }

    public void mainsClick(View view){
        mainsListView.setVisibility(View.VISIBLE);//sets only mains listview to be visible
        starterListView.setVisibility(View.INVISIBLE);
        dessertsListView.setVisibility(View.INVISIBLE);
        drinksListView.setVisibility(View.INVISIBLE);
    }

    public void dessertClick(View view){
        dessertsListView.setVisibility(View.VISIBLE);//sets only desserts listview to be visible
        starterListView.setVisibility(View.INVISIBLE);
        mainsListView.setVisibility(View.INVISIBLE);
        drinksListView.setVisibility(View.INVISIBLE);
    }

    public void drinksClick(View view){
        drinksListView.setVisibility(View.VISIBLE);//sets only drinks listview to be visible
        starterListView.setVisibility(View.INVISIBLE);
        mainsListView.setVisibility(View.INVISIBLE);
        dessertsListView.setVisibility(View.INVISIBLE);
    }

}


