package com.fractaltechnologies.ashleyw.buffbuddy;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    User user = new User();
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // We will need to write to the database
        db = new DBAdapter(this);
        db.openWrite();
    }


    public void Register(View v){
        boolean valid = true;
        // Gather all variables from form
        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etEmail = (EditText)findViewById((R.id.etEmail));
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        final EditText etFirstName = (EditText)findViewById(R.id.etFirstName);
        final EditText etLastName = (EditText)findViewById(R.id.etLastName);

        // Retrieve text values
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String fname = etFirstName.getText().toString();
        String lname = etLastName.getText().toString();

        // Ensure all fields are filled
        if(username.equals("")||email.equals("")||password.equals("")||
                confirmPassword.equals("")||lname.equals("")||fname.equals("")){
            Toast.makeText(this, "All fields must have values.", Toast.LENGTH_LONG);
            valid = false;
        }
        // Ensure password values are the same
        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Passwords must match", Toast.LENGTH_LONG);
            valid = false;
        }
        if(valid) {
            // Determine if the user successfully logged in
            if (User.Register(username, email, password, confirmPassword, fname, lname, db)) {
                Toast.makeText(RegisterActivity.this, "Successful registration!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "Failed to register. Try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
