package com.example.nastoyshiishashlik.dao.roomDao;

import android.annotation.SuppressLint;

import androidx.lifecycle.AndroidViewModel;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.models.ProductModel;

import java.util.List;
import io.reactivex.Single;

public class ProductDBModel extends AndroidViewModel {
    private static final String TAG = ProductDBModel.class.getCanonicalName();
    private RoomDB database;

    public static class ProductDBModelHolder {
        public static final ProductDBModel HOLDER_INSTANCE = new ProductDBModel();
    }

    public static ProductDBModel getInstance() {
        return ProductDBModelHolder.HOLDER_INSTANCE;
    }

    @SuppressLint("CheckResult")
    public ProductDBModel() {
        super(App.getContext());

        database = RoomDB.getInstance(App.getContext());
    }

    public Single<List<ProductModel>> getAll(){
        return database.mainDao().getAll();
    }

    public Single<List<ProductModel>> getByHit(int hit) {
        return database.mainDao().getByHit(hit);
    }

    public Single<List<ProductModel>> getByDishes(String sDishes){return database.mainDao().getByDishes(sDishes);}

    public Single<ProductModel> getById(int iId){
        return database.mainDao().getById(iId);
    }
}
