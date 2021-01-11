package com.example.nastoyshiishashlik;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nastoyshiishashlik.menuBar.MenuAdapter;
import com.example.nastoyshiishashlik.model.Product;

public class SectionMenuActivity extends AppCompatActivity {
    private Product product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Product product = (Product) intent.getParcelableExtra(MenuAdapter.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
    }
}
