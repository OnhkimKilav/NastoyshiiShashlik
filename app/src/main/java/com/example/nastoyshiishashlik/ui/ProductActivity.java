package com.example.nastoyshiishashlik.ui;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.fragments.BasketFragment;
import com.example.nastoyshiishashlik.fragments.ProductFragment;
import com.example.nastoyshiishashlik.fragments.SingleProductFragment;
import com.example.nastoyshiishashlik.models.ProductModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductActivity extends BaseActivity implements BasketFragment.Communicator{
    private static final String TAG = ProductActivity.class.getCanonicalName();

    @BindView(R.id.product_activity_ns_main)
    NestedScrollView scrollView;
    @BindView(R.id.toUp)
    ImageView buttonUp;

    private ProductModel productModel;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private final ArrayList<ProductModel> listSentencesProducts = new ArrayList<>();

    @Override
    public int getViewId() {
        return R.layout.activity_product;
    }


    @Override
    protected void onStart() {
        super.onStart();

        initFragmentProduct(productModel);
        initSentencesProductList();
    }

    @Override
    public void onCreateView() {
        productModel = (ProductModel) getIntent().getSerializableExtra("product");
        generationSentencesList();
        checkButtonUpVisibility();
    }

    @OnClick(R.id.toUp)
    public void onClickToUp(){
        scrollView.fullScroll(ScrollView.FOCUS_UP);
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

    private void initFragmentProduct(ProductModel product){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);

        SingleProductFragment singleProductFragment = new SingleProductFragment();
        singleProductFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.conteiner, singleProductFragment);
        fragmentTransaction.commit();
    }

    private void initSentencesProductList(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("productList", listSentencesProducts);
        bundle.putString("orientation", "horizontal");
        bundle.putString("idStyleLayout", String.valueOf(R.layout.sentences));

        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.conteiner_sentences, productFragment);
        fragmentTransaction.commit();

        Log.d(TAG, "method initSentencesProductList is commit");
    }

    //Get list products for sentences
    private void generationSentencesList(){
        if(!productModel.getSentences().equals("null")) {
            String[] res = productModel.getSentences().split("[,]", 0);
            for (String s : res) {
                getDatabase().getById(Integer.parseInt(s))
                        .subscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(product1 -> {
                            listSentencesProducts.add(product1);
                            Log.d(TAG, "generationSentencesList: read sentences's product (" + product1.toString() + ")");
                        });
            }
        }
    }

    @Override
    public void updateViewBasket() {
        BasketFragment basketFragment = (BasketFragment) fragmentManager.findFragmentById(R.id.product_activity__frag_top_button)
                .getChildFragmentManager().findFragmentById(R.id.fragment_basket);
        basketFragment.setupBadge();
    }

}
