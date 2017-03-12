package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

// This class will take a given user and Workout and cycle through the exercises in that workout.
public class PerformWorkoutActivity extends AppCompatActivity {

    Workout workout;
    User user;
    ArrayList<Exercise> exercises;
    int currentExerciseIndex = 0;
    String weight = "";

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

    void promptForResult(String dlgTitle, String dlgMessage, String positiveMsg, String negativeMsg, final DialogInputInterface dlg) {
        // replace "MyClass.this" with a Context object. If inserting into a class extending Activity,
        // using just "this" is perfectly ok.
        AlertDialog.Builder builder = new AlertDialog.Builder(PerformWorkoutActivity.this);
        builder.setTitle(dlgTitle);
        builder.setMessage(dlgMessage);
        // build the dialog
        final View v = dlg.onBuildDialog();
        // put the view obtained from the interface into the dialog
        if (v != null) {
            builder.setView(v);
        }
        // procedure for when the ok button is clicked.
        builder.setPositiveButton(positiveMsg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //weight = weightInput.getText().toString();
                // Pass this to the database to update the personal progress table
                int exerciseID = exercises.get(currentExerciseIndex).getID();
                int userID = user.getID();
            }
        });

        builder.setNegativeButton(negativeMsg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dlg.onCancel();
                dialog.dismiss();
                return;
            }
        });
        builder.show();
    }

    public void PromptForWeightEntry(){
        promptForResult("Weight Entry", "Enter the weight used for this workout.", "Save", "Skip", new DialogInputInterface() {
            EditText weightInput;

            @Override
            public View onBuildDialog() {
                // procedure to build the dialog view
                weightInput = new EditText(PerformWorkoutActivity.this);
                // cast the edittext as a view
                View outView = (View) weightInput;
                // return the view to the dialog builder
                return outView;
            }

            @Override
            public void onCancel() {
                // User has cancelled out of the dialog
            }

            @Override
            public void onResult(View v) {
                weight = weightInput.getText().toString();
                // Pass this to the database to update the personal progress table
                int exerciseID = exercises.get(currentExerciseIndex).getID();
                int userID = user.getID();
            }
        });
    }
}
