package com.example.nastoyshiishashlik.menuBar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.SectionMenuActivity;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.dao.roomDao.RoomDB;
import com.example.nastoyshiishashlik.model.Menu;
import com.example.nastoyshiishashlik.model.Product;
import com.example.nastoyshiishashlik.optimization.OptimizationImageBitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    public static final String EXTRA_MESSAGE = "com.example.nastoyshiishashlik.menuBar.MenuAdapter";
    private final List<Menu> menus;
    private final int id;
    private RoomDB database;

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
                //Initialize DB
                database = RoomDB.getInstance(App.getContext());
                //Initialization intent for creating activity for displaying list dishes
                Intent intent = new Intent(App.getContext(), SectionMenuActivity.class);
                //Get dishes of the product you have clicked on
                String sDishes = menus.get(position).getDishes().getTitle();

                //Get all products from DB where dishes equals our product
                ArrayList<Product> products = (ArrayList<Product>) database.mainDao().getByDishes(sDishes);
                intent.putExtra(EXTRA_MESSAGE, products);

                database.close();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menus.size();
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
            OptimizationImageBitmap optimizationImageBitmap = new OptimizationImageBitmap();
            optimizationImageBitmap.execute(menu.getPoster(), 60, 60);

            try {
                posterImageView.setImageBitmap(optimizationImageBitmap.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            nameTextView.setText(menu.getName());
        }
    }
}
