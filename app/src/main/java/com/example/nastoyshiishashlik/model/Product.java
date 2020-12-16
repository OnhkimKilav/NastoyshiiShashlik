package com.example.nastoyshiishashlik.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Product {
    @PrimaryKey
    private int id;
    private int poster;
    private String name;
    private int weight;
    private double price;
    @ColumnInfo(name = "min_weight_for_order")
    private int minWeightForOrder;
    @ColumnInfo(name = "final_price")
    private double finalPrice;
    @TypeConverters({DishesConverter.class})
    private Dishes dishes;
    private boolean hit;

    public Product() {
    }

    public Product(int id, int poster, String name, int weight, double price, int minWeightForOrder,
                   double finalPrice, Dishes dishes, boolean hit) {
        this.id = id;
        this.poster = poster;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.minWeightForOrder = minWeightForOrder;
        this.finalPrice = finalPrice;
        this.dishes = dishes;
        this.hit = hit;
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

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", poster=" + poster +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", minWeightForOrder=" + minWeightForOrder +
                ", finalPrice=" + finalPrice +
                ", dishes=" + dishes +
                ", hit=" + hit +
                '}';
    }
}
