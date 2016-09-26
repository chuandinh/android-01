package com.example.dinh.whatsfordinner.data;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import android.net.Uri;

/**
 * Created by dinh on 9/8/16.
 */
public class Recipe {
    public static final String RECIPE_ID = "recipeId";
    public static final String RECIPE_NAME = "recipeName";

    int id;
    public String name;
    public Uri imageUrl;
    public String directions;
    public Map<Integer, Double> ingredients;
    public Nutrition nutrition;

    public Recipe(int id)
    {
        this.id = id;

        this.ingredients = new LinkedHashMap<>();
        this.nutrition = new Nutrition();
    }

    public Recipe(int id, String name, Uri imageUrl, String directions)
    {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.directions = directions;

        this.ingredients = new LinkedHashMap<>();
        this.nutrition = new Nutrition();
    }

    public void addIngredient(int ingredientId, double value)
    {
        this.ingredients.put(ingredientId, value);
        //this.ingredients.add(ingredientId);
    }

    public static List<Recipe> getRecipes()
    {
        List<Recipe> list = new ArrayList<>();
        List<Ingredient> ingredients = Ingredient.getIngredients();

        //Nachos Grande
        {
            String recipeName = "Nachos Grande";
            String directions = "Stir the soup and salsa in a 2-quart saucepan.\n" +
                    "Cook the beef and onion, " +
                    "stirring often to separate meat. Pour off any fat. Stir 1/2 cup soup mixture into the skillet. " +
                    "Cook until the beef mixture is hot and bubbling.\n" +
                    "Heat the remaining soup mixture over medium heat until it's hot and bubbling.\n" +
                    "Place the chips onto a serving platter and top with the beef mixture. " +
                    "Spoon the soup mixture over the beef mixture. Top with the tomato and jalapeno pepper, if desired.";

            Uri imageUrl = Uri.parse("http://images.media-allrecipes.com/userphotos/600x600/1475913.jpg");

            Recipe newRecipe = new Recipe(0, recipeName, imageUrl, directions);

            newRecipe.addIngredient(30, 1);
            newRecipe.addIngredient(21, 0.25);
            newRecipe.addIngredient(54, 1);
            newRecipe.addIngredient(44, 0.5);
            newRecipe.addIngredient(13, 1);
            newRecipe.addIngredient(9, 4);
            newRecipe.addIngredient(49, 1);
            newRecipe.addIngredient(45, 1.5);
            //newRecipe.addIngredient(14, 0.5);
            newRecipe.addIngredient(29, 0.5);
            //newRecipe.addIngredient(0, 1);
            newRecipe.addIngredient(48, 2);

            //Nutrition
            newRecipe.nutrition.calories = 450;
            newRecipe.nutrition.carbohydrates = 20;
            newRecipe.nutrition.vitamins = 3;
            newRecipe.nutrition.minerals = 2;
            newRecipe.nutrition.sugar = 45;

            list.add(newRecipe);
        }

        //Aloha Chicken
        {
            String recipeName = "Aloha Chicken";
            String directions = "Preheat oven to 400 degrees F (200 degrees C).\n" +
                    "Arrange chicken pieces in a single layer in a well greased 9x13 inch baking dish. " +
                    "In a small bowl mix together the ginger, paprika, onion powder and garlic salt. " +
                    "Add the vinegar and mix well. Divide this mixture. Brush 1/2 over the chicken pieces and bake in the preheated oven for 15 minutes.\n" +
                    "Turn the chicken pieces, baste with the remaining 1/2 of the vinegar mixture " +
                    "and bake for 15 minutes longer. Meanwhile, in a medium bowl combine the ketchup, " +
                    "soy sauce, pineapple and brown sugar. When chicken baking time is up, " +
                    "spoon the pineapple/soy mixture over the chicken. Bake for another 30 minutes. Serve while still hot.";
            Uri imageUrl = Uri.parse("http://images.media-allrecipes.com/userphotos/250x250/82706.jpg");
            Recipe newRecipe = new Recipe(1, recipeName, imageUrl, directions);

            newRecipe.addIngredient(4, 4);
            newRecipe.addIngredient(22, 1);
            newRecipe.addIngredient(36, 1);
            newRecipe.addIngredient(7, 1);
            newRecipe.addIngredient(15, 1);
            newRecipe.addIngredient(32, 1);
            newRecipe.addIngredient(55, 1);
            newRecipe.addIngredient(50, 1);
            newRecipe.addIngredient(40, 0.25);

            //Nutrition
            newRecipe.nutrition.calories = 300;
            newRecipe.nutrition.carbohydrates = 15;
            newRecipe.nutrition.vitamins = 1;
            newRecipe.nutrition.minerals = 1;
            newRecipe.nutrition.sugar = 20;

            list.add(newRecipe);
        }

        //Grilled Italian Chicken
        {
            String recipeName = "Grilled Italian Chicken";
            String directions = "In a shallow baking dish, mix the salad dressing, garlic powder, and salt. " +
                    "Place the chicken in the bowl, and turn to coat. Marinate in the refrigerator at least 4 hours. " +
                    "(For best results, marinate overnight.)\n" +
                    "Preheat the grill for high heat.\n" +
                    "Lightly oil grate. Discard marinade, and grill chicken 8 minutes on each side, or until juices run clear.";
            Uri imageUrl = Uri.parse("http://images.media-allrecipes.com/userphotos/720x405/665982.jpg");

            Recipe newRecipe = new Recipe(2, recipeName, imageUrl, directions);

            newRecipe.addIngredient(4, 2);
            newRecipe.addIngredient(34, 1);

            //Nutrition
            newRecipe.nutrition.calories = 350;
            newRecipe.nutrition.carbohydrates = 15;
            newRecipe.nutrition.vitamins = 4;
            newRecipe.nutrition.minerals = 1;
            newRecipe.nutrition.sugar = 40;

            list.add(newRecipe);
        }

        //Shish Kebabs
        {
            String recipeName = "Shish Kebabs";
            String directions = "Soak the skewers in water for 1 hour.\n" +
                    "Onto each skewer, alternately thread green bell pepper chunks, shrimp, zucchini slices, chicken, cherry tomatoes, steak, onion, mushroom; repeat until skewer is full. Drizzle olive oil over the prepared skewers; season with 1/4 teaspoon garlic powder, salt, and black pepper. Allow to marinate in refrigerator at least 3 hours, turning every 45 minutes.\n" +
                    "Preheat an outdoor grill for medium-high heat, and lightly oil the grate.\n" +
                    "Bring a pot of lightly salted water to a rolling boil. Cook the fettuccini in the boiling water, stirring occasionally, until cooked through yet firm to the bite, about 8 minutes; drain. Toss the hot pasta in a large bowl with the softened butter and 1/4 teaspoon garlic powder.\n" +
                    "While the pasta boils, cook the skewers on the preheated grill, turning frequently until nicely browned on all sides, the shrimp are pink and opaque, and the meat is no longer pink in the center, 10 to 15 minutes. Push the cooked meat and vegetables off the skewer onto the tossed pasta.";
            Uri imageUrl = Uri.parse("http://images.media-allrecipes.com/userphotos/250x250/682355.jpg");

            Recipe newRecipe = new Recipe(2, recipeName, imageUrl, directions);

            newRecipe.addIngredient(52, 1);
            newRecipe.addIngredient(38, 1);
            newRecipe.addIngredient(2, 2);
            newRecipe.addIngredient(59, 1);
            newRecipe.addIngredient(10, 1);
            newRecipe.addIngredient(41, 1);

            //Nutrition
            newRecipe.nutrition.calories = 500;
            newRecipe.nutrition.carbohydrates = 30;
            newRecipe.nutrition.vitamins = 4;
            newRecipe.nutrition.minerals = 1;
            newRecipe.nutrition.sugar = 50;

            list.add(newRecipe);
        }

        //Shrimp Fettuccini Pasta
        {
            String recipeName = "Shrimp Fettuccini Pasta";
            String directions = "1. Cook 3/4 lb fettuccini noodles in salted water according to package instructions and drain. Don’t rinse (this helps the sauce stick to the noodles better).\n" +
                    "2. Lightly season shrimp with salt, pepper and paprika. Preheat a large skillet to med/high and add 1 Tbsp oil. Once oil is hot, add shrimp in a single layer and cook 1-2 min per side or just until fully cooked and no longer translucent." +
                    "3. In same pan, over medium/high heat, add 2 Tbsp butter and onion and sauté onion till golden. Stir in garlic and sauté another minute. Stir in 1/3 cup white wine and reduce to 25% scraping the bottom to deglaze the pan." +
                    "4.Stir in Cream and simmer for 2 min. Next, sprinkle the top with 1/3 cup parmesan (or add to taste) and stir just until creamy and smooth and remove from heat. Don’t boil or the cheese will separate from the cream. Add about 1/4 tsp paprika and season with S&P to taste." +
                    "5. Add the cooked shrimp and drained (un-rinsed pasta).  Garnish with parsley, basil, extra parmesan or freshly cracked pepper if desired." +
                    "";
            Uri imageUrl = Uri.parse("http://natashaskitchen.com/wp-content/uploads/2014/02/Shrimp-Alfredo-3-3.jpg");

            Recipe newRecipe = new Recipe(2, recipeName, imageUrl, directions);

            newRecipe.addIngredient(20, 0.5);
            newRecipe.addIngredient(6, 1);
            newRecipe.addIngredient(12, 0.5);
            newRecipe.addIngredient(31, 1);
            newRecipe.addIngredient(28, 6);

            //Nutrition
            newRecipe.nutrition.calories = 250;
            newRecipe.nutrition.carbohydrates = 10;
            newRecipe.nutrition.vitamins = 5;
            newRecipe.nutrition.minerals = 2;
            newRecipe.nutrition.sugar = 15;

            list.add(newRecipe);
        }

        Random rand = new Random();
        for(int i=1; i<=10; i++)
        {
            String directions = "";
            Uri imageUrl = null;
            String recipeName = "Random Recipe " + i;
            if(i== 1) recipeName = "Test";

            //Generate dummy direction
            for(int j = 0; j < rand.nextInt(10); j++)
                directions += "This is the direction. ";

            Recipe newRecipe = new Recipe(i, recipeName, imageUrl, directions);

            //Generate dummy ingredients
            for(int j = 0; j < rand.nextInt(10); j++) {
                int ingredientId = rand.nextInt(ingredients.size());
                double value = rand.nextDouble()*5;

                newRecipe.addIngredient(ingredientId, rand.nextInt(3) + 1);
            }

            //Nutrition
            newRecipe.nutrition.calories = rand.nextInt(500);
            newRecipe.nutrition.carbohydrates = rand.nextInt(50);
            newRecipe.nutrition.vitamins = rand.nextInt(5);
            newRecipe.nutrition.minerals = rand.nextInt(5);
            newRecipe.nutrition.sugar = rand.nextInt(50);

            list.add(newRecipe);
        }

        return list;
    }
}
