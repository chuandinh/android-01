package com.example.dinh.whatsfordinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dinh.whatsfordinner.data.Recipe;

import java.util.List;

/**
 * Created by dinh on 9/8/16.
 */
public class RecipeArrayAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private List<Recipe> objects;

    public RecipeArrayAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe recipe = objects.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, null);

        TextView tvContent = (TextView) view.findViewById(android.R.id.text1);
        tvContent.setText(recipe.name);

        return view;
    }
}
