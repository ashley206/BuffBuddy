package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Context;

import java.util.List;
import java.util.Vector;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class WorkoutDAO implements IDAO<Workout> {
    @Override
    public void Create(Workout wo, Context c) {

    }

    @Override
    public void Update(Workout wo, Context c){

    }
    @Override
    public void Delete(Workout wo, Context c){

    }
    @Override
    public List<Workout> FindByName(String name){
        List<Workout> workouts = new Vector<Workout>();

        return workouts;
    }
    @Override
    public List<Workout> FetchAll(){
        List<Workout> workouts = new Vector<Workout>();

        return workouts;
    }
}
