package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ashley H on 1/19/2017.
 */


public class WorkoutDAO implements IDAO<Workout> {

    private final static String TAG = "WorkoutDAO";
    @Override
    public void Create(Workout workout, Context context) {
        DBAdapter dbAdapter = new DBAdapter(context);
        ContentValues values = new ContentValues();
        values.put("NAME", workout.getName());
        values.put("USER_ID", String.valueOf(((CreateWorkoutActivity) context).user.getID()));
        dbAdapter.openWrite().Create("WORKOUT", values);
    }

    @Override
    public void Update(Workout workout, Context c){
        DBAdapter dbAdapter = new DBAdapter(c);
        ContentValues values = new ContentValues();
        values.put("NAME", workout.getName());
        String [] whereArgs = { String.valueOf(workout.getId()) };
        dbAdapter.openWrite().Update("WORKOUT", values, "ID = ?", whereArgs);
    }
    @Override
    public void Delete(Workout workout, Context context){
        DBAdapter dbAdapter = new DBAdapter(context);
        String [] whereArgs = { String.valueOf(workout.getId()) };
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
            Log.e(TAG, "FindByName: " + ex.getMessage());
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
            workoutID = workouts.get(i).getId();
            workouts.get(i).setExercises(FetchExercisesInWorkout(workoutID, context));
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

    public void InitDefaultWorkoutExerciseInformation(int userId, Context context){
        // TODO: This is pretty trash. Reading from a file would be preferable.
        try {
            DBAdapter dbAdapter = new DBAdapter(context);
            ContentValues workoutValues = new ContentValues();
            //1
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Legs: Quads and Glutes");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //2
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Legs: Hamstrings");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //3
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Legs: Glutes and Hamstrings");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //4
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Chest and Triceps");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //5
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Chest and Triceps pt. 2");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //6
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Biceps and Triceps");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //7
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Posterior Deltoids");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //8
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Back and Shoulders");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //9
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Back and Traps");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //10
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Lower Back");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //11
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Core: Abdominals");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //12
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Core: Obliques");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            //13
            workoutValues.put("USER_ID", userId);
            workoutValues.put("NAME", "Medial Deltoids");
            dbAdapter.openWrite().Create("WORKOUT", workoutValues);
            // Insert all exercises into the database
            dbAdapter.openWrite().getDBInstance().execSQL(
                    "INSERT INTO\n" +
                            "EXERCISE\n" +
                            "(NAME,\n" +
                            "    PRIMARY_TARGET_MUSCLE,\n" +
                            "    SECONDARY_TARGET_MUSCLE,\n" +
                            "    SETS,\n" +
                            "    REP1, REP2, REP3, REP4, REP5, REP6,\n" +
                            "    WORKOUT_ID)\n" +
                            "VALUES\n" +
                            "(\"Barbell Squats\",\"Quadriceps\",\"Glutes\",        5, 14, 14, 12, 12, 10, NULL, 1),\n" +
                            "(\"Leg Press\", \"Quadriceps\", \"Hamstrings\",       5, 40, 35, 30, 25, 20, NULL, 1),\n" +
                            "(\"Stiff-Leg Deadlift\", \"Hamstrings\", \"Glutes\",  5, 16, 16, 14, 14, 12, NULL, 1),\n" +
                            "\n" +
                            "(\"Leg Extension\", \"Quadriceps\", NULL,             6, 15, 35, 30, 25, 20, 15, 2),\n" +
                            "(\"Standing Calf Raises\", \"Calves\", NULL,          4, 15, 15, 12, 10, NULL, NULL, 2),\n" +
                            "(\"Leg Curls\", \"Hamstrings\", NULL,                 4, 20, 16, 12, 10, NULL, NULL, 2),\n" +
                            "(\"Barbell Hip Thrusters\", \"Glutes\", NULL,         4, 12, 12, 10, 8, NULL, NULL, 2),\n" +
                            "(\"Romanian Deadlift\", \"Hamstring\", \"Glutes\",    3, 20, 20, 20, NULL, NULL, NULL, 2),\n" +
                            "\n" +
                            "(\"Bridges\", \"Glutes\", \"Hamstrings\",             4, 15, 15, 12, 10, NULL, NULL,3),\n" +
                            "(\"Leg Lifts\", \"Glutes\", \"Hamstrings\",           3, 15, 15, 15, NULL, NULL, NULL, 3),\n" +
                            "(\"Calf Press\", \"Calves\", NULL,                    3, 15, 20, 20, NULL, NULL, NULL, 3),\n" +
                            "(\"Lunge\", \"Quadriceps\", \"Glutes\",               5, 10, 10, 10, 10, 10, NULL, 3),\n" +
                            "(\"Leg Press\", \"Quadriceps\", \"Hamstrings\",       6, 20, 40, 35, 30, 25, 20, 3),\n" +
                            "\n" +
                            "(\"Barbell Bench Press\", \"Pectorals\", NULL,        5, 15, 15, 12, 12, 10, NULL, 4),\n" +
                            "(\"Incline Flyes\", \"Pectorals\", NULL,              4, 15, 12, 10, 10, NULL, NULL, 4),\n" +
                            "(\"Cable Crossover\", \"Pectorals\", NULL,            6, 12 , 12, 10, 10, 8, 8, 4),\n" +
                            "(\"Reverse Grip Tricep Pushdown\", \"Triceps\", NULL, 4, 15, 12, 10,10, NULL, NULL, 4),\n" +
                            "\n" +
                            "(\"Dumbell Incline Press\", \"Pectorals\", NULL,      5, 15, 15 ,12, 12 ,10, NULL, 5),\n" +
                            "(\"Tricep Kickbacks\", \"Triceps\", NULL,             4, 12, 12, 10, 8, NULL, NULL, 5),\n" +
                            "(\"Dumbell Flyes\", \"Pectorals\", NULL,              4, 15, 12, 10, 10, NULL, NULL, 5),\n" +
                            "\n" +
                            "(\"Hammer Curls\", \"Biceps\", NULL,                  4, 10, 10, 8, 6, NULL, NULL, 6),\n" +
                            "(\"Wide Grip Barbell Curls\", \"Biceps\", NULL,       5, 12, 12, 10, 10, 8, NULL, 6),\n" +
                            "(\"Tricep Pushdown\", \"Triceps\", NULL,              4, 15, 15, 12, 10, NULL, NULL, 6),\n" +
                            "\n" +
                            "(\"Upright Row\", \"Trapezius\", \"Posterior Deltoids\",      4, 15, 12, 10, 10, NULL, NULL, 7),\n" +
                            "(\"Dumbbell Shrug\", \"Trapezius\", \"Posterior Deltoids\",   3, 12, 12, 12, NULL, NULL, NULL,7),\n" +
                            "(\"Rope Curls\", \"Biceps\", NULL,                            4, 15, 12, 10, 10, NULL, NULL, 7),\n" +
                            "\n" +
                            "(\"Side Lateral Raise\", \"Medial Deltoids\", \"Posterior Deltoids\", 4, 10, 10, 10, 10, NULL, NULL,8),\n" +
                            "(\"Lat Pull Down\", \"Back\", NULL,                           5, 15, 15, 12, 12, 10, NULL, 8),\n" +
                            "(\"Seated Close-Grip Cable Row\", \"Back\", \"Trapezius\",    5, 15, 15, 12, 12, 10, NULL, 8),\n" +
                            "\n" +
                            "(\"Seated Bent-Over Delt Raise\", \"Posterior Deltoids\", \"Medial Deltoids\",    4, 15, 12, 10, 10, NULL, NULL, 9),\n" +
                            "(\"Bent-Over Row\", \"Back\", \"Trapezius\",                  4, 15, 15, 12, 10, NULL, NULL, 9),\n" +
                            "(\"Seated Reverse Cable Fly\", \"Posterior Deltoids\", NULL,  3, 15, 15, 12, NULL, NULL, NULL,9),\n" +
                            "(\"Barbell Shrug\", \"Trapeziuz\", NULL,                      3, 10, 10, 10, NULL, NULL, NULL,9),\n" +
                            "(\"Straight-Arm Pulldown\", \"Back\", NULL,                   5, 15, 15, 12, 12, 10, NULL,9),\n" +
                            "\n" +
                            "(\"Shoulder Press\", \"Medial Deltoids\", \"Anterior Deltoids\",   4, 12, 10, 8, 6, NULL, NULL, 10),\n" +
                            "(\"Arnold Press\", \"Medial Deltoids\", \"Anterior Deltoids\",     5, 10, 10, 8, 8, 6, NULL, 10),\n" +
                            "(\"Front Raise\", \"Anterior Deltoids\", \"Medial Deltoids\",      4, 10, 10, 10, 10, NULL, NULL, 10),\n" +
                            "(\"Dumbbell Row\", \"Posterior Deltoids\", NULL,                   4, 12, 10, 8, 8, NULL, NULL, 10),\n" +
                            "\n" +
                            "(\"Russian Twists\", \"Obliques\", \"Abdominals\",    3, 20, 20, 20, NULL, NULL, NULL, 11),\n" +
                            "(\"Side Plank\", \"Obliques\", \"Abdominals\",        3, 1, 1, 1, NULL, NULL, NULL, 11),\n" +
                            "(\"Plank\", \"Abdominals\", NULL,                     3, 1, 1, 1, NULL, NULL, NULL, 11),\n" +
                            "\n" +
                            "(\"Leg Raises\", \"Abdominals\", NULL,                3, 20, 20, 20, NULL, NULL, NULL, 12),\n" +
                            "(\"Flutter Kicks\", \"Abdominals\", NULL,             3, 30, 30, 30, NULL, NULL, NULL,12),\n" +
                            "(\"Heel Touches\", \"Obliques\", NULL,                3, 30, 30, 30, NULL, NULL, NULL, 12),\n" +
                            "\n" +
                            "(\"Supermans\", \"Lower Back\", NULL,                 3, 20, 20, 20, NULL, NULL ,NULL, 13),\n" +
                            "(\"Opposite Arm/Leg Raises\", \"Lower Back\", NULL,   3, 20, 20, 20, NULL, NULL, NULL, 13),\n" +
                            "(\"Child Pose\", \"Lower Back\", NULL,                2, 30, 30, NULL, NULL, NULL, NULL, 13),\n" +
                            "(\"Windshield Wipers\", \"Lower Back\", NULL,         3, 20, 20, 20, NULL, NULL, NULL, 13);"
            );
        }catch (Exception ex){
            Log.e(TAG, "InitDefaultWorkoutExerciseInformation: " + ex.getMessage());
        }

    }
}
