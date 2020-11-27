package com.example.nastoyshiishashlik.menuBar;

import android.graphics.Bitmap;

public class Menu {
    private final Bitmap bitmap;
    private final String name;

    public Menu(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public Bitmap getPoster() {
        return bitmap;
    }

    public String getName() {
        return name;
    }
}
