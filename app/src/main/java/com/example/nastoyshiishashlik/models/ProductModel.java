package com.example.nastoyshiishashlik.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.nastoyshiishashlik.Saleable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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
/*
    public ProductModel(int ID, int poster, String name, double weight, int price, double minWeightForOrder,
                        int finalPrice, Dishes dishes, boolean hit, String description, String dishesName,
                        String sentences, int quantity) {
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
        this.quantity = quantity;
    }

    public ProductModel(int poster, String name, double weight, int price, double minWeightForOrder,
                        int finalPrice, Dishes dishes, boolean hit, String description, String dishesName,
                        String sentences, int quantity) {
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
        this.quantity = quantity;
    }*/

    /*protected ProductModel(Parcel in) {
        ID = in.readInt();
        poster = in.readInt();
        name = in.readString();
        weight = in.readDouble();
        price = in.readInt();
        minWeightForOrder = in.readDouble();
        finalPrice = in.readInt();
        hit = in.readByte() != 0;
        description = in.readString();
        dishesName = in.readString();
        sentences = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
*/
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

   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(poster);
        dest.writeString(name);
        dest.writeDouble(weight);
        dest.writeInt(price);
        dest.writeDouble(minWeightForOrder);
        dest.writeInt(finalPrice);
        dest.writeString(dishes.getTitle());
        dest.writeInt(hit ? 1 : 0);
        dest.writeString(description);
        dest.writeString(dishesName);
        dest.writeString(sentences);
        dest.writeInt(quantity);
    }*/
}

