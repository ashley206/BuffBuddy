package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class ExerciseDAO implements IDAO<Exercise> {

    DBAdapter db;
    private static final String TAG = "ExerciseDAO";
    @Override
    public void Create(Exercise exercise, Context context){

        ContentValues values = getValues(exercise);
        try {
            db = new DBAdapter(context);
            db.openWrite().Create("EXERCISE", values);
        }catch (SQLiteException ex){
            Log.e(TAG, "Create: " + ex.getMessage());
            db.close();
        }
        db.close();
    }

    @Override
    public void Update(Exercise exercise, Context context){
        ContentValues values = getValues(exercise);
        try{
            db = new DBAdapter(context);
            String selectionArgs[] = { String.valueOf(exercise.getID()) };
            db.openWrite().Update("EXERCISE", values, " ID = ?", selectionArgs);
        }
        catch (SQLiteException ex){
            Log.e(TAG, "Update: " + ex.getMessage());
            db.getDBInstance().endTransaction();
            db.close();
        }
        db.close();
    }

    @Override
    public void Delete(Exercise exercise, Context context){
        ContentValues values = getValues(exercise);
        try{
            db = new DBAdapter(context);
            db.openWrite();
            String whereArgs[] = { String.valueOf(exercise.getID()) };
            db.openWrite().Delete("EXERCISE", " ID = ?", whereArgs);
        }
        catch (SQLiteException ex){
            Log.e(TAG, "Delete: " + ex.getMessage());
            db.close();
        }
        db.close();
    }

    @Override
    public Exercise FindByName(String name, Context context){
        DBAdapter dbAdapter = new DBAdapter(context);
        String sql = "SELECT * FROM EXERCISE WHERE NAME = ?";
        Exercise exercise = null;
        try {
            Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, new String[]{name});
            if (c.moveToFirst()) {
                ArrayList<Integer> reps = new ArrayList<Integer>();
                for (int i = 0; i < 6; i++) {
                    reps.add(Integer.parseInt(c.getString(c.getColumnIndex("REP" + String.valueOf(i)))));
                }
                exercise = new Exercise(
                        c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("NAME")),
                        reps,
                        Integer.parseInt(c.getString(c.getColumnIndex("SETS"))),
                        TargetMuscle.fromString(c.getString(c.getColumnIndex("PRIMARY_TARGET_MUSCLE"))),
                        TargetMuscle.fromString(c.getString(c.getColumnIndex("SECONDARY_TARGET_MUSCLE"))),
                        Integer.parseInt(c.getString(c.getColumnIndex("WORKOUT_ID")))
                );
                reps.clear();
            }
            c.close();
        }catch (SQLiteException ex){
            Log.e(TAG, "FindByName: " + ex.getMessage());
        }
        return exercise;
    }

    public ArrayList<Exercise> FetchAllNotInWorkout(Context context, int workoutID){
        String sql = "SELECT * FROM EXERCISE WHERE WORKOUT_ID <> " + String.valueOf(workoutID);
        DBAdapter dbAdapter = new DBAdapter(context);
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        try {
            Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, null);
            if (c.moveToFirst()) {
                ArrayList<Integer> reps;
                do {
                    reps = new ArrayList<Integer>();
                    for (int i = 1; i <= 6; i++) {
                        String val = c.getString(c.getColumnIndex("REP" + i));
                        if (val != null) {
                            reps.add(Integer.parseInt(val));
                        }
                    }
                    Exercise exercise = new Exercise(
                            c.getInt(c.getColumnIndex("ID")),
                            c.getString(c.getColumnIndex("NAME")),
                            reps,
                            Integer.parseInt(c.getString(c.getColumnIndex("SETS"))),
                            TargetMuscle.fromString(c.getString(c.getColumnIndex("PRIMARY_TARGET_MUSCLE"))),
                            TargetMuscle.fromString(c.getString(c.getColumnIndex("SECONDARY_TARGET_MUSCLE"))),
                            Integer.parseInt(c.getString(c.getColumnIndex("WORKOUT_ID")))
                    );
                    exercises.add(exercise);
                } while (c.moveToNext());
            }
            c.close();
        }catch (SQLiteException ex){
            Log.e(TAG, "FetchAllNotInWorkout: " + ex.getMessage());
        }
        return exercises;
    }

    @Override
    public ArrayList<Exercise> FetchAll(Context context){
        String sql = "SELECT * FROM EXERCISE";
        DBAdapter dbAdapter = new DBAdapter(context);
        ArrayList<Exercise> exercises = null;
        try {
            Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, null);
            exercises = new ArrayList<Exercise>();
            if (c.moveToFirst()) {
                ArrayList<Integer> reps;
                do {
                    reps = new ArrayList<Integer>();
                    for (int i = 1; i <= 6; i++) {
                        String val = c.getString(c.getColumnIndex("REP" + i));
                        if (val != null) {
                            reps.add(Integer.parseInt(val));
                        }
                    }
                    Exercise exercise = new Exercise(
                            c.getInt(c.getColumnIndex("ID")),
                            c.getString(c.getColumnIndex("NAME")),
                            reps,
                            Integer.parseInt(c.getString(c.getColumnIndex("SETS"))),
                            TargetMuscle.fromString(c.getString(c.getColumnIndex("PRIMARY_TARGET_MUSCLE"))),
                            TargetMuscle.fromString(c.getString(c.getColumnIndex("SECONDARY_TARGET_MUSCLE"))),
                            Integer.parseInt(c.getString(c.getColumnIndex("WORKOUT_ID")))
                    );
                    exercises.add(exercise);
                } while (c.moveToNext());
            }
            c.close();
        }
        catch (SQLiteException ex){
            Log.e(TAG, "FetchAll: " + ex.getMessage());
        }
        return exercises;
    }

    private ContentValues getValues(Exercise e){
        ContentValues values = new ContentValues();
        String primary, secondary;
        values.put("NAME", e.getName());
        if(e.getPrimaryTargetMuscle() != null) {
            primary = e.getPrimaryTargetMuscle().toString();
        }
        else{
            primary = null;
        }
        if(e.getSecondaryTargetMuscle() != null) {
            secondary = e.getSecondaryTargetMuscle().toString();
        }
        else{
            secondary = null;
        }
        values.put("PRIMARY_TARGET_MUSCLE", primary );
        values.put("SECONDARY_TARGET_MUSCLE", secondary);
        values.put("SETS", e.getSets());
        List<Integer> r = e.getReps();
        for(int i = 0; i < r.size(); i++){
            String str_i = String.valueOf(i + 1);
            values.put("REP" + str_i, r.get(i));
        }
        values.put("WORKOUT_ID", e.getWorkoutID());
        return values;
    }
}
