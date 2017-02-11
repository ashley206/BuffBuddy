package com.fractaltechnologies.ashleyw.buffbuddy;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class Workout {
    private String m_name;
    private List<Exercise> m_exercises;

    public Workout(String name){
        m_name = name;
        m_exercises = new Vector<Exercise>();
    }

    public Workout(String name, List<Exercise> exercises){
        m_name = name;
        m_exercises = exercises;
    }

    public String GetName(){
        return this.m_name;
    }

    public void SetName(String name){
        m_name = name;
    }

    public List<Exercise> GetExercises(){
        return this.m_exercises;
    }

    public void SetExercises(List<Exercise> exercises){
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
