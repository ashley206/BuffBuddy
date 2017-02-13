package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectWorkout extends AppCompatActivity {

    User user;
    WorkoutDAO workoutDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout);

        // Retrieve the user
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");

        // Retrieve list of workouts
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        workoutDAO = new WorkoutDAO();
        workouts = workoutDAO.FetchAll(this);

        // Create unique adapter to convert array to views
        WorkoutAdapter adapter = new WorkoutAdapter(this, workouts);

        // Attach adapter to ListView
        ListView listView = (ListView) findViewById(R.id.lvWorkouts);
        listView.setAdapter(adapter);
    }

    // COMPLETE
    public void CreateWorkout(View v){
        Intent i = new Intent(SelectWorkout.this, CreateWorkoutActivity.class);
        i.putExtra("User", user);
        startActivity(i);
    }


}
