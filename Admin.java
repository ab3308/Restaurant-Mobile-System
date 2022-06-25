package com.example.foodorderingsystem;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        //displaying admin layout

        final SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);
        //opens db if exists, if not creates new

        Button itemManagementBtn = (Button)findViewById(R.id.itemManagementButton);
        //onClick for item management button
        itemManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this, ItemManagement.class));
                //launches stock class when stock button clicked
            }
        });

        Button stockBtn = (Button)findViewById(R.id.stockButton);
        //onClick for stock button
        stockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this, Stock.class));
                //launches stock class when stock button clicked
            }
        });

        Button performanceBtn = (Button)findViewById(R.id.performanceButton);
        //onClick for performance button
        performanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this, Performance.class));
                //launches stock class when stock button clicked
            }
        });

        Button ordersBtn = (Button)findViewById(R.id.orderHistoryButton);
        //onClick for order history button
        ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this, OrderHistory.class));
                //launches order class when order history button clicked
            }
        });

        Button addUserBtn = (Button)findViewById(R.id.addUserBtn);
        //initializing button
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launches AddUser class when this button clicked
                startActivity(new Intent(Admin.this, AddUser.class));
            }
        });
    }
}
