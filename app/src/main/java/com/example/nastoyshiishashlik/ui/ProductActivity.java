package com.example.nastoyshiishashlik.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.controller.MenuController;
import com.example.nastoyshiishashlik.controller.ProductController;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.service.ProductAdapter;
import com.example.nastoyshiishashlik.model.Product;
import com.example.nastoyshiishashlik.service.OptimizationBitmap;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductActivity extends AppCompatActivity {
    private static final String TAG = ProductActivity.class.getCanonicalName();
    private Product product = new Product();
    private MenuController menuController = new MenuController();
    private ProductController productController = new ProductController();
    private ProductDBModel database = new ProductDBModel();
    private TextView tvName;
    private ImageView ivPoster;
    private TextView tvText;
    private TextView tvWeightPrice;
    private TextView tvMinWeight;
    private TextView tvTotalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent i = getIntent();
        product = (Product) i.getSerializableExtra(ProductAdapter.class.getCanonicalName());
        Log.d(TAG, "onCreate: get from MenuAdapter class products list, when have size: " + product.toString());

        tvName = findViewById(R.id.product_activity__tv_name_product);
        ivPoster = findViewById(R.id.product_activity__iv_poster);
        tvText = findViewById(R.id.product_activity__tv_text);
        tvWeightPrice = findViewById(R.id.product_activity__tv_weight_price);
        tvMinWeight = findViewById(R.id.product_activity__tv_weight);
        tvTotalPrice = findViewById(R.id.product_activity__tv_total_price);

        tvName.setText(product.getName());
        OptimizationBitmap.optimizationBitmap(product.getPoster(), 450, 250)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    ivPoster.setImageBitmap(bitmap);
                    Log.d(TAG, "onCreate: rendering bitmap - "+bitmap.getGenerationId()+" is corect");
                }, throwable -> Log.e(TAG, "onCreate: rendering bitmap isn't corect"));
        tvText.setText(product.getDescription());
        tvWeightPrice.setText(String.format(
                App.getContext().getResources().getString(R.string.main_activity__weight_price),
                product.getPrice(), product.getWeight()
        ));
        tvMinWeight.setText(String.format(
                App.getContext().getResources().getString(R.string.main_activity__min_weight),
                product.getMinWeightForOrder()
        ));
        tvTotalPrice.setText(String.format(
                App.getContext().getResources().getString(R.string.main_activity__total_price),
                product.getFinalPrice()
        ));

        connectSentencesMenu();
    }

    /**
     * create connect to sentences products menu
     */
    private void connectSentencesMenu(){
        RecyclerView recyclerView = findViewById(R.id.product_activity__rv_sentences);
        productController.createSentences(generationSentencesList(), recyclerView, R.layout.sentences);
    }
    /**
     * Get list products for sentences
     */
    //TODO
    private List<Product> generationSentencesList(){
        List<Product> products1 = new ArrayList<>();
        if(product.getSentences() != "null") {
            String[] res = product.getSentences().split("[,]", 0);
            for (String s : res) {
                database.getById(Integer.parseInt(s))
                        .subscribeOn(Schedulers.io())
                        .subscribe(product1 -> {
                            products1.add(product1);
                        });
            }
        }
        return products1;
    }
}