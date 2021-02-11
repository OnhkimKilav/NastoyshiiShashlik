package com.example.nastoyshiishashlik.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.controller.PopupController;
import com.example.nastoyshiishashlik.ui.MainActivity;

public class HeaderButtonFragment extends Fragment implements View.OnClickListener {
    private Dialog dialog;
    private ImageView phone, menu, logo;

    public HeaderButtonFragment() {
        super(R.layout.fragment_header_button);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new Dialog(this.getContext());
        phone = view.findViewById(R.id.header_button__iv_phone);
        menu = view.findViewById(R.id.header_button__iv_menu);
        logo = view.findViewById(R.id.header_button__iv_logo);

        phone.setOnClickListener(this);
        menu.setOnClickListener(this);
        logo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.header_button__iv_phone: {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+380672515945"));
                startActivity(intent);
                break;
            }
            /**
             * Initialize popup menu
             * This popup menu was realized using a dialog box
             *
             * @param v
             */
            case R.id.header_button__iv_menu: {
                PopupController popupController = new PopupController();
                popupController.createPopupMenu(dialog);
                break;
            }
            case R.id.header_button__iv_logo: {
                Intent intent = new Intent(App.getContext(), MainActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
