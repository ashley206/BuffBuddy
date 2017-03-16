package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;

public class ProgressReportActivity extends AppCompatActivity {

    private final static String TAG = "ProgressReportActivity";
    DBAdapter dbAdapter;
    Exercise exercise;
    GraphView graphView;
    ArrayList<Exercise> exercises;
    LineGraphSeries<DataPoint> series;
    TextView tvExerciseName;
    TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report);

        dbAdapter = new DBAdapter(this);

        // Retrieve exercises
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        exercises = exerciseDAO.FetchAll(this);

        // Initialize textViews used in this page
        tvExerciseName = (TextView)findViewById(R.id.tvExerciseName);
        tvNoData = (TextView)findViewById(R.id.tvNoData);

        final Spinner spinnerExercise = (Spinner)findViewById(R.id.spExercises);

        // Create an ArrayAdapter using the the default spinner layout
        ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(
                getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item,
                exercises);
        // Specifying the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply similar adapters to both spinners
        spinnerExercise.setAdapter(adapter);

        // Override methods that will act as event handlers for changing the graph's data
        spinnerExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exercise = (Exercise) spinnerExercise.getSelectedItem();
                tvExerciseName.setText(exercise.getName());
                InitializeGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // For now do nothing
            }
        });

        graphView = (GraphView)findViewById(R.id.graphExercise);
        InitializeSeries();
        graphView.addSeries(series);
    }

    private void InitializeSeries(){
        series = new LineGraphSeries<>();
        series.setTitle("Progress");
        series.setDrawDataPoints(true);
        series.setThickness(8);
        series.setDataPointsRadius(14);
    }

    private void InitializeGraph(){
        // Only remove a series if the graphview has data
        if(!graphView.getSeries().isEmpty()) {
            // Remove the last series from the graph
            graphView.removeSeries(series);
        }
        // Reset the information in the graph
        InitializeSeries();
        try {
            String sql = "SELECT DATE_COMPLETED, WEIGHT FROM PROGRESS_HISTORY WHERE EXERCISE_ID = " + exercise.getID();
            Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, null);
            double i = 0, weight = 0;

            if(c.moveToFirst()) {
                do {
                    // TODO: Eventually, attempt to get dates visible on x-axis (http://www.android-graphview.org/dates-as-labels/)
                    weight = c.getDouble(c.getColumnIndex("WEIGHT"));
                    series.appendData(new DataPoint(i++, weight), false, 10);
                } while (c.moveToNext());

                graphView.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);

                graphView.getViewport().setMinX(0);
                graphView.getViewport().setMinY(0);
                // Give "padding" to the graph
                graphView.getViewport().setMaxX(i + 1);
                graphView.getViewport().setMaxY(weight + 10);
                graphView.getViewport().setScalable(true);
            }
            else{
                graphView.setVisibility(View.INVISIBLE);
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception ex){
            Log.e(TAG, "InitializeGraph: " + ex.getMessage());
        }
        // Graph the information
        graphView.addSeries(series);
    }
}
