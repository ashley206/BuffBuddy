package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.Cursor;
import net.sqlcipher.DatabaseUtils;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;

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
    }

    public DBAdapter openWrite(){
        db = dbHelper.getWritableDatabase(dbHelper.getPassphrase());
        return this;
    }

    public DBAdapter openRead(){
        db = dbHelper.getReadableDatabase(dbHelper.getPassphrase());
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

        SQLiteDatabase.loadLibs(m_context);

        this.openWrite().getDBInstance().insert("EXERCISE", null, values);
        db.close();
    }

    // Determines if the user exists based on their email. Used exclusively for the sake
    // of Google Sign in, since all information about account we can retrieve is their
    // email address.
    public boolean UserExists(String email){
        boolean exists = false;
        SQLiteDatabase.loadLibs(m_context);

        SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase(dbHelper.getPassphrase());
        String sql = "SELECT EMAIL FROM USER WHERE EMAIL = ?";
        try {
            Cursor c = db.rawQuery(sql, new String [] { email });
            // If moveToFirst is valid, user exists
            if (c.moveToFirst()) {
                exists = true;
            }
            c.close();
        }catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION: " + ex.getMessage());
        }
        return exists;
    }

    public int getUserId(String email){
        SQLiteDatabase.loadLibs(m_context);
        int userId = -1;
        try {
            SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase(dbHelper.getPassphrase());
            Cursor c = db.rawQuery("SELECT ID FROM USER WHERE EMAIL = ?", new String[] {email});
            if(c.moveToFirst()){
                userId = c.getInt(c.getColumnIndex("ID"));
            }
            c.close();
        }
        catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION: " + ex.getMessage());
        }
        return userId;
    }

    public User GoogleLogin(String email){
        SQLiteDatabase.loadLibs(m_context);

        User user = null;
        SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase(dbHelper.getPassphrase());
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
            c.close();
        }catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION: " + ex.getMessage());
        }
        return user;
    }

    public User Login(String username, String password){
        SQLiteDatabase.loadLibs(m_context);

        User user = null;
        SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase(dbHelper.getPassphrase());
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
            c.close();
        }catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION: " + ex.getMessage());
        }
        return user;
    }

    public int GoogleRegister(String email){
        SQLiteDatabase.loadLibs(m_context);

        int userId = -1;
        try {
            // We must ensure that this is a unique entry in the database
            SQLiteDatabase db = new SQLiteHelper(m_context).getWritableDatabase(dbHelper.getPassphrase());
            String sql = "SELECT EMAIL FROM USER WHERE EMAIL = ?";
            Cursor c = db.rawQuery(sql, new String[] { email });
            // TODO: Ensure no values exist. This was MAJORLY breaking the emulator but never had issue on actual phone.
            if (!c.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put("FIRSTNAME", "");
                values.put("LASTNAME", "");
                values.put("USERNAME", "");
                values.put("EMAIL", email);
                values.put("PASSWORD", "");
                values.put("GOOGLEACCOUNT", 1);
                db.insert("USER", null, values);
            }
            c.close();
        }catch (Exception ex){
            Log.e("TAG", "EXCEPTION MESSAGE: " + ex.getMessage());
        }
        return userId;
    }

    public boolean Register(String username, String email, String password, String fName, String lName ){
        SQLiteDatabase.loadLibs(m_context);

        boolean success = false;
        try {
            // We must ensure that this is a unique entry in the database
            db = new SQLiteHelper(m_context).getReadableDatabase(dbHelper.getPassphrase());

            //Cursor c = db.query("USER", new String[] {"EMAIL"}, "EMAIL = ?", new String[] { email }, null, null, null ); //rawQuery("SELECT EMAIL FROM USER WHERE EMAIL = '" + email + "'", null);
            // Ensure no values exist
            //if (!(c.moveToFirst()) || c.getCount() == 0) {
                db = new SQLiteHelper(m_context).getWritableDatabase(dbHelper.getPassphrase());
                db.beginTransaction();
                ContentValues values = new ContentValues();
                values.put("FIRSTNAME", fName);
                values.put("LASTNAME", lName);
                values.put("USERNAME", username);
                values.put("EMAIL", email);
                values.put("PASSWORD", password);
                values.put("GOOGLEACCOUNT", 0);
                db.insertOrThrow("USER", null, values);
                db.setTransactionSuccessful();
            //}
            success = true;
        }catch (Exception ex){
            Log.e("TAG", "EXCEPTION MESSAGE: " + ex.getMessage());
        }
        finally {
            db.endTransaction();
        }
        return success;
    }

//    public PersonalLog GetLogByDate(Date date, int userId){
//        SQLiteDatabase.loadLibs(m_context);
//
//        String sql = "SELECT * FROM PERSONALLOG" +
//                "WHERE USER_ID = ? " +
//                "AND" +
//                "SUBMISSION_DATE = ?";
//        PersonalLog log = null;
//        try {
//            SQLiteDatabase db = new SQLiteHelper(m_context).getReadableDatabase(dbHelper.getPassphrase());
//            log = new PersonalLog();
//            Cursor c = db.rawQuery(sql, new String[]{String.valueOf(userId), date.toString()});
//            if (c.moveToFirst()) {
//                log.SetMessage(c.getString(c.getColumnIndex("MESSAGE")));
//                // TODO: Retrieve the ID of the workout
//                log.SetWorkout(c.getInt(c.getColumnIndex("WORKOUT_ID")));
//                String s_date = c.getString(c.getColumnIndex("SUBMISSION_DATE"));
//
//                //log.SetDate();
//            }
//        }
//        catch (SQLiteException ex){
//            Log.e("TAG", "GetLogByDate: " + ex.getMessage());
//        }
//        return log;
//    }


    public void Create(String tableName, ContentValues values){
        SQLiteDatabase.loadLibs(m_context);
        db.insert(tableName, null, values);
    }

    public void Update(String tableName, ContentValues values, String whereClause, String [] whereArgs){
        SQLiteDatabase.loadLibs(m_context);
        db.update(tableName, values, whereClause, whereArgs);
    }

    public void Delete(String tableName, String whereClause, String [] whereArgs){
        SQLiteDatabase.loadLibs(m_context);
        db.delete(tableName, whereClause, whereArgs);
    }
}
