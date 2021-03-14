package com.example.nastoyshiishashlik.fragments;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.api.model.requestOrder.Order;
import com.example.nastoyshiishashlik.api.model.requestOrder.Product;
import com.example.nastoyshiishashlik.liveData.LiveDataOrder;
import com.example.nastoyshiishashlik.models.CartHelper;
import com.example.nastoyshiishashlik.models.CartItemsEntityModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public abstract class BaseDeliveryFragment extends BaseFragment{

    public void countFinalPriceProducts(TextView finalPrice){
        LiveData<BigDecimal> liveData = LiveDataOrder.getInstance().getData();

        liveData.observe(getViewLifecycleOwner(), new Observer<BigDecimal>() {
            @Override
            public void onChanged(BigDecimal bigDecimal) {
                finalPrice.setText(String.format(context.getString(R.string.price_cart_format), bigDecimal.intValue()));
            }
        });
    }

    public void createMaskForPhoneNumber(EditText phone){
        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("+38(0__)___-__-__");
        FormatWatcher formatWatcher = new MaskFormatWatcher( // форматировать текст будет вот он
                MaskImpl.createTerminated(slots)
        );
        formatWatcher.installOn(phone);
    }

    public boolean checkOrder(TextView name, TextView phone){
        if(CartHelper.getCart().getProducts().size()==0){
            Toast.makeText(context, getResources().getString(R.string.empty_basket), Toast.LENGTH_SHORT).show();
            return false;
        }else if(name.getText().toString().equals("")){
            Toast.makeText(context, getResources().getString(R.string.empty_name), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!name.getText().toString().matches("^[а-яёА-ЯЁ]+$")){
            Toast.makeText(context, getResources().getString(R.string.mask_name),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if(phone.getText().toString().equals("")){
            Toast.makeText(context, getResources().getString(R.string.empty_phone), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public Order initializeOrder(TextView name, TextView phone, String strDishes, int countDishes,
                                 EditText message){
        List<Product> productList = new ArrayList<>();
        for (CartItemsEntityModel cartItemsEntityModel: CartHelper.getCartItems()) {
            Product product = new Product();
            product.setProductId(String.valueOf(cartItemsEntityModel.getProduct().getId()));
            product.setCount(String.valueOf(cartItemsEntityModel.getQuantity()));
            productList.add(product);
        }
        Order order = new Order();
        order.setSpotId("1");

        if(!name.getText().toString().equals(""))
            order.setFirstName(name.getText().toString());

        if(!phone.getText().toString().equals(""))
            order.setPhone(phone.getText().toString());

        if(!((CalendarFragment)getChildFragmentManager().findFragmentById(R.id.delivery__calendar)).getDate().equals("")){
            order.setComment("Дата: "+((CalendarFragment)getChildFragmentManager().findFragmentById(R.id.delivery__calendar)).getDate());
        }

        if(!((CalendarFragment)getChildFragmentManager().findFragmentById(R.id.delivery__calendar)).getTime().equals("")){
            order.setComment(order.getComment() +"; Время: "+((CalendarFragment)getChildFragmentManager().findFragmentById(R.id.delivery__calendar)).getTime());
        }

        //TODO
        //Проверить отправку на постер с украинским языком
        if(!((DeliveryPaymentFragment)getChildFragmentManager().findFragmentById(R.id.delivery__payment)).tvDelivery.equals("")){
            switch (((DeliveryPaymentFragment)getChildFragmentManager().findFragmentById(R.id.delivery__payment)).tvDelivery.getText().toString()){
                case "Наличными" :
                case "Готівкою" :
                    order.setComment(order.getComment() + "; Оплата наличными");
                    break;
                case "Картой курьеру" :
                case "Картою кур'єру" :
                    order.setComment(order.getComment() + "; Картой курьеру");
                    break;
                case "Картой на сайте" :
                case "Картою на сайті" :
                    order.setComment(order.getComment() + "; Картой на сайте");
                    break;
            }
        }

        if(!strDishes.equals("")) {
            order.setComment(order.getComment() + strDishes + countDishes);
        }

        if(!message.getText().toString().equals(""))
            order.setComment(order.getComment() + "; " + message.getText().toString());

        order.setProducts(productList);

        return order;
    }
}
