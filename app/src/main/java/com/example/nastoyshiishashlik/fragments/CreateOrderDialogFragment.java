package com.example.nastoyshiishashlik.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.models.CartHelper;
import com.example.nastoyshiishashlik.ui.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateOrderDialogFragment extends AppCompatDialogFragment {

    public static CreateOrderDialogFragment newInstance() {

        Bundle args = new Bundle();
        CreateOrderDialogFragment fragment = new CreateOrderDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_order, container, false);
        ButterKnife.bind(this, view);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setGravity(Gravity.CENTER);

        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        return view;
    }

    @OnClick(R.id.create_oder_button)
    public void onClickButton(){
        CartHelper.getCart().clear();

        Intent intent = new Intent(getContext(), MainActivity.class);
        getActivity().finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }
}
