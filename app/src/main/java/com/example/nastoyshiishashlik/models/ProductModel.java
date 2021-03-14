package com.example.nastoyshiishashlik.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "products")
public class ProductModel implements Serializable {

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
    private int quantity;

    public ProductModel() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

