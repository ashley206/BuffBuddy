package com.fractaltechnologies.ashleyw.buffbuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class Exercise {
    private int m_id;
    private String m_name;
    private List<Integer> m_reps;
    private int m_sets;
    private List<TargetMuscle> m_targetMuscles;
    private int m_workoutID;

    public Exercise(String name){
        m_id = 0;       // TODO: Another bad assumption
        m_name = name;
        m_reps = new ArrayList<Integer>();
        m_sets = 0;
        m_targetMuscles = new Vector<TargetMuscle>();
        m_workoutID = 0;    // TODO: Not a good assumption to make..
    }

    public Exercise(String name, List<Integer> reps, int sets, List<TargetMuscle> targetMuscles, int workoutID){
        m_name = name;
        m_reps = reps;
        m_sets = sets;
        m_targetMuscles = targetMuscles;
        m_workoutID = workoutID;
    }

    public String getName(){
        return m_name;
    }

    public int getSets(){
        return m_sets;
    }

    public List<Integer> getReps(){
        return m_reps;
    }

    public List<TargetMuscle> getTargetMuscles(){
        return m_targetMuscles;
    }

    public int getWorkoutID(){return m_workoutID;}

    public int getID(){
        return m_id;
    }

    public void setID(int id){
        m_id = id;
    }
    public Exercise SelectExercise() {
        return this;
    }
}
