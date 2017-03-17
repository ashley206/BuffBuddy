package com.fractaltechnologies.ashleyw.buffbuddy;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.content.res.AssetManager;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.SQLException;
import net.sqlcipher.Cursor;
import net.sqlcipher.DatabaseUtils;
import net.sqlcipher.database.SQLiteException;

import java.io.*;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String passphrase = "b4h5d231963";

    private final static String TAG = "SQLiteHelper";
    private static String DATABASE_PATH = "/data/user/0/com.fractaltechnologies.ashleyw.buffbuddy/databases/";
    private static final String DATABASE_NAME = "buffbuddy.sqlite";
    private static final int DATABASE_VERSION = 3;
    private Context m_context;

    public SQLiteHelper(Context context ){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        m_context = context;
    }

    // Overridden method to create the sql database tables necessary
    // for this application.
    @Override
    public void onCreate(SQLiteDatabase db){
        try {
            String createSQL = "CREATE TABLE IF NOT EXISTS USER(\n" +
                    "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    FIRSTNAME TEXT,\n" +
                    "    LASTNAME TEXT,\n" +
                    "    USERNAME TEXT,\n" +
                    "    EMAIL TEXT,\n" +
                    "    PASSWORD TEXT,\n" +
                    "    GOOGLEACCOUNT INTEGER\n" +
                    "    )";
            db.execSQL(createSQL);

            createSQL = "CREATE TABLE IF NOT EXISTS WORKOUT(\n" +
                    "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    USER_ID INTEGER, \n" +
                    "    NAME TEXT,\n" +
                    "    FOREIGN KEY(USER_ID) REFERENCES USER(ID)\n" +
                    "    )";
                    db.execSQL(createSQL);

            createSQL = "CREATE TABLE IF NOT EXISTS EXERCISE (\n" +
                    "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    NAME TEXT,\n" +
                    "    PRIMARY_TARGET_MUSCLE TEXT,\n" +
                    "    SECONDARY_TARGET_MUSCLE TEXT,\n" +
                    "    SETS INTEGER,\n" +
                    "    REP1 INTEGER, REP2 INTEGER, REP3 INTEGER,\n" +
                    "    REP4 INTEGER, REP5 INTEGER, REP6 INTEGER,\n" +
                    "    WORKOUT_ID INTEGER,\n" +
                    "    FOREIGN KEY(WORKOUT_ID) REFERENCES WORKOUT(ID)\n" +
                    "    )";
                    db.execSQL(createSQL);

            createSQL = "CREATE TABLE IF NOT EXISTS PROGRESS_HISTORY(\n" +
                    "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    EXERCISE_ID INTEGER,\n" +
                    "    USER_ID INTEGER,\n" +
                    "    WEIGHT REAL,\n" +
                    "    DATE_COMPLETED DATE,\n" +
                    "    FOREIGN KEY (EXERCISE_ID) REFERENCES EXERCISE(ID),\n" +
                    "    FOREIGN KEY (USER_ID) REFERENCES USER(ID)\n" +
                    "    )";
                    db.execSQL(createSQL);

            createSQL = "CREATE TABLE IF NOT EXISTS REPORT(\n" +
                    "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    COMMENT TEXT\n" +
                    "    )";
            db.execSQL(createSQL);
        }catch (SQLiteException ex){
            Log.e(TAG, "OnCreate: " + ex.getMessage());
        }
    }

    // Overridden method to handle upgrades to the database, if any.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        if(newVersion > oldVersion){
//            // New cases can be added as the database changes.
//            switch (oldVersion){
//                // Cases will not 'break' so they can be subsequently upgraded
//                case 1:
//                    // 1/27/17: create_v2.sql modified the Exercise table to include 6
//                    // optional columns for specific rep amounts.
//                    executeSQLScript(db, "create_v2.sql");
//                case 2:
//                    executeSQLScript(db, "create_v3.sql");
//            }
//            onCreate(db);
//        }
    }

    // Takes a given SQL file and runs the code SQL statements in that file.
    public void executeSQLScript(SQLiteDatabase db, String script){
        AssetManager assetManager = m_context.getAssets();
        InputStream inputStream = null;
        int length = 0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buffer[] = new byte[1024];
        String[] createScript;

        try {
            inputStream = assetManager.open(script);
            while ((length = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, length);
            }
            // Close streams
            outputStream.close();
            inputStream.close();
            // Separate SQL statements by semicolons
            createScript = outputStream.toString().split(";");
            // TODO: Does not account for parsing out comments yet.
            // Execute each sql statement
            for(String sql : createScript){
                sql = sql.trim();
                if(sql.length() > 0){
                    db.execSQL(sql);
                }
            }
        } catch (IOException e) {
            // TODO: Appropriate error handling/message
            e.printStackTrace();
        }
        catch (SQLException e){
            // TODO: Appropriate error handling/message
            e.printStackTrace();
        }
    }


    //This function will import a database from the assets folder -- this WILL overwrite the
    //existing database if one exists already. Calling functions should check for an existing db.
    private void importDatabase() throws IOException{
        // Open local db
        AssetManager mgr = m_context.getAssets();
        // databases is the subfolder in the assets folder -- may change later but is functional now
        InputStream inputStream = mgr.open("databases" + File.separator + DATABASE_NAME);
        // Path in the phone itself for the just-create db
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        // Open empty db as output stream
        OutputStream outputStream = new FileOutputStream(outFileName);

        // Reansfer bytes from input to output file
        byte [] buffer = new byte[1024];
        int len = 0;
        while((len = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, len);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public boolean databaseExists(){
        SQLiteDatabase db = null;
        try{
            String path = DATABASE_PATH + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(path, passphrase, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            Log.e("TAG", "EXCEPTION: " + e.getMessage());
        }
        // If db exists, it needs to be closed again
        if(db != null){
            db.close();
        }
        // Return boolean corresponding to existence of db
        return db == null? false : true;
    }

    public boolean tableExists(String tableName, boolean openDb) {
        SQLiteDatabase db = this.getReadableDatabase(passphrase);
        if(openDb) {
            if(db == null || !db.isOpen()) {
                db = getReadableDatabase(passphrase);
            }

            if(!db.isReadOnly()) {
                db.close();
                db = getReadableDatabase(passphrase);
            }
        }
        boolean exists = false;
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                exists = true;
            }
        }
        cursor.close();
        return exists;
    }

    public String getPassphrase(){
        return passphrase;
    }


}
