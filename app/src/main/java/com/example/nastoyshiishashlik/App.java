package com.example.nastoyshiishashlik;

import android.app.Application;
import android.util.Log;

import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.dao.roomDao.ReadCSV;
import com.example.nastoyshiishashlik.dao.roomDao.RoomDB;
import com.example.nastoyshiishashlik.model.Product;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

public class App extends Application {
    private static final String TAG = App.class.getCanonicalName();
    private static App mContext;
    private RoomDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;


        database = RoomDB.getInstance(App.getContext());
        database.mainDao().getAll()
                .subscribeOn(Schedulers.io())
                .subscribe(products -> {
                            if(products.size() != 0)
                                database.mainDao().reset(products);
                            //Read data from CSV fail
                            ArrayList<Product> productsFromCSV = new ArrayList<>(new ReadCSV().readProducts());
                            for (Product product : productsFromCSV) {
                                //Insert text in database
                                database.mainDao().insert(product);
                            }
                            Log.d(TAG, "onCreate: product's table length is " + products.size());
                        },
                        throwable -> Log.e(TAG, "onCreate: Error! Getting all file from product's table didn't work")
                );
    }

    public static App getContext(){
        return mContext;
    }
}
