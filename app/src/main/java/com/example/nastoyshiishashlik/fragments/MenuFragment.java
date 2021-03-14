package com.example.nastoyshiishashlik.fragments;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.adapters.MenuAdapter;

import butterknife.BindView;

public class MenuFragment extends BaseFragment {
    @BindView(R.id.fragment_menu_recycler_view)
    RecyclerView recyclerView;

    private MenuAdapter menuAdapter;

    @Override
    public int getViewId() {
        return R.layout.fragment_menu;
    }

    @Override
    public void onViewCreated(View view) {
        menuAdapter = new MenuAdapter(R.id.fragment_menu_recycler_view, context);
        recyclerView.setAdapter(menuAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onStop() {
        menuAdapter = null;
        super.onStop();
    }
}
