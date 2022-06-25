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

public class Performance extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.performance_layout);

        final SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);
        //opens db

        //defining listview
        ListView performanceLV = (ListView)findViewById(R.id.performanceListView);

        //defining textviews
        final TextView nameTV = (TextView)findViewById(R.id.nameTxtView);
        final TextView idTV = (TextView)findViewById(R.id.staffTxtView);
        final TextView salesTV = (TextView)findViewById(R.id.totalTxtView);
        final TextView numSalesTV = (TextView)findViewById(R.id.noSalesTxtView);

        //defining array and array adapter that it's passed into
        final ArrayList<String> staffArray = new ArrayList<String>();
        final ArrayAdapter<String> staffArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, staffArray);

        //validation to prevent crash
        try {
            Cursor staffCursor = database.rawQuery("SELECT * FROM staff", null);
            //retrieves all staff data
            staffCursor.moveToFirst();
            //moves to 1st item in db
            int IdIndex = staffCursor.getColumnIndex("login");
            //gets index of login

            while (!staffCursor.isAfterLast()) {//loops through all items
                String id = staffCursor.getString(IdIndex);//gets item from cursor and converts to string
                staffArray.add(id);//adds item to array
                staffCursor.moveToNext();//moves cursor to next item
            }

            performanceLV.setAdapter(staffArrayAdapter);
        }catch(Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        performanceLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //finds specific performance data for selected member of staff
                String selectedStaff = staffArray.get(i);
                Cursor selectionCursor = database.rawQuery("SELECT * FROM staff WHERE login='"+selectedStaff+"'", null);
                selectionCursor.moveToFirst();

                //indexes of relevant data
                int nameIndex = selectionCursor.getColumnIndex("name");
                int salesIndex = selectionCursor.getColumnIndex("sales");
                int numSalesIndex = selectionCursor.getColumnIndex("numberOfSales");

                //inserting data into relevant textviews
                salesTV.setText("£"+selectionCursor.getString(salesIndex));
                numSalesTV.setText(selectionCursor.getString(numSalesIndex));
                nameTV.setText(selectionCursor.getString(nameIndex));
                idTV.setText(selectedStaff);
            }
        });
    }
}


