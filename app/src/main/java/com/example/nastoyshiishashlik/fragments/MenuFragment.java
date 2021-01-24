package com.example.nastoyshiishashlik.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.controller.MenuController;

public class MenuFragment extends Fragment {
    private MenuController menuController;
    private RecyclerView recyclerView;

    public MenuFragment() {
        super(R.layout.fragment_menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_menu_recycler_view);

        menuController = new MenuController();
        menuController.createMenuBar(recyclerView, R.id.fragment_menu_recycler_view);
    }
}
