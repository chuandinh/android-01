package com.example.dinh.whatsfordinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dinh.whatsfordinner.data.DayPlan;
import com.example.dinh.whatsfordinner.data.MyDataContext;

import java.util.List;

/**
 * Created by chuandinh on 9/14/16.
 */
public class MealPlanArrayAdapter extends ArrayAdapter<DayPlan> {
    private final MyDataContext app = MyDataContext.getInstance();
    private Context context;
    private List<DayPlan> objects;

    public MealPlanArrayAdapter(Context context, int resource, List<DayPlan> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    static class ViewHolder {
        TextView dayTextView;
        TextView breakfastTextView;
        TextView lunchTextView;
        TextView dinnerTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DayPlan dayPlan = objects.get(position);

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.meals_dayplan_item, null);

            final ViewHolder holder = new ViewHolder();

            holder.dayTextView = (TextView) convertView.findViewById(R.id.textViewDay);
            holder.breakfastTextView = (TextView) convertView.findViewById(R.id.textViewBreakFast);
            holder.lunchTextView = (TextView) convertView.findViewById(R.id.textViewLunch);
            holder.dinnerTextView = (TextView) convertView.findViewById(R.id.textViewDinner);

            convertView.setTag(holder);
        }


        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.dayTextView.setText(dayPlan.day);

        if (dayPlan.breakfast != null)
            holder.breakfastTextView.setText(dayPlan.breakfast.name);
        if (dayPlan.lunch != null)
            holder.lunchTextView.setText(dayPlan.lunch.name);
        if (dayPlan.dinner != null)
            holder.dinnerTextView.setText(dayPlan.dinner.name);

        return convertView;
    }

}
