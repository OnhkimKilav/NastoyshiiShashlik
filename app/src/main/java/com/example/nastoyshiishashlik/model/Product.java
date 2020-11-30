package com.example.nastoyshiishashlik.model;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final int poster;
    private final String name;
    private final int weight;
    private final double price;
    private final int minWeightForOrder;
    private final double finalPrice;

    public Product(int poster, String name, int weight, double price, int minWeightForOrder, double finalPrice) {
        this.poster = poster;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.minWeightForOrder = minWeightForOrder;
        this.finalPrice = finalPrice;
    }

    public int getPoster() {
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
