package com.example.trading_platform001.carts_pages.models;

import com.example.trading_platform001.models.Product;

public class CartItemsEntityModel {
    private Product product;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
