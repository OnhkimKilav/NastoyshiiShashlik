package com.example.nastoyshiishashlik.menuBar;


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
import com.example.nastoyshiishashlik.ListProductsByDishesActivity;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.model.Menu;

import com.example.nastoyshiishashlik.model.Product;
import com.example.nastoyshiishashlik.optimization.OptimizationBitmap;


import java.util.ArrayList;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private final String TAG = MenuAdapter.class.getCanonicalName();
    public static final String EXTRA_MESSAGE = "com.example.nastoyshiishashlik.menuBar.MenuAdapter";
    private final List<Menu> menus;
    private final int id;
    private ProductDBModel database = new ProductDBModel();

    public MenuAdapter(List<Menu> menus, int id) {
        this.menus = menus;
        this.id = id;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (id){
            case R.id.main_activity__rv_pop_up_menu_icons:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main__pop_up_menu_item, viewGroup, false);
                break;
            case R.id.main_activity__rv_menu_icons:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main__menu_item, viewGroup, false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int position) {
        menuViewHolder.bind(menus.get(position));

        menuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize list for products
                List<Product> productList = new ArrayList<>();
                //Initialization intent for creating activity for displaying list dishes
                Intent intent = new Intent(App.getContext(), ListProductsByDishesActivity.class);
                //Get dishes of the product you have clicked on
                String sDishes = menus.get(position).getDishes().getTitle();

                //Get all products from DB where dishes equals our product
                database.getByDishes(sDishes)
                        .subscribeOn(Schedulers.io())
                        .subscribe(products -> {
                            productList.addAll(products);
                            Log.d(TAG, "onClick: get products from db by dishes is successful");
                        }, throwable -> Log.e(TAG, "onClick: get products from db by dishes is not successful"));

                //TODO
                //create intent and transfer to list products
                //intent.putExtra(EXTRA_MESSAGE, productList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menus.size();
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
