package com.example.nastoyshiishashlik.sliderBar;

import android.graphics.Bitmap;

public class SliderItem {
    private final Bitmap poster;

    public SliderItem(Bitmap poster) {
        this.poster = poster;
    }

    public Bitmap getPoster() {
        return poster;
    }
}
