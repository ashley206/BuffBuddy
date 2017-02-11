package com.fractaltechnologies.ashleyw.buffbuddy;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button bttnLogin;
    DBAdapter db;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = new User();
        // Create instance of SQLite database
        db = new DBAdapter(this);
        db = db.openWrite();
        // Retrieve reference of Login button
        bttnLogin = (Button)findViewById(R.id.bttnLogin);
    }

    public void Login(View v) {
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        // Retrieve username and password text values
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        try {
            user.Login(username, password, db);
        }catch (Exception ex){
            Log.e("TAG", "HELP: " + ex.getMessage());
        }
        // Determine if the user successfully logged in
        if (user != null) {
            Toast.makeText(LoginActivity.this, "Successful login!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.putExtra("User", user);
            startActivity(i);
        } else {
            Toast.makeText(LoginActivity.this, "Failed to log in. Try again.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
