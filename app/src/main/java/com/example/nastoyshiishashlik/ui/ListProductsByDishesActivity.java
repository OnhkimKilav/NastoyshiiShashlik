package com.example.nastoyshiishashlik.ui;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.fragments.BasketFragment;
import com.example.nastoyshiishashlik.fragments.ProductFragment;
import com.example.nastoyshiishashlik.models.ProductModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListProductsByDishesActivity extends BaseActivity implements BasketFragment.Communicator{
    private final String TAG = ListProductsByDishesActivity.class.getCanonicalName();

    @BindView(R.id.list_product_activity__text_view_hits)
    TextView tvDishesName;
    @BindView(R.id.list_product_activity__ns)
    NestedScrollView scrollView;
    @BindView(R.id.toUp)
    ImageView buttonUp;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public int getViewId() {
        return R.layout.activity_list_products_by_dishes;
    }

    @Override
    public void onCreateView() {
        Intent intent = getIntent();
        String fName = intent.getStringExtra("product");
        generationProductList(fName);
        checkButtonUpVisibility();
    }

    @OnClick(R.id.toUp)
    public void onClickToUp(){
        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    private void checkButtonUpVisibility(){
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY(); //for verticalScrollView
                if (scrollY <= 300)
                    buttonUp.setVisibility(View.INVISIBLE);
                    //button visible
                else
                    buttonUp.setVisibility(View.VISIBLE);
                //button invisible
            }
        });
    }

    private void generationProductList(String fName) {
        ArrayList<ProductModel> productsList = new ArrayList<>();

        getDatabase().getByDishes(fName)
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
        bundle.putString("orientation", "vertical");

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

    @Override
    protected void onStop() {
        //fragmentTransaction.remove(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.conteiner)));
        super.onStop();
        Log.d(TAG, "onStop: method was call");
    }
}
