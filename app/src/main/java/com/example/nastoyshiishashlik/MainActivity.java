package com.example.nastoyshiishashlik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        menus.add(new Menu(R.drawable.stock, "акции"));
        menus.add(new Menu(R.drawable.sets, "сеты"));
        menus.add(new Menu(R.drawable.kebab, "шашлык"));
        menus.add(new Menu(R.drawable.lyulyakebab, "люля-кебаб"));
        menus.add(new Menu(R.drawable.grilled_fish, "рыба на мангале"));
        menus.add(new Menu(R.drawable.beverages, "напитки"));
        menus.add(new Menu(R.drawable.cold_snacks, "холодные закуски"));
        menus.add(new Menu(R.drawable.garnish, "гарниры"));
        menus.add(new Menu(R.drawable.grilled_vegetables, "овощи на мангале"));
        menus.add(new Menu(R.drawable.hot_snack, "горячие закуски"));
        menus.add(new Menu(R.drawable.khachapuri, "хачапури"));
        menus.add(new Menu(R.drawable.pickled_meat, "маринованое мясо"));
        menus.add(new Menu(R.drawable.pita, "лаваш"));
        menus.add(new Menu(R.drawable.salad, "салат"));

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
        sliderItems.add(new SliderItem(OptimizationImageBitmap.decodeSampledBitmapFromResource(getResources(), R.drawable.bavarian_sausages, 400, 250)));
        sliderItems.add(new SliderItem(OptimizationImageBitmap.decodeSampledBitmapFromResource(getResources(), R.drawable.dorado, 400, 250)));
        sliderItems.add(new SliderItem(OptimizationImageBitmap.decodeSampledBitmapFromResource(
                getResources(),
                R.drawable.teriyaki_wings,
                400,
                250)));

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
        products.add(new Product(
                OptimizationImageBitmap.decodeSampledBitmapFromResource(
                        getResources(),
                        R.drawable.bavarian_sausages,
                        400,
                        250),
                "Баварские сосиски",
                100,
                85,
                200,
                170));

        products.add(new Product(OptimizationImageBitmap.decodeSampledBitmapFromResource(
                getResources(),
                R.drawable.dorado,
                400,
                250),
                "Дорада",
                200,
                95,
                200,
                60));

        return products;
    }

    /**
     *  Asynchronous work with optimization image "bitmap"
     */
   /* private class BitmapAsyncTask extends AsyncTask<Integer, Void, Bitmap>{

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

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmap.set
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
    }*/
}