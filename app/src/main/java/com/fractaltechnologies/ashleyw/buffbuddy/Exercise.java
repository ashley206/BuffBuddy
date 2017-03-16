package com.fractaltechnologies.ashleyw.buffbuddy;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class Exercise implements Serializable {
    private int m_id;
    private String m_name;
    private ArrayList<Integer> m_reps;
    private int m_sets;
    private ArrayList<TargetMuscle> m_targetMuscles;
    private TargetMuscle m_primaryTargetMuscle;
    private TargetMuscle m_secondaryTargetMuscle;
    private int m_workoutID;

    public Exercise(String name){
        m_id = -1;
        m_name = name;
        m_reps = new ArrayList<Integer>();
        m_sets = -1;
        m_primaryTargetMuscle = null;
        m_secondaryTargetMuscle = null;
        m_workoutID = -1;
    }

    public Exercise(String name, ArrayList<Integer> reps, int sets, TargetMuscle primary, TargetMuscle secondary, int workoutID){
        m_id = -1;
        m_name = name;
        m_reps = reps;
        m_sets = sets;
        m_primaryTargetMuscle = primary;
        m_secondaryTargetMuscle = secondary;
        m_workoutID = workoutID;
    }

    public Exercise(int id, String name, ArrayList<Integer> reps, int sets, TargetMuscle primary, TargetMuscle secondary, int workoutID){
        m_id = id;
        m_name = name;
        m_reps = reps;
        m_sets = sets;
        m_primaryTargetMuscle = primary;
        m_secondaryTargetMuscle = secondary;
        m_workoutID = workoutID;
    }

    public String getName(){
        return m_name;
    }

    public int getSets(){
        return m_sets;
    }

    public ArrayList<Integer> getReps(){
        return m_reps;
    }

    public int getID(){
        return m_id;
    }

    public void setID(int id){
        m_id = id;
    }

    public int getWorkoutID(){
        return m_workoutID;
    }

    public void setWorkoutID(int workoutID){
        m_workoutID = workoutID;
    }

    public void setName(String name){
        m_name = name;
    }

    public void setSets(int sets){
        m_sets = sets;
    }

    public void setReps(ArrayList<Integer> reps){
        m_reps = reps;
    }

    public TargetMuscle getPrimaryTargetMuscle(){
        return m_primaryTargetMuscle;
    }

    public TargetMuscle getSecondaryTargetMuscle(){
        return  m_secondaryTargetMuscle;
    }

    public void setPrimaryTargetMuscle(TargetMuscle primaryTargetMuscle){
        m_primaryTargetMuscle = primaryTargetMuscle;
    }

    public void setSecondaryTargetMuscle(TargetMuscle secondaryTargetMuscle){
        m_secondaryTargetMuscle = secondaryTargetMuscle;
    }

    public Exercise SelectExercise() {
        return this;
    }

    @Override
    public String toString() {
        return this.m_name;
    }
}
