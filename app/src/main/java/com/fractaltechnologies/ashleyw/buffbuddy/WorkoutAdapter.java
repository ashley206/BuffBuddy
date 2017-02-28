package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashley H on 2/11/2017.
 */

public class WorkoutAdapter extends BaseAdapter implements Filterable {

    private static final String LOG_TAG = "WorkoutAdapter";
    private static ArrayList<Workout> workoutArrayList;
    private static ArrayList<Workout> filteredArrayList;
    private LayoutInflater layoutInflater;
    private WorkoutFilter filter = new WorkoutFilter();

    private static class ViewHolder{
        TextView name;
    }

    public WorkoutAdapter(Context context, ArrayList<Workout> workouts){
        //super(context, R.layout.item_workout, workouts);
        workoutArrayList = workouts;
        filteredArrayList = workouts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Workout wo = getItem(position);
            ViewHolder viewHolder;
            // Check if existing view is being reused; otherwise inflate
            if (convertView == null) {
                viewHolder = new ViewHolder();

                convertView = layoutInflater.inflate(R.layout.item_workout, null);

                viewHolder = new ViewHolder();
                viewHolder.name = (TextView)convertView.findViewById(R.id.tvName);
                // Cache the viewholder
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            // Data population
            viewHolder.name.setText(filteredArrayList.get(position).GetName());
            //viewHolder.primary.setText();
        }
        catch (Exception ex) {
            Log.e("TAG", LOG_TAG + ex);
        }
        return convertView;
    }

    public Workout getItem(int position){
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

    private class WorkoutFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Workout> list = workoutArrayList;
            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<String>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).GetName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredArrayList = (ArrayList<Workout>) results.values;
            notifyDataSetChanged();
        }

    }
}
