package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateWorkout extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");
    }

    public void EditExercises(View v){

    }

    public void SaveWorkout(View v){
        // This will update the workout in the database, given the workout ID
        WorkoutDAO workoutDAO = new WorkoutDAO();
        //workoutDAO.Create(workout, this);
    }
}
