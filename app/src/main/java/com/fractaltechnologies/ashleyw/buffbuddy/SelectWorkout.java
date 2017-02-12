package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SelectWorkout extends AppCompatActivity {

    User user;
    DBAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout);

        // Retrieve the user
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");
        try {
            ArrayList<Workout> workouts = new ArrayList<Workout>();

            dbAdapter = new DBAdapter(this);
            workouts = (ArrayList<Workout>) dbAdapter.openRead().FetchAll();

            // Create unique adapter to convert array to views
            WorkoutAdapter adapter = new WorkoutAdapter(this, workouts);

            // Attach adapter to ListView
            ListView listView = (ListView) findViewById(R.id.lvWorkouts);
            listView.setAdapter(adapter);
        }
        catch(Exception ex){
            Log.e("TAG", "EXCEPTION: " + ex);
        }
    }

    public void CreateWorkout(View v){
        Intent i = new Intent(SelectWorkout.this, CreateWorkout.class);
        i.putExtra("User", user);
        startActivity(i);

    }


}
