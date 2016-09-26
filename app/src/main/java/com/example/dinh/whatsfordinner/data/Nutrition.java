package com.example.dinh.whatsfordinner.data;

/**
 * Created by dinh_c on 9/14/2016.
 */
public class Nutrition {
    public int calories;
    public double carbohydrates;
    public double minerals;
    public double vitamins;
    public double sugar;

    public void addValues(Recipe recipe)
    {
        this.calories += recipe.nutrition.calories;
        this.carbohydrates += recipe.nutrition.carbohydrates;
        this.minerals += recipe.nutrition.minerals;
        this.vitamins += recipe.nutrition.vitamins;
        this.sugar += recipe.nutrition.sugar;
    }
}
