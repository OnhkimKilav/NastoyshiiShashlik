package com.example.nastoyshiishashlik.ui;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

import com.example.nastoyshiishashlik.R;

import butterknife.BindView;
import butterknife.OnClick;

public class DeliveryPaymentActivity extends BaseActivity {

    @BindView(R.id.delivery_payment_activity_ns)
    NestedScrollView scrollView;
    @BindView(R.id.toUp)
    ImageView buttonUp;

    @Override
    public int getViewId() {
        return R.layout.activity_delivery_payment;
    }

    @Override
    public void onCreateView() {
        checkButtonUpVisibility();
    }

    @OnClick(R.id.toUp)
    public void onClickToUp(){
        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    private void checkButtonUpVisibility(){
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY(); //for verticalScrollView
                if (scrollY <= 300)
                    buttonUp.setVisibility(View.INVISIBLE);
                    //button visible
                else
                    buttonUp.setVisibility(View.VISIBLE);
                //button invisible
            }
        });
    }
}