package com.example.nastoyshiishashlik.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.api.NetworkService;
import com.example.nastoyshiishashlik.api.model.requestOrder.Order;
import com.example.nastoyshiishashlik.api.model.responseOrder.Example;
import com.example.nastoyshiishashlik.dao.roomDao.ReadCSV;

import java.util.ArrayList;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryFragment extends BaseDeliveryFragment {

    private static final String TAG = DeliveryFragment.class.getCanonicalName();

    @BindView(R.id.fragment_delivery__first_name)
    EditText name;
    @BindView(R.id.fragment_delivery__phone)
    EditText phone;
    @BindView(R.id.fragment_delivery__address)
    AutoCompleteTextView address;
    @BindView(R.id.fragment_delivery__house)
    EditText house;
    @BindView(R.id.fragment_delivery__floor)
    EditText floor;
    @BindView(R.id.fragment_delivery__apartment)
    EditText apartment;
    @BindView(R.id.fragment_delivery__check_box_dishes)
    CheckBox checkBoxDishes;
    @BindView(R.id.fragment_delivery__quantity_dishes)
    TextView quantityDishes;
    @BindView(R.id.fragment_delivery__message)
    EditText message;
    @BindView(R.id.fragment_delivery__ll_dishes)
    LinearLayout linearLayoutDishes;
    @BindView(R.id.fragment_delivery__final_price)
    TextView finalPrice;

    private int countDishes;
    private String strDishes = "";
    private List<String> streets = new ArrayList<>();
    //private String strFinalPrice = "0";

    @Override
    public int getViewId() {
        return R.layout.fragment_delivery;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initializeStreetsList();
    }

    @Override
    public void onViewCreated(View view) {
        countDishes = Integer.parseInt(quantityDishes.getText().toString());

        createMaskForPhoneNumber(phone);
        countFinalPriceProducts(finalPrice);
        initializeAutoCompleteTextView();
        setCheckBox();
    }

    @OnClick(R.id.fragment_delivery__minus)
    void OnClickMinus(){
        if(countDishes > 1){
            countDishes--;
            quantityDishes.setText(String.valueOf(countDishes));
        }
    }

    @OnClick(R.id.fragment_delivery__plus)
    void OnClickPlus(){
        if(countDishes < 10){
            countDishes++;
            quantityDishes.setText(String.valueOf(countDishes));
        }
    }

    private void setCheckBox(){
        checkBoxDishes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutDishes.setVisibility(View.VISIBLE);
                    strDishes = "; Количество одноразовой посуды: ";
                }else{
                    linearLayoutDishes.setVisibility(View.INVISIBLE);
                    strDishes = "";
                }
            }
        });
    }


    @OnClick(R.id.buyButton)
    public void onBuyClick() {
        if(checkOrder(name, phone)) {
            Order order = initializeOrder(name, phone, strDishes, countDishes, message);

            //TODO
            //Сделать конечную картинку заказа
            NetworkService.instance().postOrder(App.getContext().getResources().getString(R.string.token),
                    order).enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    CreateOrderDialogFragment fragment = CreateOrderDialogFragment.newInstance();
                    fragment.show(getChildFragmentManager(), "createOrder");
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Toast.makeText(context, "Ошибка, заказ не принят", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public boolean checkOrder(TextView name, TextView phone) {
        if(super.checkOrder(name, phone)){
            if(address.getText().toString().equals("")){
                Toast.makeText(context, getResources().getString(R.string.empty_street), Toast.LENGTH_SHORT).show();
                return false;
            }else if(house.getText().toString().equals("")){
                Toast.makeText(context, getResources().getString(R.string.empty_house), Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Order initializeOrder(TextView name, TextView phone, String strDishes, int countDishes, EditText message) {
        Order order = super.initializeOrder(name, phone, strDishes, countDishes, message);

        if(!address.getText().toString().equals("")) {
            order.setAddress(address.getText().toString());

            if(!house.getText().toString().equals(""))
                order.setAddress(order.getAddress() + ",дом:"+house.getText().toString());

            if(!floor.getText().toString().equals(""))
                order.setAddress(order.getAddress() +",этаж:"+ floor.getText().toString());

            if(!apartment.getText().toString().equals(""))
                order.setAddress(order.getAddress() +",квартира:"+ apartment.getText().toString());
        }

        order.setServiceMode("3");

        return order;
    }

    private void initializeAutoCompleteTextView(){
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(App.getContext(), R.layout.support_simple_spinner_dropdown_item, streets);
        address.setAdapter(adapter);
    }


    @SuppressLint("CheckResult")
    private void initializeStreetsList(){
        ReadCSV readCSV = new ReadCSV();
        readCSV.readStreets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(streets -> {
                    this.streets.addAll(streets);
                    Log.d(TAG, "bind: optimization poster for hits is successful");
                }, throwable -> Log.e(TAG, "bind: optimization poster for hits isn't successful"));
    }

}
