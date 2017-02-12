package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAdapter db = new DBAdapter(this);
        db.openRead();

        SQLiteHelper helper = new SQLiteHelper(this, db.getDBName(), null, db.getDBInstance().getVersion());
        //helper.executeSQLScript(db.getDBInstance(), "defaultExercises.sql");
//        String sql = "CREATE TABLE IF NOT EXISTS temp(\n" +
//                "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                "    FIRSTNAME TEXT,\n" +
//                "    LASTNAME TEXT,\n" +
//                "    USERNAME TEXT,\n" +
//                "    EMAIL TEXT,\n" +
//                "    PASSWORD TEXT,\n" +
//                "    GOOGLEACCOUNT INTEGER\n" +
//                "    );" +
//                "DROP TABLE USER;" +
//                "ALTER TABLE temp RENAME TO USER";
//        try {
//            db.getDBInstance().execSQL(sql);
//        }catch (SQLiteException ex){
//            Log.e("TAG", "EXCEPTION MESSAGE: " + ex.getMessage());
//        }

        try {
//            String sql = "DROP TABLE USER;";
//            db.getDBInstance().execSQL(sql);
//
//            sql = "CREATE TABLE IF NOT EXISTS USER(\n" +
//                    "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                    "    FIRSTNAME TEXT,\n" +
//                    "    LASTNAME TEXT,\n" +
//                    "    USERNAME TEXT,\n" +
//                    "    EMAIL TEXT,\n" +
//                    "    PASSWORD TEXT,\n" +
//                    "    GOOGLEACCOUNT INTEGER\n" +
//                    "    );";
//            db.getDBInstance().execSQL(sql);

//            String sql = "INSERT INTO USER(" +
//                    "    FIRSTNAME,\n" +
//                    "    LASTNAME,\n" +
//                    "    USERNAME,\n" +
//                    "    EMAIL,\n" +
//                    "    PASSWORD,\n" +
//                    "    GOOGLEACCOUNT) VALUES(" +
//                    "'ashley', 'wagner', 'awagner', 'ashleybwagner@comcast.net', 'sadie', 0);";
//            db.getDBInstance().execSQL(sql);

            String sql = "SELECT ID, NAME FROM EXERCISE WHERE WORKOUT_ID = 8;";
//            ExerciseDAO ed = new ExerciseDAO();
//            List<Integer> l = new ArrayList<>();
//            l.add(0);
//            l.add(1);
//            l.add(2);
//            l.add(3);
//            l.add(4);
//            l.add(5);
//            List<TargetMuscle> m = new ArrayList<TargetMuscle>();
//            m.add(TargetMuscle.BACK);
//            m.add(TargetMuscle.BICEPS);
//            Exercise e = new Exercise("New Exercise", l, 6, m, 8);
//            ed.Create(e, this);
            Cursor c = db.getDBInstance().rawQuery(sql, null);
            if (c != null && c.moveToFirst()) {
                do {
                    String test = c.getString(c.getColumnIndex("ID"));
                }while(c.moveToNext());
            }
        }catch (SQLiteException ex){
            Log.e("TAG", "EXCEPTION MESSAGE" + ex.getMessage());
        }
        Button bttnLogin = (Button)findViewById(R.id.bttnLogin);
        Button bttnRegister = (Button)findViewById(R.id.bttnRegister);

        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        bttnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

}
