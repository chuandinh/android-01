package com.example.dinh.whatsfordinner.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinh on 9/8/16.
 */
public class Ingredient {
    public String name;
    public String unitName;
    //public double unitValue;

    public Ingredient(String name, String unitName)
    {
        this.name = name;
        this.unitName = unitName;
    }

    /**
     * Sample Ingredient list
     * @return
     */
    public static List<Ingredient> getIngredients()
    {
        List<Ingredient> list = new ArrayList<>();

        list.add(new Ingredient("Avocado ", "pc"));
        list.add(new Ingredient("BBQ Sauce Of Choice ", "bottle"));
        list.add(new Ingredient("Bell Peppers ", "pc"));
        list.add(new Ingredient("Black Berries ", "pc"));
        list.add(new Ingredient("Boneless, Skinless Chicken Breasts ", "pc"));
        list.add(new Ingredient("Broccoli ", "pc"));
        list.add(new Ingredient("Butter ", "tsp"));
        list.add(new Ingredient("Can Pineapple Chunks ", "can"));
        list.add(new Ingredient("Cantaloupe ", "pc"));
        list.add(new Ingredient("Cheese, Shredded ", "cup"));
        list.add(new Ingredient("Cherry Tomatoes ", "pc"));
        list.add(new Ingredient("Chili Powder ", "tsp"));
        list.add(new Ingredient("Cooked Shrimp ", "lb"));
        list.add(new Ingredient("Corn Chips ", "bag"));
        list.add(new Ingredient("Corn, Heated ", "cup"));
        list.add(new Ingredient("Cornstarch ", "tsp"));
        list.add(new Ingredient("Couscous ", "cup"));
        list.add(new Ingredient("Cream of Mushroom Soup ", "can"));
        list.add(new Ingredient("Dark Brown Sugar ", "tsp"));
        list.add(new Ingredient("Egg Noodles ", "bag"));
        list.add(new Ingredient("Fettuccini Pasta ", "lb"));
        list.add(new Ingredient("Finely Chopped Onion ", "cup"));
        list.add(new Ingredient("Flour ", "flour"));
        list.add(new Ingredient("Fresh Basil, Coarsely Chopped ", "cup"));
        list.add(new Ingredient("Fresh Mint Leaves, Loosely Packed ", "cup"));
        list.add(new Ingredient("Fresh Tomatoes, Chopped ", "cup"));
        list.add(new Ingredient("Garlic Powder ", "tsp"));
        list.add(new Ingredient("Garlic, Minced ", "clove"));
        list.add(new Ingredient("Grated Parmesan Cheese ", "cup"));
        list.add(new Ingredient("Green Pepper, Diced ", "pepper"));
        list.add(new Ingredient("Ground Beef ", "lb"));
        list.add(new Ingredient("Half And Half ", "cup"));
        list.add(new Ingredient("Honey ", "cup"));
        list.add(new Ingredient("Ice Cream (Optional) ", "pt"));
        list.add(new Ingredient("Italian Dressing ", "bottle"));
        list.add(new Ingredient("Lemon Curd ", "jar"));
        list.add(new Ingredient("Oil ", "tsp"));
        list.add(new Ingredient("Olive Oil ", "tsp"));
        list.add(new Ingredient("Onion ", "pc"));
        list.add(new Ingredient("Onion, Diced ", "cup"));
        list.add(new Ingredient("Pepper ", "tsp"));
        list.add(new Ingredient("Pineapple Chunks, Drained ", "can"));
        list.add(new Ingredient("Pork Baby Back Ribs", "lb"));
        list.add(new Ingredient("Portobello Mushrooms ", "bag"));
        list.add(new Ingredient("Refried Beans or Black Beans ", "can"));
        list.add(new Ingredient("Salsa ", "cup"));
        list.add(new Ingredient("Salt ", "tsp"));
        list.add(new Ingredient("Seasoned French Fries ", "bag"));
        list.add(new Ingredient("Sliced Black Olives ", "can"));
        list.add(new Ingredient("Sour Cream ", "cup"));
        list.add(new Ingredient("Soy Sauce ", "tsp"));
        list.add(new Ingredient("Squash, Sliced (Green and Yellow) ", "cup"));
        list.add(new Ingredient("Steak, Chicken or Pork ", "lb"));
        list.add(new Ingredient("Strawberries ", "bag"));
        list.add(new Ingredient("Taco Seasoning ", "bag"));
        list.add(new Ingredient("Teriyaki Sauce ", "tsp"));
        list.add(new Ingredient("Toasted Pine Nuts ", "cup"));
        list.add(new Ingredient("Uncooked White Rice ", "cup"));
        list.add(new Ingredient("Water ", "cup"));
        list.add(new Ingredient("Whole Mushrooms ", "lb"));
        list.add(new Ingredient("Worcestershire Sauce ", "tsp"));

        return list;
    }


    @Override
    public String toString() {
        return this.name + " (" + this.unitName + ")";
    }
}
