package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateWorkoutActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");


    }



    public void SaveWorkout(View v){
        // This will create the workout in the database
        EditText etName = (EditText)findViewById(R.id.etName);
        String name = etName.getText().toString();
        Workout workout = new Workout(name);
        WorkoutDAO workoutDAO = new WorkoutDAO();
        workoutDAO.Create(workout, this);
    }
}
