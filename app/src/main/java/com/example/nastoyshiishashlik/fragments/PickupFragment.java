package com.example.nastoyshiishashlik.fragments;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.api.NetworkService;
import com.example.nastoyshiishashlik.api.model.requestOrder.Order;
import com.example.nastoyshiishashlik.api.model.responseOrder.Example;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickupFragment extends BaseDeliveryFragment {

    private static final String TAG = PickupFragment.class.getCanonicalName();

    @BindView(R.id.fragment_pickup__name)
    EditText name;
    @BindView(R.id.fragment_pickup__phone)
    EditText phone;
    @BindView(R.id.fragment_pickup__check_box_dishes)
    CheckBox checkBoxDishes;
    @BindView(R.id.fragment_pickup__quantity_dishes)
    TextView quantityDishes;
    @BindView(R.id.fragment_pickup__message)
    EditText message;
    @BindView(R.id.fragment_pickup__ll_dishes)
    LinearLayout linearLayoutDishes;
    @BindView(R.id.fragment_pickup__final_price)
    TextView finalPrice;

    private String strDishes = "";
    private int countDishes;

    @Override
    public int getViewId() {
        return R.layout.fragment_pickup;
    }

    @Override
    public void onViewCreated(View view) {
        createMaskForPhoneNumber(phone);
        countFinalPriceProducts(finalPrice);
        setCheckBox();

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
            //Сделать картинку после заказа
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

    @OnClick(R.id.fragment_pickup__minus)
    void onClickMinus(){
        if(countDishes > 1){
            countDishes--;
            quantityDishes.setText(String.valueOf(countDishes));
        }
    }

    @OnClick(R.id.fragment_delivery__plus)
    void onClickPlus(){
        if(countDishes < 10){
            countDishes++;
            quantityDishes.setText(String.valueOf(countDishes));
        }
    }

    @Override
    public Order initializeOrder(TextView name, TextView phone, String strDishes, int countDishes, EditText message) {
        Order order = super.initializeOrder(name, phone, strDishes, countDishes, message);

        order.setServiceMode("2");

        return order;
    }
}
