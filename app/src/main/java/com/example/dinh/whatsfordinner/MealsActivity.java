package com.example.dinh.whatsfordinner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dinh.whatsfordinner.data.DayPlan;
import com.example.dinh.whatsfordinner.data.MyDataContext;
import com.example.dinh.whatsfordinner.data.Nutrition;
import com.example.dinh.whatsfordinner.data.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealsActivity extends AppCompatActivity {

    private static final int NUTRITION_REQUEST_CODE = 1001;
    protected MyDataContext app = MyDataContext.getInstance();
    private ListView listView;

    Map<Recipe, Integer> meals;

    // Defined Array values to show in ListView
    ArrayList<String> mealValues = new ArrayList<String>();
    ArrayAdapter<String> mealsAdapter;

    List<DayPlan> mealPlans;
    MealPlanArrayAdapter mealPlanAdapter;

    // Nutrition Goal
    private TextView caloriesTextView;
    private TextView carbohydratesTextView;
    private TextView mineralsTextView;
    private TextView vitaminsTextView;
    private TextView sugarTextView;

    /**
     * Generate String ArrayList for meals
     */
    private void generateMealValues() {
        mealValues.clear();
        for (Recipe r : meals.keySet()) {
            mealValues.add(r.name + " (" + meals.get(r) + ")");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        meals = app.getMeals();

        listView = (ListView) findViewById(R.id.listViewAvailableMeals);

        generateMealValues();
        mealsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mealValues);
        listView.setAdapter(mealsAdapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                LayoutInflater factory = LayoutInflater.from(view.getContext());

                //text_entry is an Layout XML file containing two text field to display in alert dialog
                final View textEntryView = factory.inflate(R.layout.meals_day_input, null);

                final Spinner daySpinner = (Spinner) textEntryView.findViewById(R.id.spinner);
                final RadioGroup mealRadioGroup = (RadioGroup) textEntryView.findViewById(R.id.radioMealType);

                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                //alert.setIcon(R.drawable.icon);
                alert.setTitle("Select Day for The Meal").setView(textEntryView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // get selected radio button from radioGroup
                                int selectedId = mealRadioGroup.getCheckedRadioButtonId();
                                RadioButton selectedRadio = (RadioButton) textEntryView.findViewById(selectedId);

                                String day = daySpinner.getSelectedItem().toString();
                                String mealType = selectedRadio.getText().toString();

                                DayPlan dayPlan = app.getDayPlan(day);
                                if (dayPlan == null)
                                    dayPlan = new DayPlan(day, null, null, null);

                                //Get Recipe at the position
                                Recipe recipe = null;
                                int index = 0;
                                for (Recipe r : meals.keySet()) {
                                    if (index == position) {
                                        recipe = r;
                                        break;
                                    }
                                    index++;
                                }

                                if (mealType.compareTo(getString(R.string.radio_breakfast)) == 0) {
                                    //Add old meal back to available list
                                    if (dayPlan.breakfast != null) {
                                        app.addMeal(dayPlan.breakfast);
                                        app.removeFromShoppingList(dayPlan.breakfast);
                                    }

                                    dayPlan.breakfast = recipe;
                                } else if (mealType.compareTo(getString(R.string.radio_lunch)) == 0) {
                                    //Add old meal back to available list
                                    if (dayPlan.lunch != null) {
                                        app.addMeal(dayPlan.lunch);
                                        app.removeFromShoppingList(dayPlan.lunch);
                                    }

                                    dayPlan.lunch = recipe;
                                } else if (mealType.compareTo(getString(R.string.radio_dinner)) == 0) {
                                    //Add old meal back to available list
                                    if (dayPlan.dinner != null) {
                                        app.addMeal(dayPlan.dinner);
                                        app.removeFromShoppingList(dayPlan.dinner);
                                    }

                                    dayPlan.dinner = recipe;
                                }

                                //Remove 1 meal from the available list
                                app.removeMeal(recipe);

                                //Add ingredients to the shopping list
                                app.addToShoppingList(recipe);

                                //Reload the ListView
                                generateMealValues();
                                mealsAdapter.notifyDataSetChanged();
                                mealPlanAdapter.notifyDataSetChanged();

                                expandListViewHeight(listView);
                                reloadNutritionGoal();
                                //Toast.makeText(MealsActivity.this,
                                //        daySpinner.getSelectedItem().toString() + " " +
                                //        selectedRadio.getText(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        });
                alert.show();
            }

        });
        expandListViewHeight(listView);

        //Weekly Plan ListView
        this.mealPlans = app.getMealPlans();

        //Init with 7 days
        if (mealPlans.size() == 0)
            app.addMealPlans(getResources().getStringArray(R.array.array_days));

        ListView weeklyPlanListView = (ListView) findViewById(R.id.listViewWeeklyPlan);
        mealPlanAdapter = new MealPlanArrayAdapter(this, R.layout.meals_dayplan_item, mealPlans);
        weeklyPlanListView.setAdapter(mealPlanAdapter);
        expandListViewHeight(weeklyPlanListView);

        //Nutrition Manager
        caloriesTextView = (TextView) findViewById(R.id.textViewCalories);
        carbohydratesTextView = (TextView) findViewById(R.id.textViewCarbohydrates);
        mineralsTextView = (TextView) findViewById(R.id.textViewMinerals);
        vitaminsTextView = (TextView) findViewById(R.id.textViewVitamins);
        sugarTextView = (TextView) findViewById(R.id.textViewSugar);

        reloadNutritionGoal();
    }

    /**
     * Start Nutrition Manager Activity
     * @param view
     */
    public void onNutritionManagerClicked(View view) {
        Intent intent = new Intent(this, NutritionActivity.class);
        //intent.putExtras(b);
        startActivityForResult(intent, NUTRITION_REQUEST_CODE);
    }

    /**
     * Reload Nutrition goals
     * @param requestCode
     * @param resultCode
     * @param imageReturnedIntent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        reloadNutritionGoal();
    }

    /**
     * Reload Nutrition Goal for new data
     */
    public void reloadNutritionGoal() {
        Nutrition nutritionGoal = app.getNutritionGoal();
        Nutrition nutritionPlan = new Nutrition();

        for (DayPlan dayPlan : app.getMealPlans()) {
            if (dayPlan.breakfast != null)
                nutritionPlan.addValues(dayPlan.breakfast);

            if (dayPlan.lunch != null)
                nutritionPlan.addValues(dayPlan.lunch);

            if (dayPlan.dinner != null)
                nutritionPlan.addValues(dayPlan.dinner);
        }

        caloriesTextView.setText("Calories (K): " + nutritionPlan.calories + "/" + nutritionGoal.calories +
                (nutritionPlan.calories >= nutritionGoal.calories ? " - reached" : ""));
        carbohydratesTextView.setText("Carbohydrates (g): " + nutritionPlan.carbohydrates + "/" + nutritionGoal.carbohydrates +
                (nutritionPlan.carbohydrates >= nutritionGoal.carbohydrates ? " - reached" : ""));
        mineralsTextView.setText("Minerals (g): " + nutritionPlan.minerals + "/" + nutritionGoal.minerals +
                (nutritionPlan.minerals >= nutritionGoal.minerals ? " - reached" : ""));
        vitaminsTextView.setText("Vitamins (g): " + nutritionPlan.vitamins + "/" + nutritionGoal.vitamins +
                (nutritionPlan.vitamins >= nutritionGoal.vitamins ? " - reached" : ""));
        sugarTextView.setText("Sugar (g): " + nutritionPlan.sugar + "/" + nutritionGoal.sugar +
                (nutritionPlan.sugar >= nutritionGoal.sugar ? " - reached" : ""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * To make the listview expanding to its content
     *
     * @param listView ListView object
     */
    public static void expandListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        listView.measure(0, 0);
        params.height = listView.getMeasuredHeight() * listAdapter.getCount() + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}