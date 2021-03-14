package com.example.nastoyshiishashlik.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.models.Dishes;
import com.example.nastoyshiishashlik.models.Menu;

import com.example.nastoyshiishashlik.ui.ListProductsByDishesActivity;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private final String TAG = MenuAdapter.class.getCanonicalName();

    private final int id;
    private final List<Menu> menuList = new ArrayList<>();
    private final Activity context;

    public MenuAdapter(int id, Activity context) {
        if(menuList.isEmpty()) {
            generationMenuList().subscribeOn(Schedulers.io())
                    .subscribe(menus -> {
                        menuList.addAll(menus);
                        Log.d(TAG, "MenuAdapter: method generationMenuList() is create menu list");
                    },throwable -> Log.e(TAG, "MenuAdapter: method generationMenuList() isn't create menu list"));
        }
        this.id = id;
        this.context = context;
    }


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

        if(id == R.id.popup_menu__rv_dishes)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pop_up, viewGroup, false);
        else view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main__menu_item, viewGroup, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int position) {
        menuViewHolder.bind(menuList.get(position));

        //TODO
        menuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                //Initialization intent for creating activity for displaying list dishes
                Intent intent = new Intent(App.getContext(), ListProductsByDishesActivity.class);

                String sDishes = menuList.get(position).getDishes().getTitle();
                intent.putExtra("product", sDishes);
                context.finish();
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static final class MenuViewHolder extends RecyclerView.ViewHolder{
        private final ImageView posterImageView;
        private final TextView nameTextView;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            this.posterImageView = itemView.findViewById(R.id.menu_item__menu_poster);
            this.nameTextView = itemView.findViewById(R.id.menu_item__menu_name);
        }

        private void bind(@NonNull Menu menu){
            posterImageView.setImageResource(menu.getPoster());
            nameTextView.setText(menu.getName());
        }
    }

    private Single<List<Menu>> generationMenuList() {
        return Single.create(emitter -> {
            List<Menu> menus = new ArrayList<>();

            menus.add(new Menu(R.drawable.ic_stock, App.getContext().getResources().getString(R.string.stock), Dishes.STOCK));
            menus.add(new Menu(R.drawable.ic_set, App.getContext().getString(R.string.set), Dishes.SETS));
            menus.add(new Menu(R.drawable.ic_kebab, App.getContext().getString(R.string.kebab), Dishes.KEBAB));
            menus.add(new Menu(R.drawable.ic_lulykebab, App.getContext().getString(R.string.lulykebab), Dishes.LYULYAKEBAB));
            menus.add(new Menu(R.drawable.ic_grill_fish, App.getContext().getString(R.string.grill_fish), Dishes.GRILLED_FISH));
            menus.add(new Menu(R.drawable.ic_beverages, App.getContext().getString(R.string.beverages), Dishes.BEVERAGES));
            menus.add(new Menu(R.drawable.ic_cold_snacks, App.getContext().getString(R.string.cold_snacks), Dishes.COLD_SNACKS));
            menus.add(new Menu(R.drawable.ic_garnish, App.getContext().getString(R.string.garnish), Dishes.GARNISH));
            menus.add(new Menu(R.drawable.ic_grille_vegetables, App.getContext().getString(R.string.grille_vegetables), Dishes.GRILLED_VEGETABLES));
            menus.add(new Menu(R.drawable.ic_hot_snacks, App.getContext().getString(R.string.hot_snacks), Dishes.HOT_SNACK));
            menus.add(new Menu(R.drawable.ic_hachapuri, App.getContext().getString(R.string.hachapuri), Dishes.KHACHAPURI));
            menus.add(new Menu(R.drawable.ic_pickeld_meat, App.getContext().getString(R.string.pickeld_meat), Dishes.PICKLED_MEAT));
            menus.add(new Menu(R.drawable.ic_lavash, App.getContext().getString(R.string.lavash), Dishes.PITA));
            menus.add(new Menu(R.drawable.ic_salad, App.getContext().getString(R.string.salad), Dishes.SALAD));
            menus.add(new Menu(R.drawable.ic_sous, App.getContext().getString(R.string.sous), Dishes.SAUCES));
            menus.add(new Menu(R.drawable.ic_first, App.getContext().getString(R.string.first), Dishes.FIRST_MEAL));

            emitter.onSuccess(menus);
        });
    }


}
