package com.example.nastoyshiishashlik.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.controller.PopupController;
import com.example.nastoyshiishashlik.controller.ProductController;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.model.Product;
import android.view.View;

import com.example.nastoyshiishashlik.service.MenuAdapter;
import com.example.nastoyshiishashlik.service.SliderAdapter;
import com.example.nastoyshiishashlik.model.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private ProductController productController = new ProductController();
    //Initialize database
    private ProductDBModel database;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(this);
        database = new ProductDBModel();


        //Initialize UI
        //connectToMenuBar();
        connectToSliderBar();
        connectToHitProductsBar();

    }


    /**
     * create and handling menu bar
     */
    /*private void connectToMenuBar(){
        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_menu_icons);
        MenuAdapter menuAdapter = new MenuAdapter(R.id.main_activity__rv_menu_icons);
        recyclerView.setAdapter(menuAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }*/


    /**
     * create and handling slider bar
     */
    private void connectToSliderBar(){
        SliderAdapter sliderAdapter = new SliderAdapter(sliderItems());

        SliderView sliderView = findViewById(R.id.main_activity__image_slider);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setScrollTimeInMillis(5000);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private List<SliderItem> sliderItems(){
        List<SliderItem> sliderItems = new ArrayList<>();

        sliderItems.add(new SliderItem(R.drawable.baner1));
        sliderItems.add(new SliderItem(R.drawable.baner2));
        sliderItems.add(new SliderItem(R.drawable.baner3));

        return sliderItems;
    }

    /**
     * create and handling hit products bar
     */
    private void connectToHitProductsBar(){
        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_hits);
        productController.createProductBar(generationHitProductsList(), recyclerView, RecyclerView.VERTICAL,
                R.layout.activity_main__hits_products);
    }

    @SuppressLint("CheckResult")
    private ArrayList<Product> generationHitProductsList(){
        //Read all product from DB where value "hit" equals "true"
        List<Product> productsList = new ArrayList<>();
        database.getByHit(1)
                .subscribeOn(Schedulers.io())
                .subscribe(products -> {
                    productsList.addAll(products);
                    Log.d(TAG, "generationHitProductsList: get products from db is successful");
                }, throwable -> {
                    Log.e(TAG, "generationHitProductsList: get products from db not successful");
                });

        return (ArrayList<Product>) productsList;
    }

}