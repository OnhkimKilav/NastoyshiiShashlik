package com.example.nastoyshiishashlik.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.models.CartHelper;
import com.example.nastoyshiishashlik.ui.CartActivity;
import com.example.nastoyshiishashlik.ui.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BasketFragment extends BaseFragment {

    @BindView(R.id.logo_cart_badge)
    TextView textCartItemCount;

    @Override
    public int getViewId() {
        return R.layout.view_custom_action_cart;
    }

    @Override
    public void onViewCreated(View view) {
        setupBadge();
    }

    @OnClick(R.id.basket)
    public void onClickBasket(){
        setupBadge();
        Intent intent = new Intent(context, CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }

    public void setupBadge() {
        if (textCartItemCount != null) {
            if (CartHelper.getCartItems().size() == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(CartHelper.getCartItems().size(), 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public interface Communicator
    {
        void updateViewBasket();
    }
}
