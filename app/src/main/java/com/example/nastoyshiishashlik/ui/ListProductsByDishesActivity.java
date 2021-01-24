package com.example.nastoyshiishashlik.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.service.ProductAdapter;
import com.example.nastoyshiishashlik.service.MenuAdapter;
import com.example.nastoyshiishashlik.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListProductsByDishesActivity extends AppCompatActivity {
    private final String TAG = ListProductsByDishesActivity.class.getCanonicalName();

    private List<Product> productListByDishes = new ArrayList<>();
    private TextView tvDishesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products_by_dishes);

        Intent i = getIntent();
        productListByDishes = (List<Product>) i.getSerializableExtra(MenuAdapter.class.getCanonicalName());
        Log.d(TAG, "onCreate: get from MenuAdapter class products list, when have size: " + productListByDishes.size());

        tvDishesName = findViewById(R.id.list_product_activity__text_view_hits);
        if(productListByDishes.size() != 0)
            tvDishesName.setText(productListByDishes.get(0).getDishesName());

        //connectToMenuBar();
        connectToProductsBar();
    }

    /**
     * create and handling list products
     */
    private void connectToProductsBar(){
        RecyclerView recyclerView = findViewById(R.id.list_product_activity__rv_hits);
        ProductAdapter productAdapter = new ProductAdapter(productListByDishes, R.layout.activity_main__hits_products);
        recyclerView.setAdapter(productAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }
}