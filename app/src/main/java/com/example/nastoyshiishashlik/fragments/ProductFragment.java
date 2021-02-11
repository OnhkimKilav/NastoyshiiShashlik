package com.example.nastoyshiishashlik.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.adapters.ProductRecyclerAdapter;
import com.example.nastoyshiishashlik.entities.CartEntity;
import com.example.nastoyshiishashlik.models.CartHelper;
import com.example.nastoyshiishashlik.models.ProductEntityModel;
import com.example.nastoyshiishashlik.models.ProductModel;
import com.example.nastoyshiishashlik.ui.MainActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProductFragment extends BaseFragment implements ProductRecyclerAdapter.OnItemClickListener{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ProductRecyclerAdapter productRecyclerAdapter;
    private List<ProductModel> listProducts;
    private BasketFragment.Communicator communicator;

    @Override
    public int getViewId() {
        return R.layout.fragment_product;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        communicator = (BasketFragment.Communicator) activity;
    }

    @Override
    public void onViewCreated(View view) {
        listProducts = (List<ProductModel>) this.getArguments().getSerializable("productList");

        productRecyclerAdapter = new ProductRecyclerAdapter(context,  listProducts);
        productRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setAdapter(productRecyclerAdapter);
    }

    @Override
    public void onItemClick(ProductModel productModel) {
        ProductEntityModel product = new ProductEntityModel();
        product.setId(productModel.getID());
        product.setName(productModel.getName());
        product.setPrice(productModel.getPrice());
        product.setImage(productModel.getPoster());
        product.setFinalPrice(BigDecimal.valueOf(productModel.getFinalPrice()));
        product.setQuantity(productModel.getQuantity());
        product.setMinWeightForOrder(productModel.getMinWeightForOrder());
        product.setWeight(productModel.getWeight());

        CartEntity cart = CartHelper.getCart();
        cart.add(product, productModel.getQuantity());

        Toast toast = Toast.makeText(App.getContext(),
                product.getName()+" было добавленно в корзину", Toast.LENGTH_SHORT);
        toast.show();

        communicator.updateViewBasket();
    }

    @Override
    public void onItemPlusClicked(int position, ProductModel productModel) {
        int quantity, price, finalPrice;

        quantity = productModel.getQuantity();
        quantity++;
        productModel.setQuantity(quantity);

        price = productModel.getPrice();
        finalPrice = price * quantity;
        productModel.setFinalPrice(finalPrice);

        productRecyclerAdapter.updateItem(position, productModel);
    }

    @Override
    public void onItemMinusClicked(int position, ProductModel productModel) {
        int minQuantity = (int) (productModel.getMinWeightForOrder() / productModel.getWeight());

        if(productModel.getQuantity() > minQuantity) {
            int quantity, price, finalPrice;

            quantity = productModel.getQuantity();
            quantity--;
            productModel.setQuantity(quantity);

            price = productModel.getPrice();
            finalPrice = price * quantity;
            productModel.setFinalPrice(finalPrice);

            productRecyclerAdapter.updateItem(position, productModel);
        }
    }
}