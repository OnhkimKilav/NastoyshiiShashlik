package com.example.nastoyshiishashlik;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.model.Product;
import com.example.nastoyshiishashlik.hitsProductsBar.ProductAdapter;
import com.example.nastoyshiishashlik.model.Menu;
import com.example.nastoyshiishashlik.menuBar.MenuAdapter;
import com.example.nastoyshiishashlik.optimization.OptimizationImageBitmap;
import com.example.nastoyshiishashlik.sliderBar.SliderAdapter;
import com.example.nastoyshiishashlik.model.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private ImageView imageViewPhone;
    private ImageView imageViewBasket;
    private ImageView imageViewMenu;
    private ImageView imageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTopButton();
        connectToMenuBar();
        connectToSliderBar();
        connectToHitProductsBar();
    }

    private void createTopButton(){
        imageViewMenu = findViewById(R.id.main_activity__image_menu);
        imageViewBasket = findViewById(R.id.main_activity__image_basket);
        imageViewPhone = findViewById(R.id.main_activity__image_phone);
        imageLogo = findViewById(R.id.main_activity_image_logo);

        OptimizationImageBitmap optimizationImageBitmapPhone = new OptimizationImageBitmap();
        optimizationImageBitmapPhone.execute(R.drawable.phone, 25, 25);
        OptimizationImageBitmap optimizationImageBitmapBasket = new OptimizationImageBitmap();
        optimizationImageBitmapBasket.execute(R.drawable.basket, 25, 25);
        OptimizationImageBitmap optimizationImageBitmapMenu = new OptimizationImageBitmap();
        optimizationImageBitmapMenu.execute(R.drawable.menu, 25, 25);
        OptimizationImageBitmap optimizationImageBitmapLogo = new OptimizationImageBitmap();
        optimizationImageBitmapLogo.execute(R.drawable.logo, 200, 100);

        try {
            imageViewPhone.setImageBitmap(optimizationImageBitmapPhone.get());
            imageViewBasket.setImageBitmap(optimizationImageBitmapBasket.get());
            imageViewMenu.setImageBitmap(optimizationImageBitmapMenu.get());
            imageLogo.setImageBitmap(optimizationImageBitmapLogo.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * create and handling menu bar
     */
    private void connectToMenuBar(){
        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_menu_icons);
        MenuAdapter menuAdapter = new MenuAdapter(generationMenuList());
        recyclerView.setAdapter(menuAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private List<Menu> generationMenuList(){
        List<Menu> menus = new ArrayList<>();

        menus.add(new Menu(R.drawable.stock, "акции"));
        menus.add(new Menu(R.drawable.sets, "сеты"));
        menus.add(new Menu(R.drawable.kebab, "шашлык"));
        menus.add(new Menu(R.drawable.lyulyakebab, "люля-кебаб"));
        menus.add(new Menu(R.drawable.grilled_fish, "рыба на мангале"));
        menus.add(new Menu(R.drawable.beverages, "напитки"));
        menus.add(new Menu(R.drawable.cold_snacks, "холодные напитки"));
        menus.add(new Menu(R.drawable.garnish, "гарниры"));
        menus.add(new Menu(R.drawable.grilled_vegetables, "овощи на мангале"));
        menus.add(new Menu(R.drawable.hot_snack, "горячие закуски"));
        menus.add(new Menu(R.drawable.khachapuri, "хачапури"));
        menus.add(new Menu(R.drawable.pickled_meat, "маринованное мясо"));
        menus.add(new Menu(R.drawable.pita, "лаваш"));
        menus.add(new Menu(R.drawable.salad, "салат"));

        return menus;
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

    private List<Product> generationHitProductsList(){
        List<Product> products = new ArrayList<>();

        products.add(new Product(R.drawable.bavarian_sausages, "Баварские соски", 100, 85, 200, 170));
        products.add(new Product(R.drawable.dorado, "Дорада", 200, 95, 200, 60));

        return products;
    }
}