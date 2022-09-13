package com.example.trading_platform001.user_pages.models;

public class ItemOrder {
    private final int id;
    private final int order_id;
    private final int product_id;
    private final int price;
    private final int quantity;
    private final String created_at;
    private final String updated_at;
    private final String name;
    private final String description;
    private final String cover_img;
    private final int shop_id;

    public ItemOrder(int id, int order_id, int product_id, int price, int quantity, String created_at, String updated_at, String name, String description, String cover_img, int shop_id) {
        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.price = price;
        this.quantity = quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.name = name;
        this.description = description;
        this.cover_img = cover_img;
        this.shop_id = shop_id;
    }

    public String getCover_img() {
        return this.cover_img;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getShop_id() {
        return shop_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdated_at() {
        return updated_at;
    }

}
