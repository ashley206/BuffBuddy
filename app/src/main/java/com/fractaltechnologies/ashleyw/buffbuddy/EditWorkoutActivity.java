package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditWorkoutActivity extends AppCompatActivity {

    Workout workout;
    WorkoutDAO workoutDAO;
    ExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        Intent i = getIntent();
        workout = (Workout)i.getSerializableExtra("Workout");

        // Populate the editText field with the existing name
        final EditText etName = (EditText)findViewById(R.id.etName);
        etName.setText(workout.GetName());

        // Exercises that exists for this workout need to be populated
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        workoutDAO = new WorkoutDAO();
        exercises = workoutDAO.FetchExercisesInWorkout(workout.GetId(), this);

        // Create unique adapter to convert array to views
        adapter = new ExerciseAdapter(this, exercises);

        // Attach adapter to ListView
        final ListView listView = (ListView) findViewById(R.id.lvExercises);
        listView.setAdapter(adapter);

        // If an exercise is clicked on, view the details of that exercise
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise exercise = (Exercise) listView.getItemAtPosition(position);
                Intent i = new Intent(EditWorkoutActivity.this, EditExerciseActivity.class);
                i.putExtra("Exercise", exercise);
                i.putExtra("Workout", workout);
                startActivity(i);
            }
        });
    }

    // Add another exercise to this workout
    public void AddExercises(View v){
        Intent i = new Intent(EditWorkoutActivity.this, SelectExericsesActivity.class);
        i.putExtra("Workout", workout);
        startActivity(i);
    }

    public void SaveWorkout(View v){
        final EditText etName = (EditText)findViewById(R.id.etName);
        workout.SetName(etName.getText().toString());
        workoutDAO.Update(workout, this);

        Toast.makeText(this, "Successfully updated!", Toast.LENGTH_SHORT).show();
        // Return back to previous page
        startActivity(new Intent(EditWorkoutActivity.this, SelectWorkoutActivity.class));
    }
}
