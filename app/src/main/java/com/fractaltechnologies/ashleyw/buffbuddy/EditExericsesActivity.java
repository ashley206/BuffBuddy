package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class EditExericsesActivity extends AppCompatActivity {

    Workout workout;
    DBAdapter dbAdapter;
    ExerciseDAO exerciseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exericses);

        Intent i = getIntent();
        workout = (Workout)i.getSerializableExtra("Workout");

        // Exercises that exists for this workout need to be populated
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        exerciseDAO = new ExerciseDAO();
        exercises = exerciseDAO.FetchAll(this);

        // Create unique adapter to convert array to views
        ExerciseAdapter adapter = new ExerciseAdapter(this, exercises);

        // Attach adapter to ListView
        ListView listView = (ListView) findViewById(R.id.lvExercises);
        listView.setAdapter(adapter);
    }

    public void CreateExercise(View v){
        Intent i = new Intent(EditExericsesActivity.this, CreateExerciseActivity.class);
        i.putExtra("Workout", workout);
        startActivity(i);
    }
}
