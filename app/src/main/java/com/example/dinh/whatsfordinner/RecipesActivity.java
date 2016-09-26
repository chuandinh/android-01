package com.example.dinh.whatsfordinner;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dinh.whatsfordinner.data.MyDataContext;
import com.example.dinh.whatsfordinner.data.Recipe;

public class RecipesActivity extends AppCompatActivity
        implements RecipeListFragment.OnFragmentInteractionListener, RecipeDetailFragment.OnFragmentInteractionListener {

    protected MyDataContext app = MyDataContext.getInstance();
    protected ListFragment recipeListView;

    private static final int VIEW_REQUEST_CODE = 1001;
    private static final int EDIT_REQUEST_CODE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipeListView = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.recipeListFragment);
    }

    /**
     * Callback from RecipeListFragment
     * @param index
     * @param isLongClicked
     */
    @Override
    public void onListInteraction(int index, boolean isLongClicked) {
        if (isLongClicked) {
            //Toast.makeText(this, "Item " + index + " clicked", Toast.LENGTH_SHORT).show();

            Bundle b = new Bundle();
            b.putInt(MainActivity.BUNDLE_INDEX_KEY, index);

            Intent intent = new Intent(this, NewRecipeActivity.class);
            intent.putExtras(b);
            startActivityForResult(intent, EDIT_REQUEST_CODE);
        } else {
            //Add to the Meals list
            Recipe recipe = app.getRecipes().get(index);
            app.addMeal(recipe);
            Toast.makeText(this, recipe.name + " added to Meals", Toast.LENGTH_SHORT).show();

            Configuration config = getResources().getConfiguration();

            Bundle b = new Bundle();
            b.putInt(MainActivity.BUNDLE_INDEX_KEY, index);

            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                RecipeDetailFragment fragment = new RecipeDetailFragment();

                fragment.setArguments(b);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeDetailContainer, fragment)
                        .commit();
            } else {
                // No need to show in portrait mode
                //Intent intent = new Intent(this, RecipeDetailActivity.class);
                //intent.putExtras(b);
                //startActivityForResult(intent, VIEW_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        RecipeArrayAdapter adapter = (RecipeArrayAdapter) recipeListView.getListAdapter();
        adapter.notifyDataSetChanged();
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
