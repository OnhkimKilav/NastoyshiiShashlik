package com.example.nastoyshiishashlik.service;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.ui.ListProductsByDishesActivity;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.model.Dishes;
import com.example.nastoyshiishashlik.model.Menu;

import com.example.nastoyshiishashlik.model.Product;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private final String TAG = MenuAdapter.class.getCanonicalName();
    private final int id;
    private List<Menu> menuList = new ArrayList<>();
    private ProductDBModel database = new ProductDBModel();

    public MenuAdapter(int id) {
        if(menuList.isEmpty()) {
            menuList.addAll(generationMenuList());
        }
        this.id = id;
    }

    private List<Menu> generationMenuList() {
        List<Menu> menus = new ArrayList<>();

        menus.add(new Menu(R.drawable.stock, "акции", Dishes.STOCK));
        menus.add(new Menu(R.drawable.sets, "сеты", Dishes.SETS));
        menus.add(new Menu(R.drawable.kebab, "шашлык", Dishes.KEBAB));
        menus.add(new Menu(R.drawable.lyulyakebab, "люля-кебаб", Dishes.LYULYAKEBAB));
        menus.add(new Menu(R.drawable.grilled_fish, "рыба на\nмангале", Dishes.GRILLED_FISH));
        menus.add(new Menu(R.drawable.beverages, "напитки", Dishes.BEVERAGES));
        menus.add(new Menu(R.drawable.cold_snacks, "холодные\nзакуски", Dishes.COLD_SNACKS));
        menus.add(new Menu(R.drawable.garnish, "гарниры", Dishes.GARNISH));
        menus.add(new Menu(R.drawable.grilled_vegetables, "овощи на\nмангале", Dishes.GRILLED_VEGETABLES));
        menus.add(new Menu(R.drawable.hot_snack, "горячие\nзакуски", Dishes.HOT_SNACK));
        menus.add(new Menu(R.drawable.khachapuri, "хачапури", Dishes.KHACHAPURI));
        menus.add(new Menu(R.drawable.pickled_meat, "маринованное\nмясо", Dishes.PICKLED_MEAT));
        menus.add(new Menu(R.drawable.pita, "лаваш", Dishes.PITA));
        menus.add(new Menu(R.drawable.salad, "салаты", Dishes.SALAD));
        menus.add(new Menu(R.drawable.sauces, "соусы", Dishes.SAUCES));
        menus.add(new Menu(R.drawable.first_meal, "первые блюда", Dishes.FIRST_MEAL));

        return menus;
    }


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

        if(id == R.id.popup_menu__rv_dishes)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main__pop_up_menu_item, viewGroup, false);
        else view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main__menu_item, viewGroup, false);
        /*switch (id){
            case R.id.popup_menu__rv_dishes:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main__pop_up_menu_item, viewGroup, false);
                break;
            case R.id.main_activity__rv_menu_icons:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main__menu_item, viewGroup, false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }*/

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int position) {
        menuViewHolder.bind(menuList.get(position));

        menuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                //Initialize list for products
                List<Product> productList = new ArrayList<>();
                //Initialization intent for creating activity for displaying list dishes
                Intent intent = new Intent(App.getContext(), ListProductsByDishesActivity.class);
                //Get dishes of the product you have clicked on
                String sDishes = menuList.get(position).getDishes().getTitle();

                //Get all products from DB where dishes equals our product
                database.getByDishes(sDishes)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(products -> {
                            productList.addAll(products);
                            intent.putExtra(TAG, (Serializable) products);
                            //create intent and transfer to list products
                            App.getContext().startActivity(intent);
                            Log.d(TAG, "onClick: get products from db by dishes is successful");
                        }, throwable -> Log.e(TAG, "onClick: get products from db by dishes is not successful"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static final class MenuViewHolder extends RecyclerView.ViewHolder{
        private final String TAG = MenuViewHolder.class.getSimpleName();
        private final ImageView posterImageView;
        private final TextView nameTextView;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            this.posterImageView = itemView.findViewById(R.id.menu_item__menu_poster);
            this.nameTextView = itemView.findViewById(R.id.menu_item__menu_name);
        }

        private void bind(@NonNull Menu menu){
            OptimizationBitmap optimizationBitmap = new OptimizationBitmap();
            optimizationBitmap.optimizationBitmap(menu.getPoster(), 60, 60)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmap -> {
                        posterImageView.setImageBitmap(bitmap);
                        Log.d(TAG, "bind: optimization poster for menu is successful");
                    }, throwable -> Log.e(TAG, "bind: optimization poster for menu isn't successful"));

            nameTextView.setText(menu.getName());
        }
    }
}
