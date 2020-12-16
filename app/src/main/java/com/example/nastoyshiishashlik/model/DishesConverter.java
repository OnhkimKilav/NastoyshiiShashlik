package com.example.nastoyshiishashlik.model;

import androidx.room.TypeConverter;

public class DishesConverter {
    @TypeConverter
    public String fromHobbies(Dishes dishes) {
        return dishes.getTitle();
    }

    @TypeConverter
    public Dishes toHobbies(String data) {
        return Dishes.valueOf(data);
    }
}
