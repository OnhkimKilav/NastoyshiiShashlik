package com.example.nastoyshiishashlik.ui;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.fragments.BasketFragment;
import com.example.nastoyshiishashlik.fragments.ProductFragment;
import com.example.nastoyshiishashlik.models.ProductModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements BasketFragment.Communicator {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private final ArrayList<ProductModel> listProducts = new ArrayList<>();

    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.main_activity_ns_main)
    NestedScrollView scrollView;
    @BindView(R.id.toUp)
    ImageView buttonUp;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateView() {

        setSliderLayout();
        generationProductList();

        initFragmentProductList();
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

    private void setSliderLayout(){
        slider.addSlider(new DefaultSliderView(this).image(R.drawable.baner1));
        slider.addSlider(new DefaultSliderView(this).image(R.drawable.baner2));
        slider.addSlider(new DefaultSliderView(this).image(R.drawable.baner3));
    }

    private void initFragmentProductList(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("productList", listProducts);
        bundle.putString("orientation", "vertical");

        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.conteiner, productFragment);
        fragmentTransaction.commit();
    }



    @SuppressLint("CheckResult")
    private void generationProductList(){
        //Read all product from DB where value "hit" equals "true"
        getDatabase().getByHit(1)
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

    @Override
    protected void onStop() {
        slider.stopAutoCycle();
        //fragmentTransaction.remove(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.conteiner)));
        super.onStop();
        Log.d(TAG, "onStop: method was call");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: method was called");
    }
}