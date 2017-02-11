package com.fractaltechnologies.ashleyw.buffbuddy;

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

    public String toString(){
        return this.m_muscle;
    }
}
