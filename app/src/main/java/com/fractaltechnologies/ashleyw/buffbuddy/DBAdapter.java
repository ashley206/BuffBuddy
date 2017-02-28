package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/30/2017.
 */

public class DBAdapter {
    // SQLiteHelper Version should be same for all users
    //final static int DB_VERSION = 3;

    private static String DB_PATH = null;
    public SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    private final Context m_context;

    // Public constructor to initialize the object.
    public DBAdapter(Context context){
        // Store context for later use
        m_context = context;
        dbHelper = new SQLiteHelper(context);
        //DB_PATH = m_context.getDatabasePath(DB_NAME).getAbsolutePath();
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
        values.put("PRIMARY_TARGET_MUSCLE", ex.getPrimaryTargetMuscle().toString());
        values.put("SECONDARY_TARGET_MUSCLE", ex.getSecondaryTargetMuscle().toString());
        for (int i = 0; i < ex.getReps().size(); i++) {
            values.put("REP" + i, ex.getReps().get(i));
        }

        this.openWrite();
        db = this.getDBInstance();
        db.insert("EXERCISE", null, values);
        db.close();
    }

    public User Login(String username, String password){
        User user = null;
        SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase();
        String sql = "SELECT USERNAME, EMAIL," +
                "FIRSTNAME, LASTNAME, ID, GOOGLEACCOUNT " +
                "FROM USER WHERE USERNAME = '" + username + "'";
        try {
            Cursor c = db.rawQuery(sql, null);
            if (c.moveToFirst()) {
                if(password == c.getString(c.getColumnIndex("PASSWORD"))) {
                    user = new User();
                    user.setFirstName(c.getString(c.getColumnIndex("FIRSTNAME")));
                    user.setLastName(c.getString(c.getColumnIndex("LASTNAME")));
                    boolean google = Boolean.parseBoolean(c.getString(c.getColumnIndex("GOOGLEACCOUNT")));
                    user.setID(c.getInt(c.getColumnIndex("ID")));
                    user.setGoogleAccount(google);
                    user.setUsername(c.getString(c.getColumnIndex("USERNAME")));
                }
            }
        }catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION: " + ex.getMessage());
        }
        return user;
    }

    public boolean Register(String username, String email, String password, String fName, String lName ){
        boolean success = false;
        try {
            // We must ensure that this is a unique entry in the database
            SQLiteDatabase db = new SQLiteHelper(m_context).getWritableDatabase();
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

    public void Create(String tableName, ContentValues values){
        db.insert(tableName, null, values);
    }

    public void Update(String tableName, ContentValues values, String whereClause, String [] whereArgs){
        db.update(tableName, values, whereClause, whereArgs);
    }

    public void Delete(String tableName, String whereClause, String [] whereArgs){
        db.delete(tableName, whereClause, whereArgs);
    }

}
