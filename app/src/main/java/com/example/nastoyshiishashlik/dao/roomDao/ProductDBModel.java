package com.example.nastoyshiishashlik.dao.roomDao;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.model.Product;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ProductDBModel extends AndroidViewModel {
    private static final String TAG = ProductDBModel.class.getCanonicalName();
    private RoomDB database;

    @SuppressLint("CheckResult")
    public ProductDBModel() {
        super(App.getContext());

        database = RoomDB.getInstance(App.getContext());
    }

    public Single<List<Product>> getAll(){
        return database.mainDao().getAll();
    }

    public Single<List<Product>> getByHit(int hit) {
        return database.mainDao().getByHit(hit);
    }

    public Single<List<Product>> getByDishes(String sDishes){return database.mainDao().getByDishes(sDishes);}

    public Single<Product> getById(int iId){
        return database.mainDao().getById(iId);
    }
}
