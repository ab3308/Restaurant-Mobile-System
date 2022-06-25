package com.example.foodorderingsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Floorplan extends AppCompatActivity implements View.OnClickListener {

    Button table1;//initializing buttons
    Button table2;
    Button table3;
    Button table4;
    Button table5;
    Button table6;
    Button table7;
    Button table8;
    Button table9;
    Button table10;
    Button table11;
    Button table12;
    Button table13;
    Button table14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floorplan_layout);
        //displays floorplan layout

        //initializing + setting onClickListener's for all buttons
        table1 = (Button)findViewById(R.id.table1);
        table1.setOnClickListener(this);

        table2 = (Button)findViewById(R.id.table2);
        table2.setOnClickListener(this);

        table3 = (Button)findViewById(R.id.table3);
        table3.setOnClickListener(this);

        table4 = (Button)findViewById(R.id.table4);
        table4.setOnClickListener(this);

        table5 = (Button)findViewById(R.id.table5);
        table5.setOnClickListener(this);

        table6 = (Button)findViewById(R.id.table6);
        table6.setOnClickListener(this);

        table7 = (Button)findViewById(R.id.table7);
        table7.setOnClickListener(this);

        table8 = (Button)findViewById(R.id.table8);
        table8.setOnClickListener(this);

        table9 = (Button)findViewById(R.id.table9);
        table9.setOnClickListener(this);

        table10 = (Button)findViewById(R.id.table10);
        table10.setOnClickListener(this);

        table11 = (Button)findViewById(R.id.table11);
        table11.setOnClickListener(this);

        table12 = (Button)findViewById(R.id.table12);
        table12.setOnClickListener(this);

        table13 = (Button)findViewById(R.id.table13);
        table13.setOnClickListener(this);

        table14 = (Button)findViewById(R.id.table14);
        table14.setOnClickListener(this);

        colourChange();

    }

    public void colourChange(){
        //sets colours of tables (buttons) according to boolean value

        if(MainActivity.table1Bool){
            table1.setBackgroundColor(Color.GREEN);
            //free table
        }else{
            table1.setBackgroundColor(Color.RED);
            //occupied table
        }

        if(MainActivity.table2Bool){
            table2.setBackgroundColor(Color.GREEN);
        }else{
            table2.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table3Bool){
            table3.setBackgroundColor(Color.GREEN);
        }else{
            table3.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table4Bool){
            table4.setBackgroundColor(Color.GREEN);
        }else{
            table4.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table5Bool){
            table5.setBackgroundColor(Color.GREEN);
        }else{
            table5.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table6Bool){
            table6.setBackgroundColor(Color.GREEN);
        }else{
            table6.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table7Bool){
            table7.setBackgroundColor(Color.GREEN);
        }else{
            table7.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table8Bool){
            table8.setBackgroundColor(Color.GREEN);
        }else{
            table8.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table9Bool){
            table9.setBackgroundColor(Color.GREEN);
        }else{
            table9.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table10Bool){
            table10.setBackgroundColor(Color.GREEN);
        }else{
            table10.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table11Bool){
            table11.setBackgroundColor(Color.GREEN);
        }else{
            table11.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table12Bool){
            table12.setBackgroundColor(Color.GREEN);
        }else{
            table12.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table13Bool){
            table13.setBackgroundColor(Color.GREEN);
        }else{
            table13.setBackgroundColor(Color.RED);
        }

        if(MainActivity.table14Bool){
            table14.setBackgroundColor(Color.GREEN);
        }else{
            table14.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //flips the state of any selected table
            case R.id.table1:
                MainActivity.table1Bool = !MainActivity.table1Bool;
                break;
            case R.id.table2:
                MainActivity.table2Bool = !MainActivity.table2Bool;
                break;
            case R.id.table3:
                MainActivity.table3Bool = !MainActivity.table3Bool;
                break;
            case R.id.table4:
                MainActivity.table4Bool = !MainActivity.table4Bool;
                break;
            case R.id.table5:
                MainActivity.table5Bool = !MainActivity.table5Bool;
                break;
            case R.id.table6:
                MainActivity.table6Bool = !MainActivity.table6Bool;
                break;
            case R.id.table7:
                MainActivity.table7Bool = !MainActivity.table7Bool;
                break;
            case R.id.table8:
                MainActivity.table8Bool = !MainActivity.table8Bool;
                break;
            case R.id.table9:
                MainActivity.table9Bool = !MainActivity.table9Bool;
                break;
            case R.id.table10:
                MainActivity.table10Bool = !MainActivity.table10Bool;
                break;
            case R.id.table11:
                MainActivity.table11Bool = !MainActivity.table11Bool;
                break;
            case R.id.table12:
                MainActivity.table12Bool = !MainActivity.table12Bool;
                break;
            case R.id.table13:
                MainActivity.table13Bool = !MainActivity.table13Bool;
                break;
            case R.id.table14:
                MainActivity.table14Bool = !MainActivity.table14Bool;
                break;
            default:
                //base case
                Toast.makeText(this, "Unexpected error", Toast.LENGTH_SHORT).show();
                //error message
                break;
        }
        colourChange();//visibly update tables (buttons)
    }

}
