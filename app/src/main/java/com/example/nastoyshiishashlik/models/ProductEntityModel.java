package com.example.nastoyshiishashlik.models;

import com.example.nastoyshiishashlik.Saleable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ProductEntityModel implements Saleable, Serializable {
    private static final long serialVersionUID = -4073256626483275668L;

    private int id;
    private int image;
    private String name;
    private BigDecimal finalPrice;
    private int price;
    private int quantity;
    private double weight;
    private double minWeightForOrder;

    public ProductEntityModel() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntityModel that = (ProductEntityModel) o;
        return id == that.id &&
                image == that.image &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMinWeightForOrder() {
        return minWeightForOrder;
    }

    public void setMinWeightForOrder(double minWeightForOrder) {
        this.minWeightForOrder = minWeightForOrder;
    }
}
