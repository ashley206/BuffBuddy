package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class UpdateWorkout extends AppCompatActivity {

    User user;
    Workout workout;
    WorkoutDAO workoutDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");
        workout = (Workout)i.getSerializableExtra("Workout");

        // Exercises that exists for this workout need to be populated
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        workoutDAO  = new WorkoutDAO();
        exercises = workoutDAO.FetchExercisesInWorkout(workout.GetId(), this);

        // Create unique adapter to convert array to views
        ExerciseAdapter adapter = new ExerciseAdapter(this, exercises);

        // Attach adapter to ListView
        ListView listView = (ListView) findViewById(R.id.lvWorkouts);
        listView.setAdapter(adapter);
    }

    public void EditExercises(View v){

    }

    public void SaveWorkout(View v){
        // Update workout name
        EditText etName = (EditText)findViewById(R.id.etName);
        final String name = etName.getText().toString();
        workout.SetName(name);
        // This will update the workout in the database, given the workout ID
        WorkoutDAO workoutDAO = new WorkoutDAO();
        workoutDAO.Update(workout, this);
    }
}