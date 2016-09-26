package com.example.dinh.whatsfordinner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.example.dinh.whatsfordinner.data.MyDataContext;
import com.example.dinh.whatsfordinner.data.Recipe;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String BUNDLE_INDEX_KEY = "com.example.dinh.whatsfordinner.bunddle_index_key";

    protected MyDataContext app = MyDataContext.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * App information dialog
     *
     * @param view
     */
    public void onAboutClicked(View view) {
        LayoutInflater factory = LayoutInflater.from(view.getContext());
        final View textEntryView = factory.inflate(R.layout.activity_main_about_dialog, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        //alert.setIcon(R.drawable.icon);
        alert.setTitle("About What's for Dinner").setView(textEntryView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

        alert.show();
    }

    public void onMealsClicked(View view) {
        //Toast.makeText(this, "Meals clicked", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MealsActivity.class);
        startActivity(intent);
    }

    public void onRecipesClicked(View view) {
        //Toast.makeText(this, "Recipes clicked", Toast.LENGTH_SHORT).show();
        List<Recipe> recipes = app.getRecipes();

        Intent intent = new Intent(this, RecipesActivity.class);
        startActivity(intent);
    }

    public void onGroceriesClicked(View view) {
        //Toast.makeText(this, "Groceries clicked", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, GroceriesActivity.class);
        startActivity(intent);
    }

    public void onNewRecipeClicked(View view) {
        //Toast.makeText(this, "New Dish clicked", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, NewRecipeActivity.class);
        startActivity(intent);
    }
}
