package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EditWorkoutActivity extends AppCompatActivity {

    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        Intent i = new Intent();
        workout = (Workout)i.getSerializableExtra("Workout");
    }

    public void EditExercises(View v){
        Intent i = new Intent(EditWorkoutActivity.this, EditExericsesActivity.class);
        i.putExtra("Workout", workout);
        startActivity(i);
    }
}
