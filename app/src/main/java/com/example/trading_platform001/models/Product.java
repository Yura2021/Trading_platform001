package com.example.trading_platform001.models;


public class Product {
    private int id;
    private String name;
    private String description;
    private int Price;
    private double rating;
    private int category_id;
    private int shop_id;
    private int img_id;

    public Product() {}

    public Product(int id, String name, String description,double rating ,int price, int category_id, int shop_id, int img_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        Price = price;
        this.category_id = category_id;
        this.shop_id = shop_id;
        this.img_id = img_id;
        this.rating = rating;
    }

    public Product(String name, int price, double rating,int img_id) {
        this.name = name;
        Price = price;
        this.img_id = img_id;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
