package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class CreateExerciseActivity extends AppCompatActivity {
    Workout workout;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        Intent i = getIntent();
        workout = (Workout)i.getSerializableExtra("Workout");
        user = (User)i.getSerializableExtra("User");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

        // Default selection to "No selection"
        spinnerPrimary.setSelection(0);
        spinnerSecondary.setSelection(0);
    }

    public void CreateExercise(View v){
        // So many values
        final EditText etName = (EditText)findViewById(R.id.etExerciseName);
        final Spinner spPMG = (Spinner)findViewById(R.id.spTargetMuscleP);
        final Spinner spSMG = (Spinner)findViewById(R.id.spTargetMuscleS);
        final EditText etRep1 = (EditText)findViewById(R.id.etRep1);
        final EditText etRep2 = (EditText)findViewById(R.id.etRep2);
        final EditText etRep3 = (EditText)findViewById(R.id.etRep3);
        final EditText etRep4 = (EditText)findViewById(R.id.etRep4);
        final EditText etRep5 = (EditText)findViewById(R.id.etRep5);
        final EditText etRep6 = (EditText)findViewById(R.id.etRep6);

        Exercise exercise = new Exercise(etName.getText().toString());

        // Ensure values in target muscle spinners are valid
        if(spPMG.getSelectedItemPosition() != 0){
            exercise.setPrimaryTargetMuscle(TargetMuscle.fromString(spPMG.getSelectedItem().toString()));
        }
        else{
            exercise.setPrimaryTargetMuscle(null);
        }
        if(spSMG.getSelectedItemPosition() != 0){
            exercise.setSecondaryTargetMuscle(TargetMuscle.fromString(spSMG.getSelectedItem().toString()));
        }
        else{
            exercise.setSecondaryTargetMuscle(null);
        }
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
        exercise.setWorkoutID(workout.getId());

        ExerciseDAO exerciseDAO = new ExerciseDAO();
        exerciseDAO.Create(exercise, this);

        Intent i = new Intent(CreateExerciseActivity.this, EditWorkoutActivity.class);
        i.putExtra("Workout", workout);
        i.putExtra("User", user);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            Intent i = ApplicationParents.getInstance().parents.pop();
            i.putExtra("Workout", workout);
            i.putExtra("User", user);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
