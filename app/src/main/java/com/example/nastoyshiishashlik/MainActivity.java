package com.example.nastoyshiishashlik;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.dao.roomDao.ReadCSV;
import com.example.nastoyshiishashlik.dao.roomDao.RoomDB;
import com.example.nastoyshiishashlik.model.Dishes;
import com.example.nastoyshiishashlik.model.Product;
import android.view.View;
import android.widget.ScrollView;

import com.example.nastoyshiishashlik.hitsProductsBar.ProductAdapter;
import com.example.nastoyshiishashlik.model.Menu;
import com.example.nastoyshiishashlik.menuBar.MenuAdapter;
import com.example.nastoyshiishashlik.sliderBar.SliderAdapter;
import com.example.nastoyshiishashlik.model.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getCanonicalName();
    //Initialize database
    private ProductDBModel database = new ProductDBModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize database
        //database = new ProductDBModel();


        //Initialize UI
        connectToMenuBar();
        connectToSliderBar();
        connectToHitProductsBar();
        connectToPopUpMenuBar();

        //Initialize processing of clicks on the button
        initializeButtonClick();
    }



    private void initializeButtonClick(){
        ImageView phone = findViewById(R.id.main_activity__image_phone);
        phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.main_activity__image_phone: {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+380672515945"));
                startActivity(intent);
                break;
            }


            //.... etc
        }
    }

    /**
     * create and handling menu bar
     */
    private void connectToMenuBar(){
        //Initialize list with object's menu in other thread
        List<Menu> menuList = new ArrayList<>();
        generationMenuList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menus -> {
                    menuList.addAll(menus);
                    Log.d(TAG, "connectToMenuBar: create menu list is successful");
                },throwable -> Log.e(TAG, "connectToMenuBar: create menu list is ot successful"));

        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_menu_icons);
        MenuAdapter menuAdapter = new MenuAdapter(menuList, R.id.main_activity__rv_menu_icons);
        recyclerView.setAdapter(menuAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private Single<List<Menu>> generationMenuList(){
        return Single.create(emitter -> {
            List<Menu> menus = new ArrayList<>();

            menus.add(new Menu(R.drawable.stock, "акции", Dishes.STOCK));
            menus.add(new Menu(R.drawable.sets, "сеты", Dishes.SETS));
            menus.add(new Menu(R.drawable.kebab, "шашлык", Dishes.KEBAB));
            menus.add(new Menu(R.drawable.lyulyakebab, "люля-кебаб", Dishes.LYULYAKEBAB));
            menus.add(new Menu(R.drawable.grilled_fish, "рыба на\nмангале", Dishes.GRILLED_FISH));
            menus.add(new Menu(R.drawable.beverages, "напитки", Dishes.BEVERAGES));
            menus.add(new Menu(R.drawable.cold_snacks, "холодные\nзакуски", Dishes.COLD_SNACKS));
            menus.add(new Menu(R.drawable.garnish, "гарниры", Dishes.GARNISH));
            menus.add(new Menu(R.drawable.grilled_vegetables, "овощи на\nмангале", Dishes.GRILLED_VEGETABLES));
            menus.add(new Menu(R.drawable.hot_snack, "горячие\nзакуски", Dishes.HOT_SNACK));
            menus.add(new Menu(R.drawable.khachapuri, "хачапури", Dishes.KHACHAPURI));
            menus.add(new Menu(R.drawable.pickled_meat, "маринованное\nмясо", Dishes.PICKLED_MEAT));
            menus.add(new Menu(R.drawable.pita, "лаваш", Dishes.PITA));
            menus.add(new Menu(R.drawable.salad, "салаты", Dishes.SALAD));
            menus.add(new Menu(R.drawable.sauces, "соусы", Dishes.SAUCES));
            menus.add(new Menu(R.drawable.first_meal, "первые блюда", Dishes.FIRST_MEAL));

            emitter.onSuccess(menus);
        });
    }

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

        sliderItems.add(new SliderItem(R.drawable.bavarian_sausages));
        sliderItems.add(new SliderItem(R.drawable.dorado));
        sliderItems.add(new SliderItem(R.drawable.teriyaki_wings));

        return sliderItems;
    }

    /**
     * create and handling hit products bar
     */
    private void connectToHitProductsBar(){
        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_hits);
        ProductAdapter productAdapter = new ProductAdapter(generationHitProductsList());
        recyclerView.setAdapter(productAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
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

    /**
     * create and handling menu bar
     */
    private void connectToPopUpMenuBar(){
        //Initialize list with object's menu in other thread
        List<Menu> menuList = new ArrayList<>();
        generationMenuList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menus -> {
                    menuList.addAll(menus);
                    Log.d(TAG, "connectToMenuBar: create menu list is successful");
                },throwable -> Log.e(TAG, "connectToMenuBar: create menu list is ot successful"));

        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_pop_up_menu_icons);
        MenuAdapter menuAdapter = new MenuAdapter(menuList, R.id.main_activity__rv_pop_up_menu_icons);
        recyclerView.setAdapter(menuAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    public void onClickButtonMenu(View view) {
        ScrollView scrollView = findViewById(R.id.pop_up_menu__SV);
        scrollView.setVisibility(View.VISIBLE);
    }

    public void onClickExitMenu(View view) {
        ScrollView scrollView = findViewById(R.id.pop_up_menu__SV);
        scrollView.setVisibility(View.INVISIBLE);
    }

}