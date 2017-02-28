package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectExericsesActivity extends AppCompatActivity {

    Workout workout;
    DBAdapter dbAdapter;
    ExerciseDAO exerciseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exericses);

        Intent i = getIntent();
        workout = (Workout)i.getSerializableExtra("Workout");

        // Exercises that exists for this workout need to be populated
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        exerciseDAO = new ExerciseDAO();
        exercises = exerciseDAO.FetchAllNotInWorkout(this, workout.GetId());

        if(exercises != null) {
            // Create unique adapter to convert array to views
            ExerciseAdapter adapter = new ExerciseAdapter(this, exercises);

            // Attach adapter to ListView
            final ListView listView = (ListView) findViewById(R.id.lvExercises);
            listView.setAdapter(adapter);

            // When you select a row in the listview, the selected exercise will open in the next screen.
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Exercise exercise = (Exercise) listView.getItemAtPosition(position);
                    Intent i = new Intent(SelectExericsesActivity.this, EditExerciseActivity.class);
                    i.putExtra("Exercise", exercise);
                    startActivity(i);
                }
            });
        }
    }

    public void CreateExercise(View v){
        Intent i = new Intent(SelectExericsesActivity.this, CreateExerciseActivity.class);
        i.putExtra("Workout", workout);
        startActivity(i);
    }
}
