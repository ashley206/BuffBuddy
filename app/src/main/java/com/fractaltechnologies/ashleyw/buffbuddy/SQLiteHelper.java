package com.fractaltechnologies.ashleyw.buffbuddy;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static String DATABASE_PATH = "/data/user/0/com.fractaltechnologies.ashleyw.buffbuddy/databases/";
    private static final String DATABASE_NAME = "buffbuddy.sqlite";
    private static final int DATABASE_VERSION = 3;
    private Context m_context;

    public SQLiteHelper(Context context ){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        m_context = context;
    }

    public void createDatabase() throws IOException {
        boolean dbExists = databaseExists();
        // We only care if the database doesn't exist
        if(!dbExists){
            this.getReadableDatabase();
            try{
                // This imports the default database into the system
                importDatabase();
            }catch (IOException e){
                Log.e("TAG", "EXCEPTION: " + e.getMessage());
            }
        }
    }

    // Overridden method to create the sql database tables necessary
    // for this application.
    @Override
    public void onCreate(SQLiteDatabase db){

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
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
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
        SQLiteDatabase db = this.getReadableDatabase();
        if(openDb) {
            if(db == null || !db.isOpen()) {
                db = getReadableDatabase();
            }

            if(!db.isReadOnly()) {
                db.close();
                db = getReadableDatabase();
            }
        }
        boolean exists = false;
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                exists = true;
            }
            cursor.close();
        }
        return exists;
    }

}
