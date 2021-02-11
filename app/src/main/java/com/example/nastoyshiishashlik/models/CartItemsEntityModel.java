package com.example.nastoyshiishashlik.models;

public class CartItemsEntityModel {
    private ProductEntityModel product;
    private int quantity;

    public ProductEntityModel getProduct() {
        return product;
    }

    public void setProduct(ProductEntityModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
