package com.example.dinh.whatsfordinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.dinh.whatsfordinner.data.MyDataContext;
import com.example.dinh.whatsfordinner.data.Nutrition;

public class NutritionActivity extends AppCompatActivity {

    private MyDataContext app = MyDataContext.getInstance();
    Nutrition nutrition;
    private EditText caloriesEditText;
    private EditText carbohydratesEditText;
    private EditText mineralsEditText;
    private EditText vitaminsEditText;
    private EditText sugarEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nutrition = app.getNutritionGoal();

        caloriesEditText = (EditText) findViewById(R.id.editTextCalories);
        carbohydratesEditText = (EditText) findViewById(R.id.editTextCarbohydrates);
        mineralsEditText = (EditText) findViewById(R.id.editTextMinerals);
        vitaminsEditText = (EditText) findViewById(R.id.editTextVitamins);
        sugarEditText = (EditText) findViewById(R.id.editTextSugar);


        if (nutrition.calories > 0)
            caloriesEditText.setText(String.valueOf(nutrition.calories));
        if (nutrition.carbohydrates > 0)
            carbohydratesEditText.setText(String.valueOf(nutrition.carbohydrates));
        if (nutrition.minerals > 0)
            mineralsEditText.setText(String.valueOf(nutrition.minerals));
        if (nutrition.vitamins > 0)
            vitaminsEditText.setText(String.valueOf(nutrition.vitamins));
        if (nutrition.sugar > 0)
            sugarEditText.setText(String.valueOf(nutrition.sugar));
    }

    public void onSaveClicked(View view) {
        nutrition.calories = Integer.parseInt(caloriesEditText.getText().toString());
        nutrition.carbohydrates = Double.parseDouble(carbohydratesEditText.getText().toString());
        nutrition.minerals = Double.parseDouble(mineralsEditText.getText().toString());
        nutrition.vitamins = Double.parseDouble(vitaminsEditText.getText().toString());
        nutrition.sugar = Double.parseDouble(sugarEditText.getText().toString());

        //Close this activty
        finish();
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
}
