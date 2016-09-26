package com.example.dinh.whatsfordinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dinh.whatsfordinner.data.MyDataContext;
import com.example.dinh.whatsfordinner.data.Recipe;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailFragment.OnFragmentInteractionListener {

    protected MyDataContext app = MyDataContext.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            //Create the fragment, set its args, add it to the detail container
            RecipeDetailFragment fragment = new RecipeDetailFragment();

            Bundle b = getIntent().getExtras();
            fragment.setArguments(b);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipeDetailContainer, fragment)
                    .commit();
        }
    }

    @Override
    public void onDetailInteraction(Recipe recipe) {

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
