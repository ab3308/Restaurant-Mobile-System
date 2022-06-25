package com.example.foodorderingsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class AdminLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_layout);

        final SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);//opens db if exists, if not creates new
        database.execSQL("INSERT INTO adminLogin(name, userId, password) VALUES('Admin 1', 'admin', 'password')");
        //one valid admin required in order to access features

        Button login = (Button)findViewById(R.id.loginButton);
        //defining login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView username = (TextView)findViewById(R.id.usernameInput);//gets username input
                TextView password = (TextView)findViewById(R.id.passwordInput);//gets password input
                String usernameStr = username.getText().toString();//converts username to string
                String passwordStr = password.getText().toString();//converts password to string

                String sqlStatement = "SELECT * FROM adminLogin WHERE userId='"+usernameStr+"'";//statement to locate admin login

                Cursor cursor = database.rawQuery(sqlStatement, null);
                //executing previous SQL using cursor
                cursor.moveToFirst();
                //moving to first found record

                int passwordColumnIndex = cursor.getColumnIndex("password");
                //index of password

                if(cursor.getCount()==0){//no results matching the login
                    Toast.makeText(AdminLogin.this, "Error: no records matching this input", Toast.LENGTH_LONG).show();
                    //gives user error message
                }else{
                    if(cursor.getString(passwordColumnIndex).equals(passwordStr)){
                        //successful login attempt
                        startActivity(new Intent(AdminLogin.this, Admin.class));
                        //starts admin class
                        Toast.makeText(AdminLogin.this, "Logged in", Toast.LENGTH_LONG).show();
                    }else{
                        //incorrect password
                        Toast.makeText(AdminLogin.this, "Error: incorrect password", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

}
