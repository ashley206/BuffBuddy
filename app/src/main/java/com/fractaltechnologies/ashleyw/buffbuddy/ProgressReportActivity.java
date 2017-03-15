package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;

public class ProgressReportActivity extends AppCompatActivity {

    private final static String TAG = "ProgressReportActivity";
    Exercise exercise;
    GraphView graphView;
    ArrayList<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report);

        Intent i = getIntent();

        ExerciseDAO exerciseDAO = new ExerciseDAO();
        exercises = exerciseDAO.FetchAll(this);
        Spinner spinnerExercise = (Spinner)findViewById(R.id.spExercises);
        // Create an ArrayAdapter using the the default spinner layout
        ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(
                getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item,
                exercises);
        // Specifying the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply similar adapters to both spinners
        spinnerExercise.setAdapter(adapter);

    }

    private void InitializeGraph(){
        graphView = (GraphView)findViewById(R.id.graphExercise);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        Date date = null;

        try {
            String sql = "SELECT DATESUBMITTED, WEIGHT FROM PROGRESS WHERE EXERCISE_ID = " + exercise.getID();
            DBAdapter dbAdapter = new DBAdapter(this);
            Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, null);
            if (c.moveToFirst()) {
                // Convert from ms to s
                date = new Date(c.getLong(c.getColumnIndex("DATEDUBMITTED") * 1000));
                float weight = c.getFloat(c.getColumnIndex("WEIGHT"));
                // TODO: Ensure this is the behavior we want.
                series.appendData(new DataPoint(date, weight), false, 10);
            }
        }catch (SQLiteException ex){
            Log.e(TAG, "InitializeGraph: " + ex.getMessage());
        }
        // Graph the information
        graphView.addSeries(series);
    }
}
