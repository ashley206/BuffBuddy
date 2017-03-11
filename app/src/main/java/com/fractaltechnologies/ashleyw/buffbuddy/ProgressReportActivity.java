package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Date;
import java.util.ArrayList;

public class ProgressReportActivity extends AppCompatActivity {

    private final static String TAG = "ProgressReportActivity";
    Exercise exercise;
    GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report);

        Intent i = getIntent();
        exercise = (Exercise)i.getSerializableExtra("Exercise");

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
