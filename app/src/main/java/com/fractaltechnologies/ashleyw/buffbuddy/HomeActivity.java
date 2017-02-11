package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve the User object passed in
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");


    }
}
