package com.example.nastoyshiishashlik.fragments;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nastoyshiishashlik.R;

import butterknife.BindView;
import butterknife.OnClick;

public class DeliveryPaymentFragment extends BaseFragment{
    @BindView(R.id.fragment_delivery_payment_test__ll_choice_delivery)
    LinearLayout linLayChoiceDelivery;
    @BindView(R.id.fragment_delivery_payment_test__ll_delivery)
    LinearLayout linLayDelivery;
    @BindView(R.id.fragment_delivery_payment_test__text)
    TextView tvDelivery;
    @BindView(R.id.fragment_delivery_payment_test__tv_cash)
    TextView tvCash;
    @BindView(R.id.fragment_delivery_payment_test__tv_card_delivery)
    TextView tvCardDelivery;
    @BindView(R.id.fragment_delivery_payment_test__tv_card_site)
    TextView tvCardSite;

    @Override
    public int getViewId() {
        return R.layout.fragment_delivery_payment_test;
    }

    @Override
    public void onViewCreated(View view) {

    }

    @OnClick(R.id.fragment_delivery_payment_test__ll_arrow)
    public void OnClickArrow(){
        linLayDelivery.setVisibility(View.INVISIBLE);
        linLayChoiceDelivery.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.fragment_delivery_payment_test__tv_cash)
    public void onClickCash(){
        tvDelivery.setText(tvCash.getText());
        linLayDelivery.setVisibility(View.VISIBLE);
        linLayChoiceDelivery.setVisibility(View.GONE);
    }

    @OnClick(R.id.fragment_delivery_payment_test__tv_card_delivery)
    public void OnClickCardDelivery(){
        tvDelivery.setText(tvCardDelivery.getText());
        linLayDelivery.setVisibility(View.VISIBLE);
        linLayChoiceDelivery.setVisibility(View.GONE);
    }

    @OnClick(R.id.fragment_delivery_payment_test__tv_card_site)
    public void OnClickCardSite(){
        tvDelivery.setText(tvCardSite.getText());
        linLayDelivery.setVisibility(View.VISIBLE);
        linLayChoiceDelivery.setVisibility(View.GONE);
    }
}
