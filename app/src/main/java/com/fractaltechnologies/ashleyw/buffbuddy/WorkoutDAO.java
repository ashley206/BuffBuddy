package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Ashley H on 1/19/2017.
 */


public class WorkoutDAO implements IDAO<Workout> {
    @Override
    public void Create(Workout workout, Context context) {
        // TODO: This should check if a workout name already exists
        DBAdapter dbAdapter = new DBAdapter(context);
        ContentValues values = new ContentValues();
        values.put("NAME", workout.GetName());
        dbAdapter.openWrite().Create("WORKOUT", values);
    }

    @Override
    public void Update(Workout workout, Context c){
        DBAdapter dbAdapter = new DBAdapter(c);
        ContentValues values = new ContentValues();
        values.put("NAME", workout.GetName());
        String [] whereArgs = { String.valueOf(workout.GetId()) };
        dbAdapter.openWrite().Update("WORKOUT", values, "ID = ?", whereArgs);
    }
    @Override
    public void Delete(Workout workout, Context context){
        DBAdapter dbAdapter = new DBAdapter(context);
        String [] whereArgs = { String.valueOf(workout.GetId()) };
        dbAdapter.openWrite().Delete("WORKOUT", "ID = ?", whereArgs);
    }
    @Override
    public ArrayList<Workout> FindByName(String name, Context context){
        DBAdapter dbAdapter = new DBAdapter(context);
        String sql = "SELECT * FROM WORKOUT WHERE NAME = ?";
        Cursor c = dbAdapter.getDBInstance().rawQuery(sql, new String [] {name});
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        if(c.moveToFirst()) {
            do {
                workouts.add(new Workout(c.getString(c.getColumnIndex("NAME")),
                        Integer.parseInt(c.getString(c.getColumnIndex("ID"))))
                );
            } while (c.moveToNext());
        }
        c.close();
        return workouts;
    }
    @Override
    public ArrayList<Workout> FetchAll(Context context){
        DBAdapter dbAdapter = new DBAdapter(context);
        String sql = "SELECT * FROM WORKOUT";
        Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, null);
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        if(c.moveToFirst()) {
            do {
                workouts.add(new Workout(c.getString(c.getColumnIndex("NAME")),
                        Integer.parseInt(c.getString(c.getColumnIndex("ID"))))
                );
            } while (c.moveToNext());
        }
        c.close();
        return workouts;
    }

    public ArrayList<Exercise> FetchExercisesInWorkout(int workoutID, Context context){
        DBAdapter dbAdapter = new DBAdapter(context);
        String sql = "SELECT * FROM EXERCISE WHERE WORKOUT_ID = ?";
        Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, new String [] { String.valueOf(workoutID) });
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        if(c.moveToFirst()) {
            ArrayList<Integer> reps = new ArrayList<Integer>();
            do {
                for(int i = 0; i < 6; i++){
                    reps.add(Integer.parseInt(c.getString(c.getColumnIndex("REP" + String.valueOf(i)))));
                }
                Exercise exercise = new Exercise(c.getString(c.getColumnIndex("NAME")),
                        reps,
                        Integer.parseInt(c.getString(c.getColumnIndex("SETS"))),
                        TargetMuscle.valueOf(c.getString(c.getColumnIndex("PRIMARY_TARGET_MUSCLE"))),
                        TargetMuscle.valueOf(c.getString(c.getColumnIndex("SECONDARY_TARGET_MUSCLE"))),
                        Integer.parseInt(c.getString(c.getColumnIndex("WORKOUT_ID")))
                );
                exercises.add(exercise);
                reps.clear();   // Repopulate with data for each exercise
            }while(c.moveToNext());
        }
        c.close();
        return exercises;
    }
}
