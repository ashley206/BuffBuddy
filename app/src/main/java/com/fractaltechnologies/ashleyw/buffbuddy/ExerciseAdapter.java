package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashley H on 2/12/2017.
 */

public class ExerciseAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = "ExerciseAdapter";
    private LayoutInflater layoutInflater;
    // Filter
    private static ArrayList<Exercise> exerciseArrayList;
    private static ArrayList<Exercise> filteredArrayList;
    private ExerciseFilter filter = new ExerciseFilter();


    private static class ViewHolder{
        TextView name;
        TextView primary;
        TextView secondary;
    }

    public ExerciseAdapter(Context context, ArrayList<Exercise> exercises){
        exerciseArrayList = exercises;
        filteredArrayList = exercises;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Exercise exercise = getItem(position);
            ExerciseAdapter.ViewHolder viewHolder;
            // Check if existing view is being reused; otherwise inflate
            if (convertView == null) {
                viewHolder = new ExerciseAdapter.ViewHolder();

                convertView = layoutInflater.inflate(R.layout.item_exercise, null);

                // Assign all of the layout elements in the viewHolder
                viewHolder.name = (TextView)convertView.findViewById(R.id.tvName);
                viewHolder.primary = (TextView)convertView.findViewById(R.id.tvPrimary);
                viewHolder.secondary = (TextView)convertView.findViewById(R.id.tvSecondary);

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
            Log.e(TAG, "getView: " + ex.getMessage());
        }
        return convertView;
    }

    public Exercise getItem(int position){
        return filteredArrayList.get(position);
    }

    public int getCount(){
        return filteredArrayList.size();
    }

    public long getItemId(int position){
        return position;
    }

    public Filter getFilter(){
        return filter;
    }

    private class ExerciseFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Exercise> exerciseList = exerciseArrayList;
            final ArrayList<String> origMuscles = TargetMuscle.getTargetMusclesAsStrings();
            // Final list that could contain up to the total amount of original exercises + all muscle groups
            final ArrayList<String> nlist = new ArrayList<String>(exerciseList.size() + origMuscles.size());

            Exercise exercise;
            String filterableName, filterablePM, filterableSM;
            // Iterate over the exercises in the original list
            for (int i = 0; i < exerciseList.size(); i++) {
                exercise = exerciseList.get(i);
                filterableName = exercise.getName();
                // This ensures duplicates are not added if one filter fits more than one condition
                if (filterableName.toLowerCase().contains(filterString)) {
                    nlist.add(filterableName);
                }
                else if(exercise.getPrimaryTargetMuscle() != null) {
                    filterablePM = exercise.getPrimaryTargetMuscle().toString();
                    if(filterablePM.toLowerCase().contains(filterString)){
                        nlist.add(filterablePM);
                    }
                }
                else if(exercise.getSecondaryTargetMuscle() != null) {
                    filterableSM = exercise.getSecondaryTargetMuscle().toString();
                    if(filterableSM.toLowerCase().contains(filterString)){
                        nlist.add(filterableSM);
                    }
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredArrayList = (ArrayList<Exercise>) results.values;
            notifyDataSetChanged();
        }
    }
}
