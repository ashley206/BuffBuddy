package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class ExerciseDAO implements IDAO<Exercise> {

    DBAdapter db;

    @Override
    public void Create(Exercise e, Context c){

        ContentValues values = getValues(e);
        try {
            db = new DBAdapter(c);
            db.openWrite();
            db.getDBInstance().insert("EXERCISE", null, values);
        }catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION MESSAGE: " + ex.getMessage());
            db.close();
        }
        db.close();
    }

    @Override
    public void Update(Exercise e, Context c){
        ContentValues values = getValues(e);
        try{
            db = new DBAdapter(c);
            db.openWrite();
            String selectionArgs[] = { String.valueOf(e.getID()) };
            db.getDBInstance().update("EXERCISE", values, " ID = ?", selectionArgs);
        }
        catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION MESSAGE: " + ex.getMessage());
            db.close();
        }
        db.close();
    }

    @Override
    public void Delete(Exercise e, Context c){
        ContentValues values = getValues(e);
        try{
            db = new DBAdapter(c);
            db.openWrite();
            String selectionArgs[] = { String.valueOf(e.getID()) };
            db.getDBInstance().delete("EXERCISE", " ID = ?", selectionArgs);
        }
        catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION MESSAGE: " + ex.getMessage());
            db.close();
        }
        db.close();
    }
    @Override
    public List<Exercise> FindByName(String name){
        List<Exercise> exercises = new Vector<Exercise>();
//        try{
//            // TODO: New DBAdapter here
//            db.openRead();
//            Cursor c = db.getDBInstance().rawQuery("SELECT * FROM EXERCISE", null);
//            Exercise ex;
//            if(c.moveToFirst()) {
//                do {
//                    List<Integer> i = new ArrayList<Integer>();
//                    i.add(Integer.parseInt(c.getString(c.getColumnIndex("REP1"))));
//                    i.add(Integer.parseInt(c.getString(c.getColumnIndex("REP2"))));
//                    i.add(Integer.parseInt(c.getString(c.getColumnIndex("REP3"))));
//                    i.add(Integer.parseInt(c.getString(c.getColumnIndex("REP4"))));
//                    i.add(Integer.parseInt(c.getString(c.getColumnIndex("REP5"))));
//                    i.add(Integer.parseInt(c.getString(c.getColumnIndex("REP6"))));
//
//                    List<TargetMuscle> m = new ArrayList<TargetMuscle>();
//                    // TODO: Need to convert these back to TargetMuscles
//                    //m.add(c.getString(c.getColumnIndex("PRIMARY_TARGET_MUSCLE")));
//                    //m.add(c.getString(c.getColumnIndex("PRIMARY_TARGET_MUSCLE")));
////                    ex = new Exercise(
////                    c.getString(c.getColumnIndex("NAME")),
////                            c.getString(c.getColumnIndex("SETS")),
////                            i,
//
//
//
//                    );
//                }while(c.moveToNext());
//            }
//
//        }catch (SQLiteException ex){
//            Log.e("TAG", "EXCEPTION MESSAGE: " + ex.getMessage());
//            db.close();
//        }
//        db.close();
        return exercises;
    }
    @Override
    public List<Exercise> FetchAll(){
        List<Exercise> exercises = new Vector<Exercise>();

        return exercises;
    }

    private ContentValues getValues(Exercise e){
        ContentValues values = new ContentValues();
        values.put("NAME", e.getName());
        List<TargetMuscle> m = e.getTargetMuscles();
        values.put("PRIMARY_TARGET_MUSCLE", m.get(0).toString());
        values.put("SECONDARY_TARGET_MUSCLE", m.get(1).toString());
        values.put("SETS", e.getSets());
        List<Integer> r = e.getReps();
        for(int i = 0; i < r.size(); i++){
            values.put("REP" + i, r.get(i));
        }
        values.put("WORKOUT_ID", e.getWorkoutID());
        return values;
    }
}
