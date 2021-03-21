package com.example.nastoyshiishashlik.fragments;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.adapters.CartRecyclerAdapter;
import com.example.nastoyshiishashlik.liveData.LiveDataOrder;
import com.example.nastoyshiishashlik.models.CartHelper;
import com.example.nastoyshiishashlik.models.CartItemsEntityModel;

import java.math.BigDecimal;
import butterknife.BindView;
import butterknife.OnClick;


public class CartFragment extends BaseFragment implements CartRecyclerAdapter.OnItemClickListener, BasketFragment.Communicator {
    private final static String TAG = CartFragment.class.getCanonicalName();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.cart__empty_basket)
    LinearLayout linLayEmptyBasket;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.delivery)
    TextView delivery;
    @BindView(R.id.cart__list_products)
    RelativeLayout relativeLayout;
    @BindView(R.id.cart__delivery)
    TextView buttonDelivery;
    @BindView(R.id.cart__pickup)
    TextView buttonPickup;
    @BindView(R.id.cart_activity_ns)
    NestedScrollView scrollView;
    @BindView(R.id.toUp)
    ImageView buttonUp;
    @BindView(R.id.pickup_text)
    LinearLayout linearLayoutPickUpText;
    @BindView(R.id.fragment_cart__main_rel_lay)
    RelativeLayout mainRelativeLayout;

    private CartRecyclerAdapter productRecyclerAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LiveDataOrder liveDataOrder;
    private boolean pickup = false;

    @Override
    public int getViewId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void onViewCreated(View view) {
        liveDataOrder = LiveDataOrder.getInstance();
        onUpdateList();

        entityCheckMethods();
        onDeliveryClick();
        checkButtonUpVisibility();
        checkKeyboardVisibility();
    }

    @Override
    public void onItemClick(CartItemsEntityModel cartItemsEntityModel) {

    }


    @OnClick(R.id.toUp)
    public void onClickToUp(){
        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    public void entityCheckMethods(){
        checkEmptyBasket();
        setTotalPrice();
        setDelivery();
        setHeight();
    }

    @Override
    public void onItemPlusClicked(int position, CartItemsEntityModel cartItemsEntityModel) {
        int quantity = cartItemsEntityModel.getQuantity();
        BigDecimal finalPrice = cartItemsEntityModel.getProduct().getFinalPrice();
        int price = cartItemsEntityModel.getProduct().getPrice();

        CartItemsEntityModel cartModel = new CartItemsEntityModel();
        cartModel.setProduct(cartItemsEntityModel.getProduct());
        quantity++;
        cartModel.setQuantity(quantity);

        finalPrice = BigDecimal.valueOf(price * quantity);
        cartModel.getProduct().setFinalPrice(finalPrice);

        productRecyclerAdapter.updateItem(position, cartModel);
        setTotalPrice();
        setDelivery();
    }

    @Override
    public void onItemMinusClicked(int position, CartItemsEntityModel cartItemsEntityModel) {
        int minQuantity = (int) (cartItemsEntityModel.getProduct().getMinWeightForOrder() / cartItemsEntityModel.getProduct().getWeight());
        int quantity = cartItemsEntityModel.getQuantity();
        BigDecimal finalPrice = cartItemsEntityModel.getProduct().getFinalPrice();
        int price = cartItemsEntityModel.getProduct().getPrice();

        if(quantity > minQuantity) {
            CartItemsEntityModel cartModel = new CartItemsEntityModel();
            cartModel.setProduct(cartItemsEntityModel.getProduct());
            quantity--;
            cartModel.setQuantity(quantity);

            finalPrice = BigDecimal.valueOf(price * quantity);
            cartModel.getProduct().setFinalPrice(finalPrice);

            productRecyclerAdapter.updateItem(position, cartModel);
            setTotalPrice();
            setDelivery();
        }
    }

    @Override
    public void onUpdateList() {
        productRecyclerAdapter = new CartRecyclerAdapter(context, CartHelper.getCartItems());
        productRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(productRecyclerAdapter);
        entityCheckMethods();
        updateViewBasket();

    }



    @OnClick(R.id.cart__delivery)
    public void onDeliveryClick() {
        buttonPickup.setBackgroundResource(R.drawable.frame_translucent_no_padding);
        buttonDelivery.setBackgroundResource(R.drawable.frame_red_no_padding);
        linearLayoutPickUpText.setVisibility(View.GONE);

        //устанавливаем переменную в false для пересчета доставки
        pickup = false;

        //при переключении на доставку перепроверять финальную цену
        setTotalPrice();
        setDelivery();

        initializeDelivery(new DeliveryFragment());
    }

    @OnClick(R.id.cart__pickup)
    public void onPickupClick() {
        //меняется цвет кнопки
        buttonDelivery.setBackgroundResource(R.drawable.frame_translucent_no_padding);
        buttonPickup.setBackgroundResource(R.drawable.frame_red_no_padding);
        linearLayoutPickUpText.setVisibility(View.VISIBLE);

        //устанавливаем переменную в true для пересчета доставки
        pickup = true;

        setTotalPricePickup();
        setDelivery();

        initializeDelivery(new PickupFragment());
    }


    private void checkKeyboardVisibility(){
        mainRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                mainRelativeLayout.getWindowVisibleDisplayFrame(r);

                int heightDiff = mainRelativeLayout.getRootView().getHeight() - r.height();
                if (heightDiff > 0.25*mainRelativeLayout.getRootView().getHeight()) { // if more than 25% of the screen, its probably a keyboard...
                    buttonUp.setVisibility(View.GONE);
                }else buttonUp.setVisibility(View.VISIBLE);
            }
        });
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




    private void checkEmptyBasket(){
        if(CartHelper.getCart().getProducts().size() > 0)
            linLayEmptyBasket.setVisibility(View.GONE);
        else linLayEmptyBasket.setVisibility(View.VISIBLE);
    }

    private void setHeight(){
        if(CartHelper.getCartItems().size() >= 2) {
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 730);
            recyclerView.setLayoutParams(lp);
        }else{
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(lp);
        }
    }

    private void setDelivery(){
        delivery.setText(String.format(context.getString(R.string.price_cart_format),
                (CartHelper.getCart().getDelivery(pickup))));
    }

    private void setTotalPrice() {
        if (CartHelper.getCartItems().size() == 0){
            this.totalPrice.setText(String.format(context.getString(R.string.price_cart_format), 0));
            liveDataOrder.setLiveData(BigDecimal.valueOf(0));
        } else{
            BigDecimal totalPrice = CartHelper.getCart().getTotalPrice();
            this.totalPrice.setText(String.format(context.getString(R.string.total_price_cart_format),totalPrice));
            liveDataOrder.setLiveData(totalPrice);
        }
        setDelivery();
    }

    /**
     * Изменение текстовых полей при выборе самовывоза
     */
    private void setTotalPricePickup(){
        //Если корзина пуста, тогда выставляем все по нулям
        if (CartHelper.getCartItems().size() == 0) {
            totalPrice.setText(String.format(context.getString(R.string.price_cart_format), 0));

            liveDataOrder.setLiveData(BigDecimal.valueOf(0));
        }else {
            //При переключении на самовывоз передается новая цена со скидкой
            BigDecimal totalPriceWithDiscount = CartHelper.getCart().getTotalPriceWithDiscount();
            totalPrice.setText(String.format(context.getString(R.string.total_price_cart_format),
                    totalPriceWithDiscount));
            //Финальная цена передается в лайв дату для передачи во фрагмент
            liveDataOrder.setLiveData(totalPriceWithDiscount);
        }
        setDelivery();
    }



    private void initializeDelivery(Fragment fragment){
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void updateViewBasket() {
        BasketFragment basketFragment = (BasketFragment) getChildFragmentManager().findFragmentById(R.id.fragment_container_view_menu)
                .getChildFragmentManager().findFragmentById(R.id.fragment_basket);
        basketFragment.setupBadge();
    }
}
