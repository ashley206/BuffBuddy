package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
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

    // Determines if the user exists based on their email. Used exclusively for the sake
    // of Google Sign in, since all information about account we can retrieve is their
    // email address.
    public boolean UserExists(String email){
        boolean exists = false;
        SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase();
        String sql = "SELECT EMAIL FROM USER WHERE EMAIL = ?";
        try {
            Cursor c = db.rawQuery(sql, new String [] { email });
            // If moveToFirst is valid, user exists
            if (c.moveToFirst()) {
                exists = true;
            }
        }catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION: " + ex.getMessage());
        }
        return exists;
    }

    public User GoogleLogin(String email){
        User user = null;
        SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase();
        String sql = "SELECT USERNAME, PASSWORD, EMAIL," +
                "FIRSTNAME, LASTNAME, ID, GOOGLEACCOUNT " +
                "FROM USER WHERE EMAIL = ?";
        try {
            Cursor c = db.rawQuery(sql, new String [] { email });
            if (c.moveToFirst()) {
                user = new User();
                user.setFirstName(c.getString(c.getColumnIndex("FIRSTNAME")));
                user.setLastName(c.getString(c.getColumnIndex("LASTNAME")));
                boolean google = Boolean.parseBoolean(c.getString(c.getColumnIndex("GOOGLEACCOUNT")));
                user.setID(c.getInt(c.getColumnIndex("ID")));
                user.setGoogleAccount(google);
                user.setUsername(c.getString(c.getColumnIndex("USERNAME")));
            }
        }catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION: " + ex.getMessage());
        }
        return user;
    }

    public User Login(String username, String password){
        User user = null;
        SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase();
        String sql = "SELECT USERNAME, PASSWORD, EMAIL," +
                "FIRSTNAME, LASTNAME, ID, GOOGLEACCOUNT " +
                "FROM USER WHERE USERNAME = '" + username + "'";
        try {
            Cursor c = db.rawQuery(sql, null);
            if (c.moveToFirst()) {
                String pwd = c.getString(c.getColumnIndex("PASSWORD"));
                if(password.equals(pwd)) {
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

    public boolean GoogleRegister(String email){
        boolean success = false;
        try {
            // We must ensure that this is a unique entry in the database
            SQLiteDatabase db = new SQLiteHelper(m_context).getWritableDatabase();
            String sql = "SELECT EMAIL FROM USER WHERE EMAIL = ?";
            Cursor c = db.rawQuery(sql, new String[] { email });
            // Ensure no values exist
            if (!c.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put("FIRSTNAME", "");
                values.put("LASTNAME", "");
                values.put("USERNAME", "");
                values.put("EMAIL", email);
                values.put("PASSWORD", "");
                values.put("GOOGLEACCOUNT", 1);
                db.insert("USER", null, values);
                success = true;
            }
            c.close();
        }catch (Exception ex){
            Log.e("TAG", "EXCEPTION MESSAGE: " + ex.getMessage());
        }
        return success;
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

    public PersonalLog GetLogByDate(Date date, int userId){
        String sql = "SELECT * FROM PERSONALLOG" +
                "WHERE USER_ID = ? " +
                "AND" +
                "SUBMISSION_DATE = ?";
        PersonalLog log = null;
        try {
            SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase();
            log = new PersonalLog();
            Cursor c = db.rawQuery(sql, new String[]{String.valueOf(userId), date.toString()});
            if (c.moveToFirst()) {
                log.SetMessage(c.getString(c.getColumnIndex("MESSAGE")));
                // TODO: Retrieve the ID of the workout
                log.SetWorkout(c.getInt(c.getColumnIndex("WORKOUT_ID")));
                String s_date = c.getString(c.getColumnIndex("SUBMISSION_DATE"));
                
                //log.SetDate();
            }
        }
        catch (SQLiteException ex){
            Log.e("TAG", "GetLogByDate: " + ex.getMessage());
        }
        return log;
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
