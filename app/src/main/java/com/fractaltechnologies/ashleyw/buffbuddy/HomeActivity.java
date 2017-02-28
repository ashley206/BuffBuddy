package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    User user;
    ArrayAdapter<Workout> workoutArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve the User object passed in
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");

        ListView lv = (ListView)findViewById(R.id.lvWorkouts);


    }

    public void BeginWorkout(View v){
        // Pass the user along to the Select Workout page
        Intent i = new Intent(HomeActivity.this, SelectWorkoutActivity.class);
        i.putExtra("User", user);
        startActivity(i);
    }

    private void fillListView(ArrayList<Workout> workouts){

    }
}
