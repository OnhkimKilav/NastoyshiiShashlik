package com.example.nastoyshiishashlik.hitsProductsBar;

import android.graphics.Bitmap;

public class Product {
    private final Bitmap poster;
    private final String name;
    private final int weight;
    private final double price;
    private final int minWeightForOrder;
    private final double finalPrice;

    public Product(Bitmap poster, String name, int weight, double price, int minWeightForOrder, double finalPrice) {
        this.poster = poster;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.minWeightForOrder = minWeightForOrder;
        this.finalPrice = finalPrice;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public int getMinWeightForOrder() {
        return minWeightForOrder;
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}
