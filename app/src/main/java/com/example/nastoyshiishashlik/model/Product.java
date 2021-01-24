package com.example.nastoyshiishashlik.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "products")
public class Product implements Serializable {
    @PrimaryKey()
    @ColumnInfo(name = "_id")
    private int ID;
    private int poster;
    private String name;
    private double weight;
    private int price;
    @ColumnInfo(name = "min_weight_for_order")
    private double minWeightForOrder;
    @ColumnInfo(name = "final_price")
    private int finalPrice;
    private Dishes dishes;
    private boolean hit;
    private String description;
    @ColumnInfo(name = "dishes_name")
    private String dishesName;
    @ColumnInfo(name = "sentences")
    private String sentences;

    public Product() {
    }

    public Product(int ID, int poster, String name, double weight, int price, double minWeightForOrder,
                   int finalPrice, Dishes dishes, boolean hit, String description, String dishesName,
                   String sentences) {
        this.ID = ID;
        this.poster = poster;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.minWeightForOrder = minWeightForOrder;
        this.finalPrice = finalPrice;
        this.dishes = dishes;
        this.hit = hit;
        this.description = description;
        this.dishesName = dishesName;
        this.sentences = sentences;
    }

    public Product(int poster, String name, double weight, int price, double minWeightForOrder,
                   int finalPrice, Dishes dishes, boolean hit, String description, String dishesName,
                   String sentences) {
        this.poster = poster;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.minWeightForOrder = minWeightForOrder;
        this.finalPrice = finalPrice;
        this.dishes = dishes;
        this.hit = hit;
        this.description = description;
        this.dishesName = dishesName;
        this.sentences = sentences;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getMinWeightForOrder() {
        return minWeightForOrder;
    }

    public void setMinWeightForOrder(double minWeightForOrder) {
        this.minWeightForOrder = minWeightForOrder;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public String getSentences() {
        return sentences;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return ID == product.ID &&
                poster == product.poster &&
                Double.compare(product.weight, weight) == 0 &&
                price == product.price &&
                Double.compare(product.minWeightForOrder, minWeightForOrder) == 0 &&
                finalPrice == product.finalPrice &&
                hit == product.hit &&
                Objects.equals(name, product.name) &&
                dishes == product.dishes &&
                Objects.equals(description, product.description) &&
                Objects.equals(dishesName, product.dishesName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, poster, name, weight, price, minWeightForOrder, finalPrice, dishes, hit, description, dishesName);
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID=" + ID +
                ", poster=" + poster +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", minWeightForOrder=" + minWeightForOrder +
                ", finalPrice=" + finalPrice +
                ", dishes=" + dishes +
                ", hit=" + hit +
                ", description='" + description + '\'' +
                ", dishesName='" + dishesName + '\'' +
                ", sentences='" + sentences + '\'' +
                '}';
    }
}

