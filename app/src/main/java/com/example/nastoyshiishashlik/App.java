package com.example.nastoyshiishashlik;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.room.Room;

import com.example.nastoyshiishashlik.dao.roomDao.AppDatabase;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDAOImpl;

public class App extends Application {
    private static App mContext;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        database =  Room.databaseBuilder(this,
                AppDatabase.class, "database")
                .addMigrations(ProductDAOImpl.MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();
    }

    public static App getContext(){
        return mContext;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
