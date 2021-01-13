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

    public ProductDBModel() {
        super(App.getContext());
        database = RoomDB.getInstance(App.getContext());

        database.mainDao().getAll()
                .subscribeOn(Schedulers.io())
                .subscribe(products -> {
                            //Read data from CSV fail
                            if (products.size() == 0) {
                                ArrayList<Product> productsFromCSV = new ArrayList<>(new ReadCSV().readProducts());
                                for (Product product : productsFromCSV) {
                                    //Insert text in database
                                    database.mainDao().insert(product);
                                }
                            }
                            Log.d(TAG, "ProductDBModel: product's table length is " + products.size());
                        },
                        throwable -> Log.e(TAG, "ProductDBModel: Error! Getting all file from product's table didn't work")
                );


    }

    public Single<List<Product>> getAll(){
        return database.mainDao().getAll();
    }

    public Single<List<Product>> getByHit(int hit) {
        return database.mainDao().getByHit(hit);
    }

    public Single<List<Product>> getByDishes(String sDishes){return database.mainDao().getByDishes(sDishes);}
}
