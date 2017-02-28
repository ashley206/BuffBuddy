package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class SelectWorkoutActivity extends AppCompatActivity {

    User user;
    WorkoutDAO workoutDAO;
    WorkoutAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout);

        // Retrieve the user
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("User");

        // Retrieve list of workouts
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        workoutDAO = new WorkoutDAO();
        workouts = workoutDAO.FetchAll(this);

        if(workouts != null) {
            // Create unique adapter to convert array to views
            adapter = new WorkoutAdapter(this, workouts);

            // Attach adapter to ListView
            final ListView listView = (ListView) findViewById(R.id.lvWorkouts);
            listView.setAdapter(adapter);

            // When you select a row in the listview, the selected workout will open in the next screen.
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Workout workout = (Workout) listView.getItemAtPosition(position);
                    Intent i = new Intent(SelectWorkoutActivity.this, EditWorkoutActivity.class);
                    i.putExtra("Workout", workout);
                    startActivity(i);
                }
            });
        }

        // Back button will now direct back to Home Page
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Grab the menu and inflate it
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        // Find the proper item and put it in the search view
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    // COMPLETE
    public void CreateWorkout(View v){
        Intent i = new Intent(SelectWorkoutActivity.this, CreateWorkoutActivity.class);
        i.putExtra("User", user);
        startActivity(i);
    }


}
