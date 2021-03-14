package com.example.nastoyshiishashlik.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.models.Dishes;
import com.example.nastoyshiishashlik.ui.ContactsActivity;
import com.example.nastoyshiishashlik.ui.DeliveryPaymentActivity;
import com.example.nastoyshiishashlik.ui.ListProductsByDishesActivity;
import com.example.nastoyshiishashlik.ui.MainActivity;

import butterknife.OnClick;

public class BottomMenuFragment extends BaseFragment {

    private static final String TAG = BottomMenuFragment.class.getCanonicalName();

    @Override
    public int getViewId() {
        return R.layout.bottom_menu;
    }

    @Override
    public void onViewCreated(View view) {

    }

    @OnClick(R.id.imageBottomLogo)
    public void onClickLogo(){
        Intent intent = new Intent(context, MainActivity.class);
        context.finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.bottom_menu__stock)
    public void onClickStock(){
        Intent intent = new Intent(context, ListProductsByDishesActivity.class);
        context.finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        intent.putExtra("product", Dishes.STOCK.getTitle());
        startActivity(intent);
    }

    @OnClick(R.id.bottom_menu__contacts)
    public void onClickContacts(){
        Intent intent = new Intent(context, ContactsActivity.class);
        context.finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.bottom_menu__delivery)
    public void onClickDelivery(){
        Intent intent = new Intent(context, DeliveryPaymentActivity.class);
        context.finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.bottom_menu__ceitering)
    public void onClickCeitering(){
        Toast toast = Toast.makeText(context, R.string.ceitering_toast, Toast.LENGTH_SHORT);
        toast.show();
    }

    @OnClick(R.id.bottom_menu__instagram)
    public void onClickInstagram(){
        startActivity(newInstagramProfileIntent(context.getPackageManager(),
                "https://www.instagram.com/nashashlyk_kyiv/"));
    }

    @OnClick(R.id.bottom_menu__facebook)
    public void onClickFacebook(){
        startActivity(getOpenFacebookIntent(context));
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
