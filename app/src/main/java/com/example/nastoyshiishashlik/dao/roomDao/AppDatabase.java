package com.example.nastoyshiishashlik.dao.roomDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.nastoyshiishashlik.model.Product;


@Database(entities = {Product.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}


