package com.example.nastoyshiishashlik.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {

    @PrimaryKey()
    @ColumnInfo(name = "_id")
    private int ID;
    private ProductModel productModel;
    private int count;

    public Order() {
    }

    public Order(int ID, ProductModel productModel, int count) {
        this.ID = ID;
        this.productModel = productModel;
        this.count = count;
    }

    public int getID() {
        return ID;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public int getCount() {
        return count;
    }
}
