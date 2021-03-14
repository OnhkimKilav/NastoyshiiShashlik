package com.example.nastoyshiishashlik.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.adapters.MenuAdapter;
import com.example.nastoyshiishashlik.ui.ContactsActivity;
import com.example.nastoyshiishashlik.ui.DeliveryPaymentActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupDialogFragment extends AppCompatDialogFragment {
    @BindView(R.id.popup_menu__rv_dishes)
    RecyclerView recyclerView;

    public static PopupDialogFragment newInstance() {

        Bundle args = new Bundle();
        PopupDialogFragment fragment = new PopupDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custompopup, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setAdapter(new MenuAdapter(R.id.popup_menu__rv_dishes, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getDialog().getWindow().setGravity(Gravity.TOP | Gravity.END);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return view;
    }

    @Override
    public void onConfigurationChanged( Configuration newConfig ) {
        super.onConfigurationChanged( newConfig );
        if( !Objects.requireNonNull(getDialog()).isShowing() ){
            getDialog().dismiss();
        }
    }

    @OnClick(R.id.iv_exit)
    public void onClickExit(){
        getDialog().dismiss();
    }

    @OnClick(R.id.popup__tv_payment_delivery)
    public void onClickPaymentAndDelivery(){
        Intent intent = new Intent(getContext(), DeliveryPaymentActivity.class);
        getActivity().finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
        getDialog().dismiss();
    }

    @OnClick(R.id.popup__tv_contacts)
    public void onClickContact(){
        Intent intent = new Intent(getContext(), ContactsActivity.class);
        getActivity().finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
        getDialog().dismiss();
    }

    @OnClick(R.id.popup__tv_catering)
    public void onClickCatering(){
        Toast toast = Toast.makeText(getContext(), R.string.ceitering_toast, Toast.LENGTH_SHORT);
        toast.show();
    }
}