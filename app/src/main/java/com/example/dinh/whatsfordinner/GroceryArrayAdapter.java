package com.example.dinh.whatsfordinner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dinh.whatsfordinner.data.Ingredient;
import com.example.dinh.whatsfordinner.data.MyDataContext;

import java.util.List;

/**
 * GroceryArrayAdapter
 * Created by dinh on 9/8/16.
 */
public class GroceryArrayAdapter extends ArrayAdapter<Ingredient> {

    private final MyDataContext app = MyDataContext.getInstance();
    private Context context;
    private List<Ingredient> objects;

    public GroceryArrayAdapter(Context context, int resource, List<Ingredient> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;

        Activity activity = (Activity) this.context;
    }

    /**
     * View Holder for each item
     */
    static class ViewHolder {
        RelativeLayout container;
        TextView ingredientName;
        GestureDetectorCompat mDetector;

        TextView wipeIngredientName;
        ImageButton wipeButtonAdd;
        ImageButton wipeButtonRemove;
    }

    /**
     * getView
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Ingredient ingredient = objects.get(position);

        //if (convertView == null)
        {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.fragment_ingredient_item, null);

            final ViewHolder holder = new ViewHolder();
            holder.container = (RelativeLayout) convertView.findViewById(R.id.container);
            holder.ingredientName = (TextView) convertView.findViewById(R.id.ingredient_item_content);
            holder.wipeIngredientName = (TextView) convertView.findViewById(R.id.ingredient_item_contentEdit);
            holder.wipeButtonAdd = (ImageButton) convertView.findViewById(R.id.wipeButtoneAdd);
            holder.wipeButtonRemove = (ImageButton) convertView.findViewById(R.id.wipeButtonRemove);
            holder.mDetector = new GestureDetectorCompat(context, new MyGestureListener(context, convertView));

            holder.wipeButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    //Toast.makeText(context, "Remove " + position + "", Toast.LENGTH_SHORT).show();

                    Double newValue = app.getShoppingList().get(ingredient);
                    newValue = newValue + 1;  //Increase the qty by 1
                    if (newValue < 0) newValue = 0.0;
                    app.getShoppingList().put(ingredient, newValue);

                    String newDisplayText = ingredient.name + " (" + MyDataContext.formatDouble(newValue) + " " + ingredient.unitName + (newValue > 1 ? "s" : "") + ")";

                    holder.ingredientName.setText(newDisplayText);
                    holder.wipeIngredientName.setText(newDisplayText);

                    if (newValue > 0) {
                        holder.ingredientName.setPaintFlags(0);
                        holder.wipeIngredientName.setPaintFlags(0);
                    }
                }
            });

            holder.wipeButtonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    //Toast.makeText(context, "Add " + position + "", Toast.LENGTH_SHORT).show();

                    Double newValue = app.getShoppingList().get(ingredient);
                    newValue = newValue - 1;  //Subtract the qty by 1
                    if (newValue < 0) newValue = 0.0;
                    app.getShoppingList().put(ingredient, newValue);

                    String newDisplayText = ingredient.name + " (" + MyDataContext.formatDouble(newValue) + " " + ingredient.unitName + (newValue > 1 ? "s" : "") + ")";

                    holder.ingredientName.setText(newDisplayText);
                    holder.wipeIngredientName.setText(newDisplayText);

                    if (newValue <= 0) {
                        holder.ingredientName.setPaintFlags(holder.ingredientName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.wipeIngredientName.setPaintFlags(holder.wipeIngredientName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            });

            convertView.setTag(holder);
        }

        Double value = app.getShoppingList().get(ingredient);

        final ViewHolder holder = (ViewHolder) convertView.getTag();

        String displayText = ingredient.name + " (" + MyDataContext.formatDouble(value) + " " + ingredient.unitName + (value > 1 ? "s" : "") + ")";

        holder.ingredientName.setText(displayText);
        holder.wipeIngredientName.setText(displayText);
        holder.container.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.mDetector.onTouchEvent(event);
                return true;
            }
        });

        holder.wipeButtonAdd.setTag(position);
        holder.wipeButtonRemove.setTag(position);

        if (value <= 0)
            holder.ingredientName.setPaintFlags(holder.ingredientName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        return convertView;
    }
}
