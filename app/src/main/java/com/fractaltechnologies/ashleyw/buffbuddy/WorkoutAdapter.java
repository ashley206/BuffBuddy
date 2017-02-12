package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Ashley H on 2/11/2017.
 */

public class WorkoutAdapter extends ArrayAdapter<Workout> {

    private static final String LOG_TAG = "WorkoutAdapter";

    private static class ViewHolder{
        TextView name;
        //TextView primary;
        //TextView seconary;
    }

    public WorkoutAdapter(Context context, ArrayList<Workout> workouts){
        super(context, R.layout.item_workout, workouts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Workout wo = getItem(position);
            ViewHolder viewHolder;
            // Check if existing view is being reused; otherwise inflate
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_workout, parent, false);
                viewHolder.name = (TextView)convertView.findViewById(R.id.tvName);
                //viewHolder.primary = (TextView)convertView.findViewById(R.id.tvPrimary);
                //viewHolder.seconary = (TextView)convertView.findViewById(R.id.tvSecondary);
                // Cache the viewholder
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            // Data population
            viewHolder.name.setText(wo.GetName());
            //viewHolder.primary.setText();
        }
        catch (Exception ex) {
            Log.e("TAG", LOG_TAG + ex);
        }
        return convertView;
    }
}
