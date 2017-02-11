package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private Context m_context;
    public SQLiteHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version){
        super(context, dbName, factory, version);
        m_context = context;
    }

    // Overridden method to create the sql database tables necessary
    // for this application.
    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE IF NOT EXISTS USER(\n" +
                "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    FIRSTNAME TEXT,\n" +
                "    LASTNAME TEXT,\n" +
                "    EMAIL TEXT,\n" +
                "    PASSWORD TEXT,\n" +
                "    GOOGLEACCOUNT INTEGER\n" +
                "    )";
        db.execSQL(sql);
        //executeSQLScript(db, "create.sql");
    }

    // Overridden method to handle upgrades to the database, if any.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(newVersion > oldVersion){
            // New cases can be added as the database changes.
            switch (oldVersion){
                // Cases will not 'break' so they can be subsequently upgraded
                case 1:
                    // 1/27/17: create_v2.sql modified the Exercise table to include 6
                    // optional columns for specific rep amounts.
                    executeSQLScript(db, "create_v2.sql");
                case 2:
                    executeSQLScript(db, "create_v3.sql");
            }
            onCreate(db);
        }
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
    public boolean isTableExists(String tableName, boolean openDb) {
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
