package com.example.nastoyshiishashlik;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.hitsProductsBar.Product;
import com.example.nastoyshiishashlik.hitsProductsBar.ProductAdapter;
import com.example.nastoyshiishashlik.menuBar.Menu;
import com.example.nastoyshiishashlik.menuBar.MenuAdapter;
import com.example.nastoyshiishashlik.optimization.OptimizationImageBitmap;
import com.example.nastoyshiishashlik.sliderBar.SliderAdapter;
import com.example.nastoyshiishashlik.sliderBar.SliderItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewPhone = findViewById(R.id.main_activity__image_phone);
        BitmapTask bitmapTask = new BitmapTask();
        bitmapTask.execute(R.drawable.phone, 25, 25);

        imageViewBasket = findViewById(R.id.main_activity__image_basket);
        BitmapTask bitmapTask1 = new BitmapTask();
        bitmapTask1.execute(R.drawable.basket, 25, 25);

        imageViewMenu = findViewById(R.id.main_activity__image_menu);
        BitmapTask bitmapTask2 = new BitmapTask();
        bitmapTask2.execute(R.drawable.menu, 25, 25);

        try {
            imageViewPhone.setImageBitmap(bitmapTask.get());
            imageViewBasket.setImageBitmap(bitmapTask1.get());
            imageViewMenu.setImageBitmap(bitmapTask2.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        connectToMenuBar();
        connectToSliderBar();
        connectToHitProductsBar();
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

        try {
            BitmapTask bitmapTask = new BitmapTask();
            bitmapTask.execute(R.drawable.stock, 60, 60);
            menus.add(new Menu(bitmapTask.get(), "акции"));

            BitmapTask bitmapTask1 = new BitmapTask();
            bitmapTask1.execute(R.drawable.sets, 60, 60);
            menus.add(new Menu(bitmapTask1.get(), "сеты"));

            BitmapTask bitmapTask2 = new BitmapTask();
            bitmapTask2.execute(R.drawable.kebab, 60, 60);
            menus.add(new Menu(bitmapTask2.get(), "шашлык"));

            BitmapTask bitmapTask3 = new BitmapTask();
            bitmapTask3.execute(R.drawable.lyulyakebab, 60, 60);
            menus.add(new Menu(bitmapTask3.get(), "люля-кебаб"));

            BitmapTask bitmapTask4 = new BitmapTask();
            bitmapTask4.execute(R.drawable.grilled_fish, 60, 60);
            menus.add(new Menu(bitmapTask4.get(), "рыба на мангале"));

            BitmapTask bitmapTask5 = new BitmapTask();
            bitmapTask5.execute(R.drawable.beverages, 60, 60);
            menus.add(new Menu(bitmapTask5.get(), "напитки"));

            BitmapTask bitmapTask6 = new BitmapTask();
            bitmapTask6.execute(R.drawable.cold_snacks, 60, 60);
            menus.add(new Menu(bitmapTask6.get(), "холодные напитки"));

            BitmapTask bitmapTask7 = new BitmapTask();
            bitmapTask7.execute(R.drawable.garnish, 60, 60);
            menus.add(new Menu(bitmapTask7.get(), "гарниры"));

            BitmapTask bitmapTask8 = new BitmapTask();
            bitmapTask8.execute(R.drawable.grilled_vegetables, 60, 60);
            menus.add(new Menu(bitmapTask8.get(), "овощи на мангале"));

            BitmapTask bitmapTask9 = new BitmapTask();
            bitmapTask9.execute(R.drawable.hot_snack, 60, 60);
            menus.add(new Menu(bitmapTask9.get(), "горячие закуски"));

            BitmapTask bitmapTask10 = new BitmapTask();
            bitmapTask10.execute(R.drawable.khachapuri, 60, 60);
            menus.add(new Menu(bitmapTask10.get(), "хачапури"));

            BitmapTask bitmapTask11 = new BitmapTask();
            bitmapTask11.execute(R.drawable.pickled_meat, 60, 60);
            menus.add(new Menu(bitmapTask11.get(), "маринованное мясо"));

            BitmapTask bitmapTask12 = new BitmapTask();
            bitmapTask12.execute(R.drawable.pita, 60, 60);
            menus.add(new Menu(bitmapTask12.get(), "лаваш"));

            BitmapTask bitmapTask13 = new BitmapTask();
            bitmapTask13.execute(R.drawable.salad, 60, 60);
            menus.add(new Menu(bitmapTask13.get(), "салат"));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return menus;
    }

    /**
     * create and handling slider bar
     */
    private void connectToSliderBar(){
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        sliderAdapter.renewItems(sliderItems());

        SliderView sliderView = findViewById(R.id.main_activity__image_slider);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setScrollTimeInMillis(5000);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private List<SliderItem> sliderItems(){
        List<SliderItem> sliderItems = new ArrayList<>();

        try {
            BitmapTask bitmapTask = new BitmapTask();
            bitmapTask.execute(R.drawable.bavarian_sausages, 400, 250);
            sliderItems.add(new SliderItem(bitmapTask.get()));

            BitmapTask bitmapTask1 = new BitmapTask();
            bitmapTask1.execute(R.drawable.dorado, 400,250);
            sliderItems.add(new SliderItem(bitmapTask1.get()));

            BitmapTask bitmapTask2 = new BitmapTask();
            bitmapTask2.execute(R.drawable.teriyaki_wings, 400,250);
            sliderItems.add(new SliderItem(bitmapTask2.get()));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

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

        try {
            BitmapTask bitmapTask = new BitmapTask();
            bitmapTask.execute(R.drawable.bavarian_sausages, 400, 250);
            products.add(new Product(bitmapTask.get(), "Баварские соски", 100, 85, 200, 170));

            BitmapTask bitmapTask1 = new BitmapTask();
            bitmapTask1.execute(R.drawable.dorado, 400,250);
            products.add(new Product(bitmapTask1.get(), "Дорада", 200, 95, 200, 60));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     *  Asynchronous work with optimization image "bitmap"
     */
    private class BitmapTask extends AsyncTask<Integer, Void, Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), integers[0], options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, integers[1], integers[2]);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(getResources(), integers[0], options);
        }

        private int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}