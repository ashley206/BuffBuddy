package com.fractaltechnologies.ashleyw.buffbuddy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class Workout implements Serializable {
    private String m_name;
    private int m_id;
    private int m_userId;
    private ArrayList<Exercise> m_exercises;

    public Workout(String name, int userId){
        m_name = name;
        m_id = -1;
        m_userId = userId;
        m_exercises = new ArrayList<Exercise>();
    }

    public Workout(String name, int id, int userId){
        m_name = name;
        m_id = id;
        m_userId = userId;
        m_exercises = new ArrayList<Exercise>();
    }

    public Workout(String name, int id, int userId, ArrayList<Exercise> exercises){
        m_name = name;
        m_id = id;
        m_userId = userId;
        m_exercises = exercises;
    }

    public String GetName(){
        return this.m_name;
    }

    public void SetName(String name){
        m_name = name;
    }

    public int GetId(){
        return m_id;
    }

    public void SetId(int id){
        m_id = id;
    }

    public ArrayList<Exercise> GetExercises(){
        return this.m_exercises;
    }

    public void SetExercises(ArrayList<Exercise> exercises){
        m_exercises = exercises;
    }

    public Workout SelectWorkout(){
        return this;
    }

    public Exercise GetNextExercise(){
        // TODO: Return to this and do it properly
        return m_exercises.get(0);
    }

    private boolean LastExercise(Exercise e){
        // Compare the indexes in the list
        return m_exercises.indexOf(e) == (m_exercises.size()-1);
    }

    private boolean HasExercise(Exercise e){
        return m_exercises.contains(e);
    }
}
