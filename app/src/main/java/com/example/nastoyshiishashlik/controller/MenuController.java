package com.example.nastoyshiishashlik.controller;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.service.MenuAdapter;

public class MenuController {
    public void createMenuBar(RecyclerView recyclerView, int idRecyclerView){
        MenuAdapter menuAdapter = new MenuAdapter(idRecyclerView);
        recyclerView.setAdapter(menuAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(App.getContext());
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }
}
