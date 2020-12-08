package com.example.nastoyshiishashlik.menuBar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private final List<Menu> menus;
    private final int id;

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
            posterImageView.setImageResource(menu.getPoster());
            nameTextView.setText(menu.getName());
        }
    }
}
