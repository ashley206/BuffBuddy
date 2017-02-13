package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateWorkoutActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        // You can add the user as a value in the database to be associated with the workout
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");
    }

    public void SaveWorkout(View v){
        // This will create the workout in the database
        EditText etName = (EditText)findViewById(R.id.etName);
        String name = etName.getText().toString();
        if(!name.isEmpty()) {
            Workout workout = new Workout(name);
            WorkoutDAO workoutDAO = new WorkoutDAO();
            // TODO: What if this fails? (duplicates, other reasons)
            workoutDAO.Create(workout, this);
            workout = workoutDAO.FindByName(workout.GetName(), this);   // This will also retrieve the ID
try {
    Intent i = new Intent(CreateWorkoutActivity.this, EditWorkoutActivity.class);
    i.putExtra("Workout", workout);
    startActivity(i);
}catch (Exception ex){
    String exc = ex.getMessage();
}
        }
        else{
            Toast.makeText(CreateWorkoutActivity.this, "Workout name is required.", Toast.LENGTH_LONG).show();
        }
    }
}
