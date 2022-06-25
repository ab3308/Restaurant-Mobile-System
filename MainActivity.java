package com.example.foodorderingsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;//to format my date
import java.util.Calendar;//to find date
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //declaring boolean for each table, so that you can track whether it is free
    public static boolean table1Bool=true;
    public static boolean table2Bool=true;
    public static boolean table3Bool=true;
    public static boolean table4Bool=true;
    public static boolean table5Bool=true;
    public static boolean table6Bool=true;
    public static boolean table7Bool=true;
    public static boolean table8Bool=true;
    public static boolean table9Bool=true;
    public static boolean table10Bool=true;
    public static boolean table11Bool=true;
    public static boolean table12Bool=true;
    public static boolean table13Bool=true;
    public static boolean table14Bool=true;

    public static String userID;//to track the user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {//when the class is started
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);//displays login layout


        Calendar calendar = Calendar.getInstance();//gets current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");//defines format
        String date = dateFormat.format(calendar.getTime());//converts date to format
        TextView dateTxtView = (TextView)findViewById(R.id.date);
        dateTxtView.setText(date);//sets date to textview

        final SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);//opens db if exists, if not creates new

        database.execSQL("CREATE TABLE IF NOT EXISTS staff (name VARCHAR, login VARCHAR, sales FLOAT, numberOfSales VARCHAR, id INTEGER PRIMARY KEY)");
        //4 categories for staff: name, login, total sales and ID(primary key)

        database.execSQL("CREATE TABLE IF NOT EXISTS adminLogin (name VARCHAR, userId VARCHAR, password VARCHAR, id INTEGER PRIMARY KEY)");
        //creates table to store admin logins

        database.execSQL("CREATE TABLE IF NOT EXISTS orders (foodOrdered VARCHAR, tableNumber INTEGER, totalCost FLOAT, staffMember VARCHAR, dateTime VARCHAR, id INTEGER PRIMARY KEY)");
        //creates table to store all orders (current and complete orders)

        database.execSQL("CREATE TABLE IF NOT EXISTS menuItems (foodItem VARCHAR, price FLOAT, itemType VARCHAR, stock INTEGER, id INTEGER PRIMARY KEY)");
        //table to hold menu items and corresponding stock levels
        final EditText loginTxt = (EditText)findViewById(R.id.userID); //initializing login EditText

        Button floorplanBtn = (Button)findViewById(R.id.floorPlan);
        floorplanBtn.setOnClickListener(new View.OnClickListener() {
            //when floorplan_layout button clicked, launches floorplan_layout
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Floorplan.class));//launches floorplan class on click

            };
        });

        Button loginBtn = (Button)findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {//onClick() for login button
            @Override
            public void onClick(View v) {
                userID = loginTxt.getText().toString();
                Cursor userCheckCursor = database.rawQuery("SELECT * FROM staff WHERE login='"+userID+"'", null);
                if(userCheckCursor.getCount()!=0){
                    Toast.makeText(MainActivity.this, "Welcome "+userID, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Homepage.class));
                }else{
                    Toast.makeText(MainActivity.this, "Invalid staff ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button takeOrderBtn = (Button)findViewById(R.id.takeOrder);
        takeOrderBtn.setOnClickListener(new View.OnClickListener() {//launches order class on click
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TakeOrder.class));//launches TakeOrder class on click
            }
        });
    }
}
