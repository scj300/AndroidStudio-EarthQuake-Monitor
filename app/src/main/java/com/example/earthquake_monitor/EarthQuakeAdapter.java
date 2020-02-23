package com.example.earthquake_monitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    private ArrayList<EarthQuake> eqList;
    private Context context;
    private int layoutId;

    public EarthQuakeAdapter(Context context, int resource, List<EarthQuake> earthQuakes) {
        super(context, resource, earthQuakes);

        this.context = context;
        this.layoutId = resource;
        eqList = new ArrayList<>(earthQuakes);
    }


    @NonNull
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder = null;

        // If element is not created yet...
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layoutId, null);

            holder = new ViewHolder();

            holder.magnitudeTextView = (TextView) convertView.findViewById(R.id.eq_list_item_magnitude);
            holder.placeTextView = (TextView) convertView.findViewById(R.id.eq_list_item_place);

            convertView.setTag(holder);
        }
        else {
            convertView.getTag();
        }


        EarthQuake earthQuake = eqList.get(position);

        holder.magnitudeTextView.setText(earthQuake.getMagnitude());
        holder.placeTextView.setText(earthQuake.getPlace());

        return convertView;
    } // end getView


    private class ViewHolder {
        public TextView magnitudeTextView;
        public TextView placeTextView;

    }

}
