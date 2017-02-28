package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ashley H on 2/12/2017.
 */

public class ExerciseAdapter extends ArrayAdapter<Exercise>{

    private static final String LOG_TAG = "WorkoutAdapter";
    private boolean addBttnVisible = false;
    private boolean deleteBttnVisible = false;

    private static class ViewHolder{
        TextView name;
        TextView primary;
        TextView secondary;
//        Button addExercise;
//        Button deleteExercise;
    }

    public ExerciseAdapter(Context context, ArrayList<Exercise> exercises){
        super(context, R.layout.item_exercise, exercises);
    }

//    public void setAddButtonVisibility(boolean visible){
//        addBttnVisible = visible;
//    }
//
//    public void setDeleteButtonVisibility(boolean visible){
//        deleteBttnVisible = visible;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Exercise exercise = getItem(position);
            ExerciseAdapter.ViewHolder viewHolder;
            // Check if existing view is being reused; otherwise inflate
            if (convertView == null) {
                viewHolder = new ExerciseAdapter.ViewHolder();

                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_workout, parent, false);

                // Assign all of the layout elements in the viewHolder
                viewHolder.name = (TextView)convertView.findViewById(R.id.tvName);
                viewHolder.primary = (TextView)convertView.findViewById(R.id.tvPrimary);
                viewHolder.secondary = (TextView)convertView.findViewById(R.id.tvSecondary);
//                viewHolder.addExercise = (Button)convertView.findViewById(R.id.bttnAddExercise);
//                viewHolder.deleteExercise = (Button)convertView.findViewById(R.id.bttnDeleteExercise);
//
//                // Determine which button is visible
//                viewHolder.addExercise.setVisibility(addBttnVisible ? View.VISIBLE : View.INVISIBLE);
//                viewHolder.deleteExercise.setVisibility(deleteBttnVisible ? View.VISIBLE : View.INVISIBLE);

                // Cache the viewholder
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ExerciseAdapter.ViewHolder)convertView.getTag();
            }
            // Data population
            viewHolder.name.setText(exercise.getName());
            viewHolder.primary.setText(exercise.getPrimaryTargetMuscle().toString());
            viewHolder.secondary.setText(exercise.getSecondaryTargetMuscle().toString());
        }
        catch (Exception ex) {
            Log.e("TAG", LOG_TAG + ex);
        }
        return convertView;
    }
}
