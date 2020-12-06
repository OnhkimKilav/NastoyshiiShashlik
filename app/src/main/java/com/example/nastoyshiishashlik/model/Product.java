package com.example.nastoyshiishashlik.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int poster;
    private String name;
    private int weight;
    private double price;
    @ColumnInfo(name = "min_weight_for_order")
    private int minWeightForOrder;
    @ColumnInfo(name = "final_price")
    private double finalPrice;
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    private Dishes dishes;

    public Product() {
    }

    public Product(int poster, String name, int weight, double price, int minWeightForOrder, double finalPrice, Dishes dishes) {
        this.poster = poster;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.minWeightForOrder = minWeightForOrder;
        this.finalPrice = finalPrice;
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMinWeightForOrder() {
        return minWeightForOrder;
    }

    public void setMinWeightForOrder(int minWeightForOrder) {
        this.minWeightForOrder = minWeightForOrder;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Dishes getDishes() {
        return dishes;
    }

    public void setDishes(Dishes dishes) {
        this.dishes = dishes;
    }
}
