package com.example.nastoyshiishashlik.controller;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.service.ProductAdapter;
import com.example.nastoyshiishashlik.model.Product;
import com.example.nastoyshiishashlik.service.SentencesAdapter;

import java.util.List;

public class ProductController {
    private SentencesAdapter sentencesAdapter;
    private ProductAdapter productAdapter;

    public void createSentences(List<Product> productList, RecyclerView recyclerView, int idIForInflate){
        productAdapter = new ProductAdapter(productList, idIForInflate);
        recyclerView.setAdapter(productAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(App.getContext());
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    /**
     *
     * @param productList
     * @param recyclerView
     * @param orientation RecyclerView.VERTICAL or RecyclerView.HORIZONTAL
     */
    public void createProductBar(List<Product> productList, RecyclerView recyclerView, int orientation, int idIForInflate){
        productAdapter = new ProductAdapter(productList, idIForInflate  );
        recyclerView.setAdapter(productAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(App.getContext());
        mLayoutManager.setOrientation(orientation);
        recyclerView.setLayoutManager(mLayoutManager);
    }
}
