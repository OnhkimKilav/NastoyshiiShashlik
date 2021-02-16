package com.example.nastoyshiishashlik.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.models.Dishes;
import com.example.nastoyshiishashlik.ui.ContactsActivity;
import com.example.nastoyshiishashlik.ui.DeliveryPaymentActivity;
import com.example.nastoyshiishashlik.ui.ListProductsByDishesActivity;
import com.example.nastoyshiishashlik.ui.MainActivity;

import butterknife.BindView;

public class BottomMenuFragment extends BaseFragment {

    private static final String TAG = BottomMenuFragment.class.getCanonicalName();

    @BindView(R.id.imageBottomLogo)
    ImageView logo;
    @BindView(R.id.bottom_menu__stock)
    TextView stock;
    @BindView(R.id.bottom_menu__contacts)
    TextView contacts;
    @BindView(R.id.bottom_menu__delivery)
    TextView delivery;
    @BindView(R.id.bottom_menu__ceitering)
    TextView ceitering;
    @BindView(R.id.bottom_menu__instagram)
    ImageView instagram;
    @BindView(R.id.bottom_menu__facebook)
    ImageView facebook;

    @Override
    public int getViewId() {
        return R.layout.bottom_menu;
    }

    @Override
    public void onViewCreated(View view) {

        logo.setOnClickListener(v -> {
            Intent intent = new Intent(App.getContext(), MainActivity.class);
            startActivity(intent);
        });
        stock.setOnClickListener(v -> {
            Intent intent = new Intent(App.getContext(), ListProductsByDishesActivity.class);

            intent.putExtra("product", Dishes.STOCK.getTitle());
            App.getContext().startActivity(intent);
        });
        contacts.setOnClickListener(v -> {
            Intent intent = new Intent(App.getContext(), ContactsActivity.class);
            startActivity(intent);
        });
        delivery.setOnClickListener(v -> {
            Intent intent = new Intent(App.getContext(), DeliveryPaymentActivity.class);
            startActivity(intent);
        });
        ceitering.setOnClickListener(v -> {
            Toast toast = Toast.makeText(App.getContext(),
                    "Кейтеринг в данный момент не доступен", Toast.LENGTH_SHORT);
            toast.show();
        });
        instagram.setOnClickListener(v -> {
            startActivity(newInstagramProfileIntent(App.getContext().getPackageManager(),
                    "https://www.instagram.com/nashashlyk_kyiv/"));
        });
        facebook.setOnClickListener(v -> {
            startActivity(getOpenFacebookIntent(App.getContext()));
        });
    }

    public static Intent newInstagramProfileIntent(PackageManager pm, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                final String username = url.substring(url.lastIndexOf("/") + 1);
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1834855563473403"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/nastoiashchiishashlyk"));
        }
    }

}
