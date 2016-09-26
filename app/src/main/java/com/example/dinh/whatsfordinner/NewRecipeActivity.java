package com.example.dinh.whatsfordinner;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dinh.whatsfordinner.data.Ingredient;
import com.example.dinh.whatsfordinner.data.MyDataContext;
import com.example.dinh.whatsfordinner.data.Recipe;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class NewRecipeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    public static final String NON_SELECTED_INGREDIENT_STRING = "Select item...";
    private static final int SELECT_PHOTO = 1001;
    private static final int START_FROM_ID = 100;
    private static final int MAX_INGREDIENTS = 10;

    protected Recipe recipe;
    protected MyDataContext app = MyDataContext.getInstance();

    protected ArrayList<String> spinnerData;
    protected ArrayAdapter<String> spinnerAdapter;

    private int recipeIndex = -1;
    private EditText recipeNameEditText;
    private ImageView recipeImage;
    private ArrayList<Spinner> ingredientSpinners = new ArrayList<>(10);
    private ArrayList<EditText> ingredientValues = new ArrayList<>(10);
    private EditText directionsEditText;

    private EditText caloriesEditText;
    private EditText carbohydratesEditText;
    private EditText mineralsEditText;
    private EditText vitaminsEditText;
    private EditText sugarEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey(MainActivity.BUNDLE_INDEX_KEY)) {
            recipeIndex = b.getInt(MainActivity.BUNDLE_INDEX_KEY, -1);
        }

        if (recipeIndex != -1) {
            recipe = app.getRecipes().get(recipeIndex);

            this.setTitle("Edit Recipe");
        }
        else
            recipe = app.newRecipe();

        recipeNameEditText = (EditText) findViewById(R.id.recipeNameEditText);
        recipeImage = (ImageView) findViewById(R.id.recipeImageView);
        directionsEditText = (EditText) findViewById(R.id.directionsEditText);

        caloriesEditText = (EditText) findViewById(R.id.editTextCalories);
        carbohydratesEditText = (EditText) findViewById(R.id.editTextCarbohydrates);
        mineralsEditText = (EditText) findViewById(R.id.editTextMinerals);
        vitaminsEditText = (EditText) findViewById(R.id.editTextVitamins);
        sugarEditText = (EditText) findViewById(R.id.editTextSugar);

        //Set Values to the view objects
        recipeNameEditText.setText(recipe.name);

        // Load the Recipe image
        if (recipe.imageUrl != null) {
            if (recipe.imageUrl.toString().toLowerCase().startsWith("http://")) {
                try {
                    new DownloadImageTask(recipeImage).execute(recipe.imageUrl.toString());
                } catch (Exception ex) {
                    recipeImage.setImageResource(R.drawable.recipe_default);
                }
            } else {
                try {
                    final Uri imageUri = recipe.imageUrl;

                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    recipeImage.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    //e.printStackTrace();
                    //Toast.makeText(this, "Error loading the image", Toast.LENGTH_SHORT).show();
                    //recipeImage.setImageDrawable(getResources().getDrawable(R.drawable.recipe_default));
                    recipeImage.setImageResource(R.drawable.recipe_default);
                }
            }


            
        }

        directionsEditText.setText(recipe.directions);
        loadSpinnerData();

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerData);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.ingredientsContainer);

        for (int i = 0; i < MAX_INGREDIENTS; i++) {
            Spinner spinner = new Spinner(this);
            spinner.setId(START_FROM_ID + i);

            EditText valueEditText = new EditText(this);
            valueEditText.setId(START_FROM_ID * 2 + i);
            valueEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            //valueEditText.setWidth(50);

            //Make sure you have valid layout parameters.
            //spinner.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams paramsValue = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsValue.width = 150;
            paramsValue.rightMargin = 15;

            if (i == 0) {
                paramsValue.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                paramsValue.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                paramsValue.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);

                linearLayout.addView(valueEditText, paramsValue);

                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.LEFT_OF, START_FROM_ID * 2 + i);
                params.addRule(RelativeLayout.ALIGN_TOP, START_FROM_ID * 2 + i);
                params.addRule(RelativeLayout.ALIGN_BOTTOM, START_FROM_ID * 2 + i);

            } else {
                paramsValue.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                paramsValue.addRule(RelativeLayout.BELOW, START_FROM_ID * 2 + i - 1);

                linearLayout.addView(valueEditText, paramsValue);

                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.LEFT_OF, START_FROM_ID * 2 + i);
                params.addRule(RelativeLayout.ALIGN_TOP, START_FROM_ID * 2 + i);
                params.addRule(RelativeLayout.ALIGN_BOTTOM, START_FROM_ID * 2 + i);
            }

            spinner.setAdapter(this.spinnerAdapter);

            ingredientSpinners.add(spinner);
            ingredientValues.add(valueEditText);

            linearLayout.addView(spinner, params);
        }

        int i = 0;
        for (int index : recipe.ingredients.keySet()) {
            ingredientSpinners.get(i).setSelection(index + 1);

            String value = recipe.ingredients.get(index).toString();
            if (value.endsWith(".0")) value = value.replace(".0", "");

            ingredientValues.get(i).setText(value);

            i++;
        }

        recipeNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String recipeName = recipeNameEditText.getText().toString();

                    if (app.isDuplicated(recipeName, recipeIndex)) {
                        recipeNameEditText.setError("Name already exists!");
                    }
                }
            }
        });

        if (recipe.nutrition.calories > 0)
            caloriesEditText.setText(String.valueOf(recipe.nutrition.calories));
        if (recipe.nutrition.carbohydrates > 0)
            carbohydratesEditText.setText(String.valueOf(recipe.nutrition.carbohydrates));
        if (recipe.nutrition.minerals > 0)
            mineralsEditText.setText(String.valueOf(recipe.nutrition.minerals));
        if (recipe.nutrition.vitamins > 0)
            vitaminsEditText.setText(String.valueOf(recipe.nutrition.vitamins));
        if (recipe.nutrition.sugar > 0)
            sugarEditText.setText(String.valueOf(recipe.nutrition.sugar));

        // Get value of the Spinner item
        //String.valueOf(spinnerItem1.getSelectedItem()
    }


    private void loadSpinnerData() {
        if (this.spinnerData == null) this.spinnerData = new ArrayList<>();

        spinnerData.clear();
        spinnerData.add(NON_SELECTED_INGREDIENT_STRING);
        for (Ingredient ingredient : app.getIngredients()) {
            spinnerData.add(ingredient.toString());
        }
    }

    public void onAddRecipeImage(View view) {
        //Toast.makeText(this, "Add Image Clicked", Toast.LENGTH_SHORT).show();

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.image_popup_menu);
        popupMenu.show();
    }

    /**
     * Popup for picking new Image from photo or internet
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_gallery:
                //Toast.makeText(this, "Local Storage", Toast.LENGTH_SHORT).show();

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);

                return true;
            case R.id.item_url:
                //Toast.makeText(this, "Url", Toast.LENGTH_SHORT).show();
                final EditText txtUrl = new EditText(this);
                txtUrl.setHint("http://");

                new AlertDialog.Builder(this)
                        .setTitle("Enter Image Url")
                        //.setMessage("Paste in an image Url")
                        .setView(txtUrl)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String url = txtUrl.getText().toString();

                                recipe.imageUrl = Uri.parse(url);

                                try {
                                    new DownloadImageTask(recipeImage).execute(recipe.imageUrl.toString());
                                } catch (Exception ex) {
                                    recipeImage.setImageResource(R.drawable.recipe_default);
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();

                return true;
        }

        return true;
    }


    /**
     * Show popup for adding new Ingredient
     * @param view
     */
    public void onAddIngredient(View view) {
        //Toast.makeText(this, "Add Ingredient Clicked", Toast.LENGTH_SHORT).show();
        LayoutInflater factory = LayoutInflater.from(view.getContext());

        //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.ingredient_input_new, null);

        final EditText ingredientNameEditText = (EditText) textEntryView.findViewById(R.id.editTextIngredientName);
        final EditText unitNameEditText = (EditText) textEntryView.findViewById(R.id.editTextUnitName);

        final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        //alert.setIcon(R.drawable.icon);
        alert.setTitle("New Ingredient").setView(textEntryView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String ingredientName = ingredientNameEditText.getText().toString();
                        String unitName = unitNameEditText.getText().toString();

                        if (ingredientName.isEmpty() || unitName.isEmpty()) return;

                        Ingredient newIngredient = new Ingredient(ingredientName, unitName);
                        app.getIngredients().add(newIngredient);

                        // Notify all the spinners with the change
                        loadSpinnerData();
                        spinnerAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

        alert.show();
    }

    /**
     * Save information from UI to recipe object and add to arraylist
     * @param v
     */
    public void onSaveClicked(View v) {
        String recipeName = recipeNameEditText.getText().toString();

        if (recipeName.isEmpty()) {
            Toast.makeText(this, "Error! Please enter a dish name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (app.isDuplicated(recipeName, recipeIndex)) {
            Toast.makeText(this, "Error! Recipe Name already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        recipe.name = recipeName;
        recipe.directions = directionsEditText.getText().toString();

        recipe.ingredients.clear();
        for (int i = 0; i < ingredientSpinners.size(); i++) {
            Spinner spinner = ingredientSpinners.get(i);

            int index = spinner.getSelectedItemPosition();
            //Ingredient ingredient = app.getIngredients().get(index);

            double value = 0;
            String textValue = ingredientValues.get(i).getText().toString();

            if (textValue.length() > 0)
                value = Double.parseDouble(textValue);

            if (index > 0 && value > 0) {
                recipe.addIngredient(index - 1, value); //Exclude the first item
            }
        }

        //Nutrition
        if (caloriesEditText.getText().toString().length() > 0)
            recipe.nutrition.calories = Integer.parseInt(caloriesEditText.getText().toString());
        if (carbohydratesEditText.getText().toString().length() > 0)
            recipe.nutrition.carbohydrates = Double.parseDouble(carbohydratesEditText.getText().toString());
        if (mineralsEditText.getText().toString().length() > 0)
            recipe.nutrition.minerals = Double.parseDouble(mineralsEditText.getText().toString());
        if (vitaminsEditText.getText().toString().length() > 0)
            recipe.nutrition.vitamins = Double.parseDouble(vitaminsEditText.getText().toString());
        if (sugarEditText.getText().toString().length() > 0)
            recipe.nutrition.sugar = Double.parseDouble(sugarEditText.getText().toString());

        if (recipeIndex == -1) //New
        {
            app.getRecipes().add(recipe);
        }

        //Close this activty
        finish();
    }

    /**
     * Caputure results from other activities
     * @param requestCode
     * @param resultCode
     * @param imageReturnedIntent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        recipeImage.setImageBitmap(selectedImage);

                        recipe.imageUrl = imageUri;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
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