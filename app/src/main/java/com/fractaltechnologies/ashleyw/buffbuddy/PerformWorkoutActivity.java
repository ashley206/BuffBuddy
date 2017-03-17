package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;

// This class will take a given user and Workout and cycle through the exercises in that workout.
public class PerformWorkoutActivity extends AppCompatActivity {

    Workout workout;
    User user;

    ArrayList<Exercise> exercises;
    int currentExerciseIndex = 0;
    String weight = "";
    private final static String TAG = "PerformWorkoutActivity";
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perform_workout);

        Intent i = getIntent();
        workout = (Workout)i.getSerializableExtra("Workout");
        user = (User)i.getSerializableExtra("User");

        exercises = workout.getExercises();
        InitPerformWorkout(workout, exercises, currentExerciseIndex);
    }

    // Populate the default information in the workout
    public void InitPerformWorkout(Workout workout, ArrayList<Exercise> exercises, int index){
        final TextView tvExerciseName = (TextView)findViewById(R.id.tvExerciseName);
        final TextView tvCurrentExercise = (TextView)findViewById(R.id.tvCurrentExercise);
        final TextView tvTotalExercises = (TextView)findViewById(R.id.tvTotalExercises);
        final TextView tvExerciseSetsTotal = (TextView)findViewById(R.id.tvExerciseTotalSets);
        final TextView tvExerciseRepsCurrnt = (TextView)findViewById(R.id.tvExerciseCurrentReps);

        tvExerciseName.setText(exercises.get(index).getName());
        // Exercise you're on out of how many total
        tvCurrentExercise.setText(String.valueOf(index + 1));
        tvTotalExercises.setText(String.valueOf(exercises.size()));
        // Show sets and repa for the exercise
        tvExerciseSetsTotal.setText(String.valueOf(exercises.get(index).getSets()));
        tvExerciseRepsCurrnt.setText(String.valueOf(exercises.get(index).getReps()));

    }

    public void NextExercise(View v){
        final EditText weightInput = new EditText(PerformWorkoutActivity.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(PerformWorkoutActivity.this);
        builder.setTitle("Exercise Completed");
        builder.setMessage("Enter the weight used for this exercise.");
        builder.setView(weightInput);
        // procedure for when the ok button is clicked.
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                weight = weightInput.getText().toString();
                // Pass this to the database to update the personal progress table
                int exerciseID = exercises.get(currentExerciseIndex).getID();
                int userID = user.getID();
                // Create values to put into database
                ContentValues values = new ContentValues();
                values.put("EXERCISE_ID", exerciseID);
                values.put("USER_ID", user.getID());
                values.put("WEIGHT", weight);
                values.put("DATE_COMPLETED",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                // Conduct insert
                try {
                    dbAdapter = new DBAdapter(PerformWorkoutActivity.this);
                    dbAdapter.openWrite().getDBInstance().insert("PROGRESS_HISTORY", null, values);
                }
                catch (Exception ex){
                    Log.e(TAG, "NextExercise: " + ex.getMessage());
                }
                // Increment the current exercise being performed
                currentExerciseIndex++;

                // Assure the next exercise is valid
                if(currentExerciseIndex == exercises.size()){
                    // Take you to home screen to show a workout was completed -- the one
                    Intent i = new Intent(PerformWorkoutActivity.this, HomeActivity.class);
                    i.putExtra("User", user);
                    i.putExtra("Workout", workout);
                    startActivity(i);
                }
                else{
                    // Initialize the next workout
                    InitPerformWorkout(workout, exercises, currentExerciseIndex);
                }
            }
        });

        builder.show();
    }
}
