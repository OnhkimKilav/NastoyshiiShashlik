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

public class BasketFragment extends Fragment {

    private TextView textCartItemCount;
    private ImageView basket;

    public BasketFragment() {
        super(R.layout.view_custom_action_cart);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textCartItemCount = view.findViewById(R.id.logo_cart_badge);
        basket = view.findViewById(R.id.basket);

        setupBadge();
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupBadge();
                startActivity(new Intent(App.getContext(), CartActivity.class));
            }
        });
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
