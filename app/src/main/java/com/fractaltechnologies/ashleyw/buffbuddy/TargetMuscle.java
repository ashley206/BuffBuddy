package com.fractaltechnologies.ashleyw.buffbuddy;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Ashley H on 1/19/2017.
 */

public enum TargetMuscle {
    QUADS ("Quadriceps"),
    PECTORALS ("Pectorals"),
    MEDIALDELTS ("Medial Deltoids"),
    POSTERIORDELTS ("Posterior Deltoids"),
    ANTERIORDELTS ("Anterior Deltoids"),
    BACK ("Back"),
    LOWERBACK ("Lower Back"),
    BICEPS ("Biceps"),
    TRICEPS ("Triceps"),
    HAMSTRINGS ("Hamstrings"),
    GLUTES ("Glutes"),
    CALVES ("Calves"),
    TRAPS ("Trapezius"),
    OBLIQUES ("Obliques"),
    ABS ("Abdominals");

    private final String m_muscle;

    private TargetMuscle(String muscle){
        m_muscle = muscle;
    }

    public boolean equalsTargetMuscle(String muscle){
        return m_muscle.equals(muscle);
    }

    @Override
    public String toString(){
        return this.m_muscle;
    }

    public static TargetMuscle fromString(String muscle) {
        for (TargetMuscle name : values()) {
            if (name.m_muscle.equals(muscle)) {
                return name;
            }
        }
        return null;
    }

    public static ArrayList<String> getTargetMusclesAsStrings(){
        ArrayList<String> muscles = new ArrayList<String>();
        for(TargetMuscle muscle : values()){
            muscles.add(muscle.toString());
        }
        return muscles;
    }

    public static ArrayList<TargetMuscle> getTargetMuscles(){
        ArrayList<TargetMuscle> muscles = new ArrayList<TargetMuscle>();
        for(TargetMuscle muscle : values()){
            muscles.add(muscle);
        }
        return muscles;
    }
}
