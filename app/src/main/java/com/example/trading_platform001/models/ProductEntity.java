package com.example.trading_platform001.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProductEntity{

    private long id;
    private String name;
    private String cover_img;
    private String description;
    private BigDecimal price = BigDecimal.ZERO;
    private float rating;
    private int shop_id;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int favorite;
    private boolean isFavorite;
    public ProductEntity() {
    }

    public ProductEntity(long id, String name, String description, float rating, BigDecimal price,  int shop_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.shop_id = shop_id;
        this.rating = rating;
    }

    public ProductEntity(long id, String name, BigDecimal price, float rating) {

        this.name = name;
        this.id = id;
        this.price = price;
        this.rating = rating;
    }


    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img ;
    }

    private void setFavorite(int favorite) {
        this.isFavorite = Boolean.parseBoolean(String.valueOf(favorite));
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isfavorite) {

        this.isFavorite = isfavorite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
