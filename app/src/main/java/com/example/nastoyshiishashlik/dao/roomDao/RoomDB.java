package com.example.nastoyshiishashlik.dao.roomDao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.nastoyshiishashlik.models.DishesConverter;
import com.example.nastoyshiishashlik.models.ProductModel;

@Database(entities = {ProductModel.class}, version = 4, exportSchema = false)
@TypeConverters({DishesConverter.class})
public abstract class RoomDB extends RoomDatabase {
    //Create database instance
    private static  RoomDB database;
    //Define database name
    private static String DATABASE_NAME = "shashlik";

    public synchronized static RoomDB getInstance(Context context){
        //Check condition
        if (database == null){
            //When database is null
            //Initialize database
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        //Return database
        return database;
    }

    //Create Dao
    public abstract MainDao mainDao();
}
