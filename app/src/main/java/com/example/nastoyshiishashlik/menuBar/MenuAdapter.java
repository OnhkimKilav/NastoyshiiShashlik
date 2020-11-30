package com.example.nastoyshiishashlik.menuBar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.model.Menu;
import com.example.nastoyshiishashlik.optimization.OptimizationImageBitmap;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private final List<Menu> menus;

    public MenuAdapter(List<Menu> menus) {
        this.menus = menus;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main__menu_item, viewGroup, false);
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
