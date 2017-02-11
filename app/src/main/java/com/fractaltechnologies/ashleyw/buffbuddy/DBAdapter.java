package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/30/2017.
 */

public class DBAdapter {
    // SQLiteHelper Version should be same for all users
    final static int DB_VERSION = 3;
    private static String DB_NAME = "buffbuddy.sqlite";
    private static String DB_PATH = null;
    public SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    private final Context m_context;

    public String getDBName(){
        return DB_NAME;
    }

    // Public constructor to initialize the object.
    public DBAdapter(Context context){
        // Store context for later use
        m_context = context;
        dbHelper = new SQLiteHelper(context, DB_NAME, null, DB_VERSION);
        DB_PATH = m_context.getDatabasePath(DB_NAME).getAbsolutePath();
    }

    public DBAdapter openWrite(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public DBAdapter openRead(){
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }

    public SQLiteDatabase getDBInstance(){
        return db;
    }

    public void Add(Exercise ex){
        ContentValues values = new ContentValues();
        values.put("NAME", ex.getName());
        values.put("SETS", ex.getSets());
        values.put("PRIMARY_TARGET_MUSCLE", (ex.getTargetMuscles()).get(0).toString());
        values.put("SECONDARY_TARGET_MUSCLE", (ex.getTargetMuscles()).get(1).toString());
        for (int i = 0; i < ex.getReps().size(); i++) {
            values.put("REP" + i, ex.getReps().get(i));
        }

        this.openWrite();
        db = this.getDBInstance();
        db.insert("EXERCISE", null, values);
        db.close();
    }

    public User Login(String username, String password){
        // TODO: Using sqlcipher, encipher again here so the raw text pwd isn't sent
        User user = null;
        SQLiteDatabase db = new SQLiteHelper(m_context, DB_NAME, null, DB_VERSION).getReadableDatabase();
        String sql = "SELECT USERNAME, EMAIL," +
                "FIRSTNAME, LASTNAME, GOOGLEACCOUNT " +
                "FROM USER WHERE USERNAME = '" + username + "'";
        Cursor c = db.rawQuery(sql, null);
        if(c.moveToFirst()){
            user = new User();
            user.setFirstName(c.getString(c.getColumnIndex("FIRSTNAME")));
            user.setLastName(c.getString(c.getColumnIndex("LASTNAME")));
            boolean google = Boolean.parseBoolean(c.getString(c.getColumnIndex("GOOGLEACCOUNT")));
            user.setGoogleAccount(google);
            user.setUsername(c.getString(c.getColumnIndex("USERNAME")));
        }
        return user;
    }

    public boolean Register(String username, String email, String password, String fName, String lName ){
        boolean success = false;
        try {
            // We must ensure that this is a unique entry in the database
            SQLiteDatabase db = new SQLiteHelper(m_context, DB_NAME, null, DB_VERSION).getWritableDatabase();
            String sql = String.format("SELECT EMAIL FROM USER WHERE EMAIL = '%s'", username);
            Cursor c = db.rawQuery(sql, null);
            // Ensure no values exist
            if (!c.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put("FIRSTNAME", fName);
                values.put("LASTNAME", lName);
                values.put("USERNAME", username);
                values.put("EMAIL", email);
                values.put("PASSWORD", password);
                db.insert("USER", null, values);
                success = true;
            }
            c.close();
        }catch (Exception ex){
            Log.e("TAG", "EXCEPTION MESSAGE: " + ex.getMessage());
        }
        return success;
    }

// TODO: implement a do{} while(c.movetonext())
    public List<Workout> FetchAll(){
        SQLiteDatabase db = new SQLiteHelper(m_context, DB_NAME, null, DB_VERSION).getReadableDatabase();
        String sql = "SELECT * FROM WORKOUT";
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        List<Workout> workouts = new Vector<Workout>();
        // TODO: Check that this doesn't move to the next one right away
        while(c.moveToNext()){
            // TODO: How do we retrieve the Exercises associated?
            workouts.add(new Workout(c.getString(c.getColumnIndex("NAME"))));
        }
        c.close();
        return workouts;
    }

    public List<Workout> FetchByName(String name){
        SQLiteDatabase db = new SQLiteHelper(m_context, DB_NAME, null, DB_VERSION).getReadableDatabase();
        String sql = "SELECT * FROM WORKOUT WHERE NAME = ?";
        Cursor c = db.rawQuery(sql, new String [] {name});
        c.moveToFirst();
        List<Workout> workouts = new Vector<Workout>();
        // TODO: Check that this doesn't move to the next one right away
        while(c.moveToNext()){
            // TODO: How do we retrieve the Exercises associated?
            workouts.add(new Workout(c.getString(c.getColumnIndex("NAME"))));
        }
        c.close();
        return workouts;
    }
}
