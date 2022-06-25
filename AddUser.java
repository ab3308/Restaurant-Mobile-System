package com.example.foodorderingsystem;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUser extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_layout);
        //displays layout

        final SQLiteDatabase database = this.openOrCreateDatabase("FoodOrderingSystem", MODE_PRIVATE, null);
        //opens db if exists, if not creates new

        //initializing EditTexts
        final EditText nameTxt = (EditText)findViewById(R.id.nameInput);
        final EditText loginTxt = (EditText)findViewById(R.id.loginInput);
        final EditText passwordTxt = (EditText)findViewById(R.id.passwordInput);

        Button userBtn = (Button)findViewById(R.id.userBtn);
        userBtn.setOnClickListener(new View.OnClickListener() {//to add a user
            @Override
            public void onClick(View v) {

                String name = nameTxt.getText().toString();
                //retrieving inputs of name and login
                String login = loginTxt.getText().toString();

                String sqlStatement = "INSERT INTO staff(name, login, sales, numberOfSales) " +
                        "VALUES('" + name + "', '" + login + "', 0, 0)";
                //sql statement to insert user

                database.execSQL(sqlStatement);
                //executing sql statement

                Toast.makeText(AddUser.this, "User added", Toast.LENGTH_SHORT).show();//informing user of success

                nameTxt.setText("");
                loginTxt.setText("");
                //clearing EditText's
            }
        });

        Button adminBtn = (Button)findViewById(R.id.adminBtn);
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //retrieving inputs of name, login and password
                String name = nameTxt.getText().toString();
                String login = loginTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                String sqlStatement = "INSERT INTO adminLogin(name, userId, password) " +
                        "VALUES('" + name + "', '" + login + "', '" + password + "')";
                //statement to insert required values for admin

                database.execSQL(sqlStatement);
                //executing sql statement above

                // Admins are also general staff users, so simultaneously insert as user also
                String sqlStatement2 = "INSERT INTO staff(name, login, sales, numberOfSales) " +
                        "VALUES('" + name + "', '" + login + "', 0, 0)";
                //sql statement to insert as a user

                database.execSQL(sqlStatement2);
                //executing sql statement

                Toast.makeText(AddUser.this, "Admin added", Toast.LENGTH_SHORT).show();
                //informing user of success

                nameTxt.setText("");
                passwordTxt.setText("");
                loginTxt.setText("");
                //clearing EditText's
            }
        });
    }
}
