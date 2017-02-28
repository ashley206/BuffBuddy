package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ProgressReportActivity extends AppCompatActivity {

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
        // TODO: Here, we want all of the previous points in the Exercise graph
        // Every time an exercise is done we want to note all of the new weights
        // input by the user
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,1),
                new DataPoint(2,3),
                new DataPoint(4,1)
        });
        graphView.addSeries(series);
    }
}
