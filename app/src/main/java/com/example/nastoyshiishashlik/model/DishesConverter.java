package com.example.nastoyshiishashlik.model;

import androidx.room.TypeConverter;

public class DishesConverter {
    @TypeConverter
    public static Dishes fromStringToDishes(String strDishes) {
        return strDishes == null ? null : Dishes.valueOf(strDishes);
    }

    @TypeConverter
    public static String fromDishesToString (Dishes dishes) {
        return dishes == null ? null : dishes.getTitle();
    }
}
