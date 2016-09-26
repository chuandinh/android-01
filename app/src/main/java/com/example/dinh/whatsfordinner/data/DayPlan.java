package com.example.dinh.whatsfordinner.data;

/**
 * Created by dinh_c on 9/14/2016.
 */
public class DayPlan {
    public String day;
    public Recipe breakfast;
    public Recipe lunch;
    public Recipe dinner;

    public DayPlan(String day, Recipe breakfast, Recipe lunch, Recipe dinner)
    {
        this.day = day;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }
}
