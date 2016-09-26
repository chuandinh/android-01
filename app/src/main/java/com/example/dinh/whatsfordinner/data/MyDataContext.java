package com.example.dinh.whatsfordinner.data;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;

import com.example.dinh.whatsfordinner.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dinh_c on 9/19/2016.
 */

public class MyDataContext {
    //public static final String[] Array_Days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private static MyDataContext singleInstance = null;

    private List<Recipe> recipes;
    private List<Ingredient> ingredients;
    private Nutrition nutritionGoal;
    private Map<Recipe, Integer> meals;
    private Map<Ingredient, Double> shoppingList;
    private List<DayPlan> mealPlans;

    //Private constructor
    private MyDataContext(){
        recipes = Recipe.getRecipes();
        ingredients = Ingredient.getIngredients();
        meals = new LinkedHashMap<Recipe, Integer>();
        shoppingList = new LinkedHashMap<Ingredient, Double>();
        mealPlans = new ArrayList<DayPlan>();

        nutritionGoal = new Nutrition();
        nutritionGoal.calories = 3000;
        nutritionGoal.carbohydrates = 500;
        nutritionGoal.minerals = 200;
        nutritionGoal.vitamins = 300;
        nutritionGoal.sugar = 400;
    }

    //Get Instance
    public static MyDataContext getInstance(){
        if(singleInstance == null)
        {
            singleInstance = new MyDataContext();
        }
        return singleInstance;
    }

    public List<Recipe> getRecipes() {return recipes;}
    public List<Ingredient> getIngredients() { return ingredients;}
    public Map<Recipe, Integer> getMeals() { return meals; }
    public Map<Ingredient, Double> getShoppingList() { return shoppingList; }
    public List<DayPlan> getMealPlans() { return mealPlans; }
    public void addMealPlans(String[] days) {
        for(String day : days)
            mealPlans.add(new DayPlan(day, null, null, null));
    }

    public Nutrition getNutritionGoal() { return this.nutritionGoal; }


    /**
     * Get DayPlay by day
     * @param day day in string
     * @return DayPlan object
     */
    public DayPlan getDayPlan(String day)
    {
        for(DayPlan d : this.getMealPlans())
            if(d.day.compareTo(day) == 0) return d;

        return null;
    }

    /**
     * Add recipe to available meals
     * @param recipe
     * @return new available quantity
     */
    public int addMeal(Recipe recipe)
    {
        Integer value = meals.get(recipe);
        if(value == null) value = 0;

        meals.put(recipe, value + 1);

        return value;
    }

    /**
     * Add recipe to available meals
     * @param recipe
     * @return new available quantity
     */
    public int removeMeal(Recipe recipe)
    {
        Integer value = meals.get(recipe);
        if(value == null) return 0;

        if(value <= 1)
        {
            meals.remove(recipe);
            return 0;
        }
        else
        {
            meals.put(recipe, value - 1);
            return value;
        }
    }

    /**
     * Add ingredients of the recipe to the shopping list
     * @param recipe recipe to add
     */
    public void addToShoppingList(Recipe recipe)
    {
        //Add ingredients to the shopping list
        for(int ingredientIndex : recipe.ingredients.keySet())
            addToShoppingList(getIngredients().get(ingredientIndex), recipe.ingredients.get(ingredientIndex));
    }

    /**
     * Remove ingredients of the recipe form the shopping list
     * @param recipe recipe to remove
     */
    public void removeFromShoppingList(Recipe recipe)
    {
        //Subtract ingredients to the shopping list
        for(int ingredientIndex : recipe.ingredients.keySet())
            addToShoppingList(getIngredients().get(ingredientIndex), -recipe.ingredients.get(ingredientIndex));
    }

    /**
     * Add Ingredient to the shopping list
     * @param ingredient Ingredient object
     * @param value quantity to add
     * @return new quantity in the shopping list
     */
    public double addToShoppingList(Ingredient ingredient, double value)
    {
        Double oldValue = shoppingList.get(ingredient);
        if(oldValue == null) oldValue = 0.;

        double newValue = oldValue + value;

        if(newValue < 0) newValue = 0;

        shoppingList.put(ingredient, newValue);

        return newValue;
    }

    /**
     * Check if a recipe name already exists
     * @param recipeName recipe name
     * @param recipeIndex recipe index to be excluded
     * @return true if duplicated, otherwise false
     */
    public boolean isDuplicated(String recipeName, int recipeIndex)
    {
        for(int i=0; i< recipes.size(); i++)
            if(recipes.get(i).name.toLowerCase().compareTo(recipeName.toLowerCase()) == 0 && i != recipeIndex) return true;

        return false;
    }

    /**
     * Get ingredients in text for display in the Detail View
     * @param recipe recipe object
     * @return ingredients in string format
     */
    public String getIngredientsText(Recipe recipe)
    {
        String result = "";
        for(int i : recipe.ingredients.keySet()) {
            Ingredient ingredient = ingredients.get(i);

            result += "* " + ingredient.name + " (1 " + ingredient.unitName + ")\n";
        }

        return result;
    }


    public Recipe newRecipe()
    {
        return new Recipe(this.recipes.size()+1);
    }


    /**
     * get uri to any resource type
     * @param context - context
     * @param resId - resource id
     * @throws Resources.NotFoundException if the given ID does not exist.
     * @return - Uri to resource by given id
     */
    public static final Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        /** Return a Resources instance for your application's package. */
        Resources res = context.getResources();
        /**
         * Creates a Uri which parses the given encoded URI string.
         * @param uriString an RFC 2396-compliant, encoded URI
         * @throws NullPointerException if uriString is null
         * @return Uri for this given uri string
         */
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
        /** return uri */
        return resUri;
    }


    /**
     * Format double
     * @param value
     * @return
     */
    public static String formatDouble(double value)
    {
        int intValue = (int)value;
        double decimal = (value - (double)intValue);

        String result = "";
        if(decimal >= 0.25 && decimal < 0.5) result += "1/4";
        if(decimal >= 0.5 && decimal < 0.75) result += "1/2";
        if(decimal >= 0.75 && decimal < 1) result += "3/4";

        if(intValue > 0) result = "" + intValue + " " + result;
        if(result.length() == 0) result = "0";

        return result;
    }
}
