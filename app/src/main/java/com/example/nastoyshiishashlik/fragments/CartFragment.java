package com.example.nastoyshiishashlik.fragments;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.adapters.CartRecyclerAdapter;
import com.example.nastoyshiishashlik.models.CartHelper;
import com.example.nastoyshiishashlik.models.CartItemsEntityModel;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;


public class CartFragment extends BaseFragment implements CartRecyclerAdapter.OnItemClickListener, BasketFragment.Communicator {
    private final static String LOG = CartFragment.class.getCanonicalName();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.cart__empty_basket)
    LinearLayout linLayEmptyBasket;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.delivery)
    TextView delivery;
    @BindView(R.id.cart__list_products)
    RelativeLayout relativeLayout;
    @BindView(R.id.cart__delivery)
    TextView buttonDelivery;
    @BindView(R.id.cart__pickup)
    TextView buttonPickup;

    private CartRecyclerAdapter productRecyclerAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public int getViewId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void onViewCreated(View view) {
        onUpdateList();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        entityCheckMethods();
    }

    public void entityCheckMethods(){
        checkEmptyBasket();
        setTotalPrice();
        setDelivery();
        setHeight();
    }

    private void checkEmptyBasket(){
        if(CartHelper.getCart().getProducts().size() > 0)
            linLayEmptyBasket.setVisibility(View.GONE);
        else linLayEmptyBasket.setVisibility(View.VISIBLE);
    }

    private void setHeight(){
        if(CartHelper.getCartItems().size() >= 2) {
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 730);
            recyclerView.setLayoutParams(lp);
        }else{
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(lp);
        }
    }

    private void setDelivery(){
        delivery.setText(String.format(context.getString(R.string.price_cart_format),
                (CartHelper.getCart().getDelivery())));
    }

    private void setTotalPrice(){
        if(CartHelper.getCartItems().size() == 0)
            this.totalPrice.setText(String.format(context.getString(R.string.price_cart_format), 0));
        else{
            BigDecimal totalPrice = CartHelper.getCart().getTotalPrice();
            this.totalPrice.setText(String.format(context.getString(R.string.total_price_cart_format),totalPrice));
        }
    }

    @Override
    public void onItemClick(CartItemsEntityModel cartItemsEntityModel) {
        // open details of product
    }

    @Override
    public void onItemPlusClicked(int position, CartItemsEntityModel cartItemsEntityModel) {
        int quantity = cartItemsEntityModel.getQuantity();
        BigDecimal finalPrice = cartItemsEntityModel.getProduct().getFinalPrice();
        int price = cartItemsEntityModel.getProduct().getPrice();

        CartItemsEntityModel cartModel = new CartItemsEntityModel();
        cartModel.setProduct(cartItemsEntityModel.getProduct());
        quantity++;
        cartModel.setQuantity(quantity);

        finalPrice = BigDecimal.valueOf(price * quantity);
        cartModel.getProduct().setFinalPrice(finalPrice);

        productRecyclerAdapter.updateItem(position, cartModel);
        setTotalPrice();
        setDelivery();
    }

    @Override
    public void onItemMinusClicked(int position, CartItemsEntityModel cartItemsEntityModel) {
        int minQuantity = (int) (cartItemsEntityModel.getProduct().getMinWeightForOrder() / cartItemsEntityModel.getProduct().getWeight());
        int quantity = cartItemsEntityModel.getQuantity();
        BigDecimal finalPrice = cartItemsEntityModel.getProduct().getFinalPrice();
        int price = cartItemsEntityModel.getProduct().getPrice();

        if(quantity > minQuantity) {
            CartItemsEntityModel cartModel = new CartItemsEntityModel();
            cartModel.setProduct(cartItemsEntityModel.getProduct());
            quantity--;
            cartModel.setQuantity(quantity);

            finalPrice = BigDecimal.valueOf(price * quantity);
            cartModel.getProduct().setFinalPrice(finalPrice);

            productRecyclerAdapter.updateItem(position, cartModel);
            setTotalPrice();
            setDelivery();
        }
    }

    @Override
    public void onUpdateList() {
        productRecyclerAdapter = new CartRecyclerAdapter(context, CartHelper.getCartItems());
        productRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(productRecyclerAdapter);
        entityCheckMethods();
        updateViewBasket();
    }

    @OnClick(R.id.buyButton)
    public void onBuyClick() {
        /*Toast.makeText(context, String.format(getString(R.string.cart_success_message),
                CartHelper.getCart().getTotalQuantity(), CartHelper.getCart().getTotalPrice()), Toast.LENGTH_LONG).show();
        CartHelper.getCart().clear();
        getActivity().finish();*/
    }

    @OnClick(R.id.cart__delivery)
    public void onDeliveryClick() {
        buttonPickup.setBackgroundResource(R.drawable.frame_translucent_no_padding);
        buttonDelivery.setBackgroundResource(R.drawable.frame_red_no_padding);

        initializeDelivery(new DeliveryFragment());
    }

    @OnClick(R.id.cart__pickup)
    public void onPickupClick() {
        buttonDelivery.setBackgroundResource(R.drawable.frame_translucent_no_padding);
        buttonPickup.setBackgroundResource(R.drawable.frame_red_no_padding);

        initializeDelivery(new PickupFragment());
    }

    private void initializeDelivery(Fragment fragment){
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void updateViewBasket() {
        BasketFragment basketFragment = (BasketFragment) getChildFragmentManager().findFragmentById(R.id.fragment_container_view_menu)
                .getChildFragmentManager().findFragmentById(R.id.fragment_basket);
        basketFragment.setupBadge();
    }
}
