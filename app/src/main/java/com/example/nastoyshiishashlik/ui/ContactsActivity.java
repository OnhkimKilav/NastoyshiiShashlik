package com.example.nastoyshiishashlik.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsActivity extends BaseActivity implements OnMapReadyCallback {
    private static final String TAG = ContactsActivity.class.getCanonicalName();

    @BindView(R.id.contacts_activity_ns)
    NestedScrollView scrollView;
    @BindView(R.id.toUp)
    ImageView buttonUp;
    @BindView(R.id.contacts_activity__message)
    EditText message;
    @BindView(R.id.contacts_activity__email)
    EditText email;
    @BindView(R.id.contacts_activity__name)
    EditText name;

    private SupportMapFragment mapFragment;

    @Override
    public int getViewId() {
        return R.layout.activity_contacts;
    }

    @Override
    public void onCreateView() {
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        checkButtonUpVisibility();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        bitmapDescriptorFromVector(R.drawable.ic_placeholder)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmapDescriptor -> {
                    LatLng shashlik = new LatLng(50.451009, 30.3886107);
                    googleMap.addMarker(new MarkerOptions().position(shashlik).title("Настоящий Шашлык")
                            .icon(bitmapDescriptor));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shashlik, 15f));
                    googleMap.stopAnimation();
                }, throwable -> Log.e(TAG, "onMapReady: description is not done"));
    }

    @OnClick(R.id.toUp)
    public void onClickToUp(){
        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    @OnClick(R.id.contacts_activity__send_email)
    public void onClickSendEmail(){
        if(name.getText().toString().equals(""))
            Toast.makeText(this, "Поле \"Имя\" не должно быть пустым", Toast.LENGTH_SHORT).show();
        else if(email.getText().toString().equals(""))
            Toast.makeText(this, "Поле \"Email\" не должно быть пустым", Toast.LENGTH_SHORT).show();
        else if(message.getText().toString().equals(""))
            Toast.makeText(this, "Поле \"Сообщение\" не должно быть пустым", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "nashashlyk@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Доброго времени суток, меня зовут " + name.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString() +
                    "\n" + "Я хочу чтобы вы со мной связались по этому " + email.getText().toString());
            startActivity(intent);
        }

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

    private Single<BitmapDescriptor> bitmapDescriptorFromVector(int vectorResId) {
        return Single.create(emitter -> {
            Drawable vectorDrawable = ContextCompat.getDrawable(this, vectorResId);
            vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
            Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.draw(canvas);
            emitter.onSuccess(BitmapDescriptorFactory.fromBitmap(bitmap));
        });
    }

    @Override
    protected void onStop() {
        mapFragment.onStop();
        super.onStop();
        Log.d(TAG, "onStop: method was called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: method was called");
    }
}