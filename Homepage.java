package com.example.foodorderingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);//displays homepage layout

        Button floorplanBtn = (Button)findViewById(R.id.floorplan);//initializing button
        floorplanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launches floorplan class when floorplan button clicked
                startActivity(new Intent(Homepage.this, Floorplan.class));
            };
        });

        Button adminBtn = (Button)findViewById(R.id.adminButton);//initializing button
        adminBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //launches admin login class when admin button clicked
                startActivity(new Intent(Homepage.this, AdminLogin.class));
            }
        });

        Button takeOrderBtn = (Button)findViewById(R.id.orderButton);//initializing button
        takeOrderBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //launches ordering class when order button clicked
                startActivity(new Intent(Homepage.this, TakeOrder.class));
            }
        });

        Button stockBtn = (Button)findViewById(R.id.stockButton);//initializing button
        stockBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //launches stock class when stock button clicked
                startActivity(new Intent(Homepage.this, Stock.class));
            }
        });
    }
}
