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

public class SelectExericsesActivity extends AppCompatActivity {

    Workout workout;
    User user;
    DBAdapter dbAdapter;
    ExerciseDAO exerciseDAO;
    ExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exericses);

        Intent i = getIntent();
        workout = (Workout)i.getSerializableExtra("Workout");
        user = (User)i.getSerializableExtra("User");
        /// Set up action bar to contain Up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Exercises that exists for this workout need to be populated
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        exerciseDAO = new ExerciseDAO();
        exercises = exerciseDAO.FetchAllNotInWorkout(this, workout.getId());

        if(exercises != null) {
            // Create unique adapter to convert array to views
            adapter = new ExerciseAdapter(this, exercises);

            // Attach adapter to ListView
            final ListView listView = (ListView) findViewById(R.id.lvExercises);
            listView.setAdapter(adapter);

            // When you select a row in the listview, the selected exercise will open in the next screen.
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Set this as a parent of the subclasses (of which there are different paths)
                    ApplicationParents.getInstance().parents.push(getIntent());

                    Exercise exercise = (Exercise) listView.getItemAtPosition(position);
                    Intent i = new Intent(SelectExericsesActivity.this, EditExerciseActivity.class);
                    i.putExtra("Workout", workout);
                    i.putExtra("Exercise", exercise);
                    i.putExtra("User", user);
                    startActivity(i);
                }
            });
        }
    }

    public void CreateExercise(View v){
        // Set this as a parent of the subclasses (of which there are different paths)
        ApplicationParents.getInstance().parents.push(getIntent());

        Intent i = new Intent(SelectExericsesActivity.this, CreateExerciseActivity.class);
        i.putExtra("Workout", workout);
        i.putExtra("User", user);
        startActivity(i);
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
