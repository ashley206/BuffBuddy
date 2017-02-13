package com.fractaltechnologies.ashleyw.buffbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        // TODO: A Workout obj is going to be passed into here in order to retrieve the Workout
        // that is associated with this new exercise

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
    }

    void addExercise(View v){
        // So many values
        final EditText etName = (EditText)findViewById(R.id.etExerciseName);
        final EditText etSets = (EditText)findViewById(R.id.etSets);
        final Spinner spPMG = (Spinner)findViewById(R.id.spTargetMuscleP);
        final Spinner spSMG = (Spinner)findViewById(R.id.spTargetMuscleS);
        final EditText etRep1 = (EditText)findViewById(R.id.etRep1);
        final EditText etRep2 = (EditText)findViewById(R.id.etRep2);
        final EditText etRep3 = (EditText)findViewById(R.id.etRep3);
        final EditText etRep4 = (EditText)findViewById(R.id.etRep4);
        final EditText etRep5 = (EditText)findViewById(R.id.etRep5);
        final EditText etRep6 = (EditText)findViewById(R.id.etRep6);

        String name = etName.getText().toString();
        int sets = Integer.parseInt(etSets.getText().toString());
        String PMG = spPMG.getSelectedItem().toString();
        String SMG = spSMG.getSelectedItem().toString();
        int rep1 = Integer.parseInt(etRep1.getText().toString());
        int rep2 = Integer.parseInt(etRep2.getText().toString());
        int rep3 = Integer.parseInt(etRep3.getText().toString());
        int rep4 = Integer.parseInt(etRep4.getText().toString());
        int rep5 = Integer.parseInt(etRep5.getText().toString());
        int rep6 = Integer.parseInt(etRep6.getText().toString());


    }
}
