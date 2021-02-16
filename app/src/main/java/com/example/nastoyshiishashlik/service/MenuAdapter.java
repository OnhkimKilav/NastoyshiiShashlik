package com.example.nastoyshiishashlik.service;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.dao.roomDao.ProductDBModel;
import com.example.nastoyshiishashlik.models.Dishes;
import com.example.nastoyshiishashlik.models.Menu;

import com.example.nastoyshiishashlik.ui.ListProductsByDishesActivity;


import java.util.ArrayList;
import java.util.List;

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

        menus.add(new Menu(R.drawable.ic_stock, "акции", Dishes.STOCK));
        menus.add(new Menu(R.drawable.ic_set, "сеты", Dishes.SETS));
        menus.add(new Menu(R.drawable.ic_kebab, "шашлык", Dishes.KEBAB));
        menus.add(new Menu(R.drawable.ic_lulykebab, "люля-кебаб", Dishes.LYULYAKEBAB));
        menus.add(new Menu(R.drawable.ic_grill_fish, "рыба на\nмангале", Dishes.GRILLED_FISH));
        menus.add(new Menu(R.drawable.ic_beverages, "напитки", Dishes.BEVERAGES));
        menus.add(new Menu(R.drawable.ic_cold_snacks, "холодные\nзакуски", Dishes.COLD_SNACKS));
        menus.add(new Menu(R.drawable.ic_garnish, "гарниры", Dishes.GARNISH));
        menus.add(new Menu(R.drawable.ic_grille_vegetables, "овощи на\nмангале", Dishes.GRILLED_VEGETABLES));
        menus.add(new Menu(R.drawable.ic_hot_snacks, "горячие\nзакуски", Dishes.HOT_SNACK));
        menus.add(new Menu(R.drawable.ic_hachapuri, "хачапури", Dishes.KHACHAPURI));
        menus.add(new Menu(R.drawable.ic_pickeld_meat, "маринованное\nмясо", Dishes.PICKLED_MEAT));
        menus.add(new Menu(R.drawable.ic_lavash, "лаваш", Dishes.PITA));
        menus.add(new Menu(R.drawable.ic_salad, "салаты", Dishes.SALAD));
        menus.add(new Menu(R.drawable.ic_sous, "соусы", Dishes.SAUCES));
        menus.add(new Menu(R.drawable.ic_first, "первые\nблюда", Dishes.FIRST_MEAL));

        return menus;
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
                App.getContext().startActivity(intent);
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
            posterImageView.setImageResource(menu.getPoster());
            nameTextView.setText(menu.getName());
        }
    }
}
