package com.example.nastoyshiishashlik.ui;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.fragments.BasketFragment;
import com.example.nastoyshiishashlik.fragments.ProductFragment;
import com.example.nastoyshiishashlik.service.MenuAdapter;
import com.example.nastoyshiishashlik.models.ProductModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListProductsByDishesActivity extends BaseActivity implements BasketFragment.Communicator{
    private final String TAG = ListProductsByDishesActivity.class.getCanonicalName();

    private TextView tvDishesName;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ProductDBModel database = new ProductDBModel();

    @Override
    public int getViewId() {
        return R.layout.activity_list_products_by_dishes;
    }

    @Override
    public void onCreateView() {
        Intent intent = getIntent();
        String fName = intent.getStringExtra("product");
        generationProductList(fName);

        tvDishesName = findViewById(R.id.list_product_activity__text_view_hits);
    }

    private void generationProductList(String fName) {
        ArrayList<ProductModel> productsList = new ArrayList<>();
        database.getByDishes(fName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products -> {
                    tvDishesName.setText(products.get(0).getDishesName());

                    productsList.addAll(products);
                    initFragmentProductList(productsList);
                }, throwable -> Log.e(TAG, "onClick: get products from db by dishes is not successful"));
    }

    private void initFragmentProductList(ArrayList<ProductModel> productList){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("productList", productList);

        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.conteiner, productFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void updateViewBasket() {
        BasketFragment basketFragment = (BasketFragment) fragmentManager.findFragmentById(R.id.list_product_activity__header_button)
                .getChildFragmentManager().findFragmentById(R.id.fragment_basket);
        basketFragment.setupBadge();
    }
}
