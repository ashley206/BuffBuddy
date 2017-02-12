package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateWorkout extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");
    }
}
