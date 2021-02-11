package com.example.nastoyshiishashlik.fragments;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.adapters.ProductRecyclerAdapter;
import com.example.nastoyshiishashlik.entities.CartEntity;
import com.example.nastoyshiishashlik.models.CartHelper;
import com.example.nastoyshiishashlik.models.ProductEntityModel;
import com.example.nastoyshiishashlik.models.ProductModel;
import com.example.nastoyshiishashlik.utils.OptimizationBitmap;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SingleProductFragment extends BaseFragment{
    private String TAG = SingleProductFragment.class.getCanonicalName();
    private ProductModel product;
    private BasketFragment.Communicator communicator;

    @BindView(R.id.single_product__poster)
    ImageView image;
    @BindView(R.id.single_product__name_product)
    TextView title;
    @BindView(R.id.single_product__description)
    TextView description;
    @BindView(R.id.single_product__weight_price)
    TextView weightPrice;
    @BindView(R.id.single_product__min_weight)
    TextView minWeight;
    @BindView(R.id.single_product__quantity)
    TextView quantity;
    @BindView(R.id.single_product__plus)
    ImageButton plus;
    @BindView(R.id.single_product__minus)
    ImageButton minus;
    @BindView(R.id.single_product__price)
    TextView price;
    @BindView(R.id.single_product__buyButton)
    Button buyButton;


    @Override
    public int getViewId() {
        return R.layout.fragment_single_product;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        communicator = (BasketFragment.Communicator) activity;
    }

    @Override
    public void onViewCreated(View view) {
        product = (ProductModel) this.getArguments().getSerializable("product");

        initializeData();
        onClick();
    }

    private void onClick(){
        plus.setOnClickListener(v -> onItemPlusClicked());
        minus.setOnClickListener(v -> onItemMinusClicked());
        buyButton.setOnClickListener(v -> onItemClick());
    }

    private void initializeData(){
        title.setText(product.getName());

        switch (Double.toString(product.getWeight())){
            case ("0.5") :
                weightPrice.setText(String.format(context.getString(R.string.weight_price_litr05_format),
                        product.getPrice(), product.getWeight()));
                break;
            case ("0.25") :
                weightPrice.setText(String.format(context.getString(R.string.weight_price_litr025_format),
                        product.getPrice(), product.getWeight()));
                break;
            case ("1.0") :
                weightPrice.setText(String.format(context.getString(R.string.weight_price_shtuka_format),
                        product.getPrice(), product.getWeight()));
                break;
            default:
                weightPrice.setText(String.format(context.getString(R.string.weight_price_gramm_format),
                        product.getPrice(), product.getWeight()));
                break;
        }

        if(product.getMinWeightForOrder() > product.getWeight()){
            minWeight.setText(String.format(context.getString(R.string.min_weight_format),
                    product.getMinWeightForOrder()));
            minWeight.setVisibility(View.VISIBLE);
        }

        price.setText(String.format(context.getString(R.string.price_format),
                product.getFinalPrice()));

        quantity.setText(String.valueOf(product.getQuantity()));

        OptimizationBitmap.optimizationBitmap(
                product.getPoster(), 400, 200)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    image.setImageBitmap(bitmap);
                    Log.d(TAG, "bind: optimization poster for hits is successful");
                }, throwable -> Log.e(TAG, "bind: optimization poster for hits isn't successful"));
    }

    public void onItemClick() {
        ProductEntityModel product = new ProductEntityModel();
        product.setId(this.product.getID());
        product.setName(this.product.getName());
        product.setPrice(this.product.getPrice());
        product.setImage(this.product.getPoster());
        product.setFinalPrice(BigDecimal.valueOf(this.product.getFinalPrice()));
        product.setQuantity(this.product.getQuantity());
        product.setMinWeightForOrder(this.product.getMinWeightForOrder());
        product.setWeight(this.product.getWeight());

        CartEntity cart = CartHelper.getCart();
        cart.add(product, this.product.getQuantity());

        Toast toast = Toast.makeText(App.getContext(),
                product.getName()+" было добавленно в корзину", Toast.LENGTH_SHORT);
        toast.show();

        communicator.updateViewBasket();

    }

    public void onItemPlusClicked() {
        int quantity, price, finalPrice;

        quantity = product.getQuantity();
        quantity++;
        product.setQuantity(quantity);

        price = product.getPrice();
        finalPrice = price * quantity;
        product.setFinalPrice(finalPrice);

        updateData();
    }

    public void onItemMinusClicked() {
        int minQuantity = (int) (product.getMinWeightForOrder() / product.getWeight());

        if(product.getQuantity() > minQuantity) {
            int quantity, price, finalPrice;

            quantity = this.product.getQuantity();
            quantity--;
            this.product.setQuantity(quantity);

            price = this.product.getPrice();
            finalPrice = price * quantity;
            this.product.setFinalPrice(finalPrice);

            updateData();
        }
    }

    private void updateData(){
        quantity.setText(String.valueOf(product.getQuantity()));
        price.setText(String.format(context.getString(R.string.price_format),
                product.getFinalPrice()));
    }
}
