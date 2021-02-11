package com.example.nastoyshiishashlik.controller;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.service.MenuAdapter;
import com.example.nastoyshiishashlik.ui.ContactsActivity;
import com.example.nastoyshiishashlik.ui.DeliveryPaymentActivity;

public class PopupController {
    private TextView tvPaymentDelivery, tvContacts, tvCatering;

    public void createPopupMenu(Dialog dialog){
        ImageView ivClose;
        RecyclerView recyclerView;
        dialog.setContentView(R.layout.custompopup);
        ivClose = dialog.findViewById(R.id.iv_exit);
        tvPaymentDelivery = dialog.findViewById(R.id.popup__tv_payment_delivery);
        tvContacts = dialog.findViewById(R.id.popup__tv_contacts);
        tvCatering = dialog.findViewById(R.id.popup__tv_catering);
        recyclerView = dialog.findViewById(R.id.popup_menu__rv_dishes);

        MenuAdapter menuAdapter = new MenuAdapter(R.id.popup_menu__rv_dishes);
        recyclerView.setAdapter(menuAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(App.getContext());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvPaymentDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.getContext(), DeliveryPaymentActivity.class);
                App.getContext().startActivity(intent);
                dialog.dismiss();
            }
        });

        tvCatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(App.getContext(),
                        "Кейтеринг в данный момент не доступен", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        tvContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.getContext(), ContactsActivity.class);
                App.getContext().startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.getWindow().setGravity(Gravity.RIGHT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
