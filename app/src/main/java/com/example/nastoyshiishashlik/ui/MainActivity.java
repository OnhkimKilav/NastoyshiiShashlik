package com.example.nastoyshiishashlik.ui;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.fragments.BasketFragment;
import com.example.nastoyshiishashlik.fragments.ProductFragment;
import com.example.nastoyshiishashlik.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements BasketFragment.Communicator {
    private static final String TAG = MainActivity.class.getCanonicalName();
    //Initialize database
    private ProductDBModel database;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<ProductModel> listProducts = new ArrayList<>();

    @Override
    public int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateView() {
        database = new ProductDBModel();
        generationProductList();

        initFragmentProductList();
    }

    private void initFragmentProductList(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("productList", listProducts);

        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.conteiner, productFragment);
        fragmentTransaction.commit();
    }

    @SuppressLint("CheckResult")
    private void generationProductList(){
        //Read all product from DB where value "hit" equals "true"
        database.getByHit(1)
                .subscribeOn(Schedulers.io())
                .subscribe(products -> {
                    listProducts.addAll(products);
                    Log.d(TAG, "generationHitProductsList: get products from db is successful");
                }, throwable -> {
                    Log.e(TAG, "generationHitProductsList: get products from db not successful");
                });
    }

    @Override
    public void updateViewBasket() {
        BasketFragment basketFragment = (BasketFragment) fragmentManager.findFragmentById(R.id.main_activity__frag_top_button)
                .getChildFragmentManager().findFragmentById(R.id.fragment_basket);
        basketFragment.setupBadge();
    }
}