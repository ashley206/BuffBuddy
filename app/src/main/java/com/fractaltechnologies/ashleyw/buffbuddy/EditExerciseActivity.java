package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditExerciseActivity extends AppCompatActivity {
    Workout workout;
    Exercise exercise;
    User user;
    private static final String TAG = "EditExerciseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        /// Set up action bar to contain Up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        exercise = (Exercise)i.getSerializableExtra("Exercise");
        workout = (Workout)i.getSerializableExtra("Workout");
        user = (User)i.getSerializableExtra("User");
        PopulateExerciseInfo(exercise);

        // Not in current workout
        if(exercise.getWorkoutID() != workout.getId()){
            // Cannot remove an exercise not in your workout
            findViewById(R.id.bttnRemoveExerciseFromWorkout).setVisibility(View.GONE);
            // Can add an exercise not in your workout
            findViewById(R.id.bttnAddExerciseToWorkout).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.bttnRemoveExerciseFromWorkout).setVisibility(View.VISIBLE);
            // Can add an exercise not in your workout
            findViewById(R.id.bttnAddExerciseToWorkout).setVisibility(View.GONE);
        }
    }

    void PopulateExerciseInfo(Exercise exercise){
        //// Spinner setup
        Spinner spinnerPrimary = (Spinner)findViewById(R.id.spTargetMuscleP);
        Spinner spinnerSecondary = (Spinner)findViewById(R.id.spTargetMuscleS);
        // Create an ArrayAdapter using the the default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.targetMuscles, android.R.layout.simple_spinner_item
        );
        // Specifying the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply similar adapters to both spinners
        spinnerPrimary.setAdapter(adapter);
        spinnerSecondary.setAdapter(adapter);
        final Spinner spPMG = (Spinner)findViewById(R.id.spTargetMuscleP);
        final Spinner spSMG = (Spinner)findViewById(R.id.spTargetMuscleS);
        if(exercise.getPrimaryTargetMuscle() != null){
            spPMG.setSelection(adapter.getPosition(exercise.getPrimaryTargetMuscle().toString()));
        }
        else{
            spPMG.setSelection(0);
        }
        if(exercise.getSecondaryTargetMuscle() != null){
            spSMG.setSelection(adapter.getPosition(exercise.getSecondaryTargetMuscle().toString()));
        }
        else{
            spSMG.setSelection(0);
        }
        //// Edit Text list
        final EditText etName = (EditText)findViewById(R.id.etExerciseName);
        final EditText etRep1 = (EditText)findViewById(R.id.etRep1);
        final EditText etRep2 = (EditText)findViewById(R.id.etRep2);
        final EditText etRep3 = (EditText)findViewById(R.id.etRep3);
        final EditText etRep4 = (EditText)findViewById(R.id.etRep4);
        final EditText etRep5 = (EditText)findViewById(R.id.etRep5);
        final EditText etRep6 = (EditText)findViewById(R.id.etRep6);
        ArrayList<EditText> editTextArrayList = new ArrayList<EditText>();
        editTextArrayList.add(etRep1);
        editTextArrayList.add(etRep2);
        editTextArrayList.add(etRep3);
        editTextArrayList.add(etRep4);
        editTextArrayList.add(etRep5);
        editTextArrayList.add(etRep6);

        etName.setText(exercise.getName());

        ArrayList<Integer> reps = exercise.getReps();
        for(int i = 0; i < reps.size(); i++){
            editTextArrayList.get(i).setText(String.valueOf(reps.get(i)));
        }
    }

    public void saveExerciseToWorkout(View view) {
        final EditText etName = (EditText) findViewById(R.id.etExerciseName);
        final Spinner spPMG = (Spinner) findViewById(R.id.spTargetMuscleP);
        final Spinner spSMG = (Spinner) findViewById(R.id.spTargetMuscleS);
        final EditText etRep1 = (EditText) findViewById(R.id.etRep1);
        final EditText etRep2 = (EditText) findViewById(R.id.etRep2);
        final EditText etRep3 = (EditText) findViewById(R.id.etRep3);
        final EditText etRep4 = (EditText) findViewById(R.id.etRep4);
        final EditText etRep5 = (EditText) findViewById(R.id.etRep5);
        final EditText etRep6 = (EditText) findViewById(R.id.etRep6);
        // Set exercise object's info
        exercise.setName(etName.getText().toString());

        // Ensure only a selected value is saved to the database
        if (spPMG.getSelectedItemPosition() != 0) {
            exercise.setPrimaryTargetMuscle(TargetMuscle.fromString(spPMG.getSelectedItem().toString()));
        }
        else{
            exercise.setPrimaryTargetMuscle(null);
        }
        if(spSMG.getSelectedItemPosition() != 0) {
            exercise.setSecondaryTargetMuscle(TargetMuscle.fromString(spSMG.getSelectedItem().toString()));
        }
        else {
            exercise.setSecondaryTargetMuscle(null);
        }

        // Get the reps, but only the reps actually filled out
        ArrayList<EditText> editTextArrayList = new ArrayList<EditText>();
        editTextArrayList.add(etRep1);
        editTextArrayList.add(etRep2);
        editTextArrayList.add(etRep3);
        editTextArrayList.add(etRep4);
        editTextArrayList.add(etRep5);
        editTextArrayList.add(etRep6);
        ArrayList<Integer> reps = new ArrayList<Integer>();
        // Iterate over EditText objects and get all viable reps
        for(int i = 0; i < editTextArrayList.size(); i++){
            if(!editTextArrayList.get(i).getText().toString().equals(""))
                reps.add(Integer.parseInt(editTextArrayList.get(i).getText().toString()));
        }
        exercise.setReps(reps);
        exercise.setSets(reps.size());   // True number of reps

        // Update the exercise in the db
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        exerciseDAO.Update(exercise, this);

        // Give a success message and return to the previous page
        Toast.makeText(EditExerciseActivity.this, "Exercise updated!", Toast.LENGTH_LONG).show();

        Intent i = new Intent(EditExerciseActivity.this, EditWorkoutActivity.class);
        // We need to populate the workout again
        i.putExtra("Workout", workout);
        i.putExtra("Exercise", exercise);
        i.putExtra("User", user);
        startActivity(i);
    }

    public void removeExerciseFromWorkout(View view){
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try {
            exerciseDAO.RemoveExerciseFromWorkout(exercise, this);
            // Return to previous page
            Intent i = ApplicationParents.getInstance().parents.pop();
            i.putExtra("Workout", workout);
            i.putExtra("Exercise", exercise);
            i.putExtra("User", user);
            startActivity(i);
        }catch (Exception ex){
            Log.e(TAG, "removeExerciseFromWorkout: " + ex.getMessage());
        }

    }

    public void addExerciseToWorkout(View view){
        final EditText etName = (EditText) findViewById(R.id.etExerciseName);
        final Spinner spPMG = (Spinner) findViewById(R.id.spTargetMuscleP);
        final Spinner spSMG = (Spinner) findViewById(R.id.spTargetMuscleS);
        final EditText etRep1 = (EditText) findViewById(R.id.etRep1);
        final EditText etRep2 = (EditText) findViewById(R.id.etRep2);
        final EditText etRep3 = (EditText) findViewById(R.id.etRep3);
        final EditText etRep4 = (EditText) findViewById(R.id.etRep4);
        final EditText etRep5 = (EditText) findViewById(R.id.etRep5);
        final EditText etRep6 = (EditText) findViewById(R.id.etRep6);

        // Set exercise object's info
        Exercise dupExercise = new Exercise(etName.getText().toString());

        // Ensure only a selected value is saved to the database
        if (spPMG.getSelectedItemPosition() != 0) {
            dupExercise.setPrimaryTargetMuscle(TargetMuscle.fromString(spPMG.getSelectedItem().toString()));
        }
        else{
            dupExercise.setPrimaryTargetMuscle(null);
        }
        if(spSMG.getSelectedItemPosition() != 0) {
            dupExercise.setSecondaryTargetMuscle(TargetMuscle.fromString(spSMG.getSelectedItem().toString()));
        }
        else {
            dupExercise.setSecondaryTargetMuscle(null);
        }

        // Get the reps, but only the reps actually filled out
        ArrayList<EditText> editTextArrayList = new ArrayList<EditText>();
        editTextArrayList.add(etRep1);
        editTextArrayList.add(etRep2);
        editTextArrayList.add(etRep3);
        editTextArrayList.add(etRep4);
        editTextArrayList.add(etRep5);
        editTextArrayList.add(etRep6);
        ArrayList<Integer> reps = new ArrayList<Integer>();
        // Iterate over EditText objects and get all viable reps
        for(int i = 0; i < editTextArrayList.size(); i++){
            if(!editTextArrayList.get(i).getText().toString().equals(""))
                reps.add(Integer.parseInt(editTextArrayList.get(i).getText().toString()));
        }
        dupExercise.setReps(reps);
        dupExercise.setSets(reps.size());   // True number of reps

        dupExercise.setWorkoutID(workout.getId());

        ExerciseDAO exerciseDAO = new ExerciseDAO();
        try{
            // Creating a copy of that exercise to add to this workout.
            // Simply updating the workout ID will not work -- That will remove the exercise from
            // the previous ID's workout.
            exerciseDAO.Create(dupExercise, this);
            Intent i = ApplicationParents.getInstance().parents.pop();
            i.putExtra("Workout", workout);
            i.putExtra("Exercise", exercise);
            i.putExtra("User", user);
            startActivity(i);
        }catch (Exception ex){
            Log.e(TAG, "addExerciseToWorkout: " + ex.getMessage());

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            Intent i = ApplicationParents.getInstance().parents.pop();
            i.putExtra("Workout", workout);
            i.putExtra("Exercise", exercise);
            i.putExtra("User", user);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
