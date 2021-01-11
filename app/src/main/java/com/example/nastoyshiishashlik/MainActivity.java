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

import com.example.nastoyshiishashlik.dao.ReadCSV;
import com.example.nastoyshiishashlik.dao.roomDao.RoomDB;
import com.example.nastoyshiishashlik.model.Dishes;
import com.example.nastoyshiishashlik.model.Product;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

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

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private RoomDB database;



    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Single.fromCallable(() -> insertProductDB())
                .subscribeOn(Schedulers.io());*/
        insertProductDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Toast.makeText(App.getContext(),TAG + " insert product into db from csv file is completed", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    Toast.makeText(App.getContext(),TAG + " Failed work with DB", Toast.LENGTH_SHORT).show();
                });

        connectToPopUpMenuBar()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "onCreate: initialize and create pop-up menu bar on the UI")
                , throwable -> Log.e(TAG, "onCreate: Failed to create pop-up menu bar"));

        connectToHitProductsBar()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "onCreate: initialize and create hit products bar on the UI")
                        , throwable -> Log.e(TAG, "onCreate: Failed to create hit products bar"));

        connectToMenuBar()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "onCreate: initialize and create menu bar on the UI")
                        , throwable -> Log.e(TAG, "onCreate: Failed to create menu bar"));

        connectToSliderBar()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "onCreate: initialize and create slider bar on the UI")
                        , throwable -> Log.e(TAG, "onCreate: Failed to create slider bar"));

        //Initialize database
        //insertProductDB();

        //Initialize UI
        //connectToMenuBar();
        //connectToSliderBar();
        //connectToHitProductsBar();
        //connectToPopUpMenuBar();

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
    private Completable connectToMenuBar(){
        return Completable.create(emitter -> {
            RecyclerView recyclerView = findViewById(R.id.main_activity__rv_menu_icons);
            MenuAdapter menuAdapter = new MenuAdapter(generationMenuList(), R.id.main_activity__rv_menu_icons);
            recyclerView.setAdapter(menuAdapter);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(mLayoutManager);
        });
    }

    private List<Menu> generationMenuList(){
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

        return menus;
    }

    /**
     * create and handling slider bar
     */
    private Completable connectToSliderBar(){
        return Completable.create(emitter -> {
            SliderAdapter sliderAdapter = new SliderAdapter(sliderItems());

            SliderView sliderView = findViewById(R.id.main_activity__image_slider);
            sliderView.setSliderAdapter(sliderAdapter);
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
            sliderView.setScrollTimeInMillis(5000);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.startAutoCycle();
        });
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
    private Completable connectToHitProductsBar(){
        return Completable.create(emitter -> {
            RecyclerView recyclerView = findViewById(R.id.main_activity__rv_hits);
            ProductAdapter productAdapter = new ProductAdapter(generationHitProductsList());
            recyclerView.setAdapter(productAdapter);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
        });
    }

    private List<Product> generationHitProductsList(){
        //Read all product from DB where value "hit" equals "true"
        ArrayList<Product> products = (ArrayList<Product>) database.mainDao().getByHit(1);

        return products;
    }

    public void onClickButtonMenu(View view) {
        ScrollView scrollView = findViewById(R.id.pop_up_menu__SV);
        scrollView.setVisibility(View.VISIBLE);
    }

    /**
     * create and handling menu bar
     */
    private Completable connectToPopUpMenuBar(){
        return Completable.create(subscriber -> {
            RecyclerView recyclerView = findViewById(R.id.main_activity__rv_pop_up_menu_icons);
            MenuAdapter menuAdapter = new MenuAdapter(generationMenuList(), R.id.main_activity__rv_pop_up_menu_icons);
            recyclerView.setAdapter(menuAdapter);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
        });
    }

    public void onClickExitMenu(View view) {
        ScrollView scrollView = findViewById(R.id.pop_up_menu__SV);
        scrollView.setVisibility(View.INVISIBLE);
    }

    private Completable insertProductDB(){
        return Completable.create(subscriber -> {
            //Initialize database
            database = RoomDB.getInstance(this);

            //database.mainDao().reset(database.mainDao().getAll());
            //Get size products from table
            int sizeProduct = database.mainDao().getAll().size();

            //Read data from CSV fail
            if (sizeProduct == 0) {
                ArrayList<Product> products = new ArrayList<>(new ReadCSV().readProducts());
                for (Product product : products) {
                    //Insert text in database
                    database.mainDao().insert(product);
                }
            }
            subscriber.onComplete();
        });
    }

}