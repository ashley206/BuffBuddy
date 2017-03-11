package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

// The parent of the EditWorkout page
public class ViewWorkoutActivity extends AppCompatActivity {

    WorkoutDAO workoutDAO;
    ExerciseAdapter adapter;
    Workout workout;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);

        Intent i = getIntent();
        workout = (Workout)i.getSerializableExtra("Workout");
        user = (User)i.getSerializableExtra("User");

        /// Set up action bar to contain Up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Exercises that exists for this workout need to be populated
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        workoutDAO = new WorkoutDAO();
        exercises = workoutDAO.FetchExercisesInWorkout(workout.GetId(), this);

        // Create unique adapter to convert array to views
        adapter = new ExerciseAdapter(this, exercises);

        // Attach adapter to ListView
        final ListView listView = (ListView) findViewById(R.id.lvExercises);
        listView.setAdapter(adapter);

        PopulateWorkoutInto(workout);
    }

    private void PopulateWorkoutInto(Workout workout){

    }

    public void StartWorkout(View v){
        // Redirect to the "routine" pages
        // Need to iterate over all of the exercises and show the reps
        // Only a cancel button, not a up button
        Intent i = new Intent(ViewWorkoutActivity.this, PerformWorkoutActivity.class);
        i.putExtra("User", user);
        i.putExtra("Workout", workout);
        startActivity(i);
    }

    public void EditWorkout(View v){
        Intent i = new Intent(ViewWorkoutActivity.this, EditWorkoutActivity.class);
        i.putExtra("User", user);
        i.putExtra("Workout", workout);
        startActivity(i);
    }
}
