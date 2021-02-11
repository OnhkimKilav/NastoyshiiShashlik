package com.example.nastoyshiishashlik.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.controller.MenuController;
//import com.example.nastoyshiishashlik.controller.ProductController;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.fragments.BasketFragment;
import com.example.nastoyshiishashlik.fragments.ProductFragment;
import com.example.nastoyshiishashlik.fragments.SingleProductFragment;
import com.example.nastoyshiishashlik.models.ProductModel;
import com.example.nastoyshiishashlik.service.ProductAdapter;
import com.example.nastoyshiishashlik.utils.OptimizationBitmap;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductActivity extends BaseActivity implements BasketFragment.Communicator{
    private static final String TAG = ProductActivity.class.getCanonicalName();
    private ProductModel productModel = new ProductModel();
    private ProductDBModel database = new ProductDBModel();
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<ProductModel> listSentencesProducts = new ArrayList<>();

    @Override
    public int getViewId() {
        return R.layout.activity_product;
    }


    @Override
    public void onCreateView() {
        productModel = (ProductModel) getIntent().getSerializableExtra("product");
        generationSentencesList();
        Log.d(TAG, "onCreate: get from MenuAdapter class products list, when have size: " + productModel.toString());

        initFragmentProduct(productModel);
        initSentencesProductList();
    }

    private void initFragmentProduct(ProductModel product){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);

        SingleProductFragment singleProductFragment = new SingleProductFragment();
        singleProductFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.conteiner, singleProductFragment);
        fragmentTransaction.commit();
    }

    private void initSentencesProductList(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("productList", listSentencesProducts);

        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.conteiner_sentences, productFragment);
        fragmentTransaction.commit();
    }

    //Get list products for sentences
    private void generationSentencesList(){
        if(!productModel.getSentences().equals("null")) {
            String[] res = productModel.getSentences().split("[,]", 0);
            for (String s : res) {
                database.getById(Integer.parseInt(s))
                        .subscribeOn(Schedulers.io())
                        .subscribe(product1 -> {
                            listSentencesProducts.add(product1);
                        });
            }
        }
    }

    @Override
    public void updateViewBasket() {
        BasketFragment basketFragment = (BasketFragment) fragmentManager.findFragmentById(R.id.product_activity__frag_top_button)
                .getChildFragmentManager().findFragmentById(R.id.fragment_basket);
        basketFragment.setupBadge();
    }
}
