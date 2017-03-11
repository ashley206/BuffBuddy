package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.lang.annotation.Target;
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
        values.put("USER_ID", String.valueOf(((CreateWorkoutActivity) context).user.getID()));
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
    public Workout FindByName(String name, Context context){
        DBAdapter dbAdapter = new DBAdapter(context);
        String sql = "SELECT * FROM WORKOUT WHERE NAME = ?";
        Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, new String[]{name});
        Workout workout = null;
        try {
            if (c.moveToFirst()) {
                workout = new Workout(c.getString(c.getColumnIndex("NAME")),
                        c.getInt(c.getColumnIndex("ID")),
                        c.getInt(c.getColumnIndex("USER_ID")));
            }
        }catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION: " + ex.getMessage());
        }
        c.close();
        return workout;
    }

    @Override
    public ArrayList<Workout> FetchAll(Context context){
        DBAdapter dbAdapter = new DBAdapter(context);
        // Retrieve the user object from the existing context -- MUST exist
        String [] userID = {
                String.valueOf(((SelectWorkoutActivity) context).user.getID())
        };
        String sql = "SELECT * FROM WORKOUT WHERE USER_ID = ?";
        Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, userID);
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        if(c.moveToFirst()) {
            do {
                workouts.add(new Workout(c.getString(c.getColumnIndex("NAME")),
                        Integer.parseInt(c.getString(c.getColumnIndex("ID"))),
                        Integer.parseInt(c.getString(c.getColumnIndex("USER_ID"))))
                );
            } while (c.moveToNext());
        }
        c.close();
        // Populate Exercises inside the workout
        int workoutID = -1;
        for(int i = 0; i < workouts.size(); i++){
            workoutID = workouts.get(i).GetId();
            workouts.get(i).SetExercises(FetchExercisesInWorkout(workoutID, context));
        }

        return workouts;
    }

    public ArrayList<Exercise> FetchExercisesInWorkout(int workoutID, Context context){
        DBAdapter dbAdapter = new DBAdapter(context);
        String id = String.valueOf(workoutID);
        String sql = "SELECT * FROM EXERCISE WHERE WORKOUT_ID = " + id;
        Cursor c = dbAdapter.openRead().getDBInstance().rawQuery(sql, null);
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        if(c.moveToFirst()) {
            ArrayList<Integer> reps;// = new ArrayList<Integer>();
            do {
                reps = new ArrayList<Integer>();
                for(int i = 0; i < 6; i++){
                    String str_i = String.valueOf(i + 1);
                    if(!c.isNull(c.getColumnIndex("REP" + str_i))){
                        int rep = Integer.parseInt(c.getString(c.getColumnIndex("REP" + str_i)));
                        reps.add(rep);
                    }
                }
                TargetMuscle pm = TargetMuscle.fromString(c.getString(c.getColumnIndex("PRIMARY_TARGET_MUSCLE")));
                TargetMuscle sm = null;
                if(!c.isNull(c.getColumnIndex("SECONDARY_TARGET_MUSCLE"))) {
                    sm = TargetMuscle.fromString(c.getString(c.getColumnIndex("SECONDARY_TARGET_MUSCLE")));
                }

                Exercise exercise = new Exercise(
                        c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("NAME")),
                        reps,
                        Integer.parseInt(c.getString(c.getColumnIndex("SETS"))),
                        pm,
                        sm,
                        Integer.parseInt(c.getString(c.getColumnIndex("WORKOUT_ID")))
                );
                exercises.add(exercise);

            }while(c.moveToNext());
        }
        c.close();
        return exercises;
    }
}
