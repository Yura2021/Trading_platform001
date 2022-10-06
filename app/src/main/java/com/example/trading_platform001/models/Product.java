package com.example.trading_platform001.models;

import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
import com.example.trading_platform001.interfaces.Saleable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product implements Saleable, Serializable {
    private static final long serialVersionUID = -4_073_256_626_483_275_668L;

    private long id;
    private String name;
    private String cover_img;
    private String description;
    private BigDecimal price = BigDecimal.ZERO;
    private float rating;
    private int category_id;
    private long shop_id;
    private int quantity;
    private Timestamp created_at;
    private Timestamp updated_at;
    private boolean favorite;

    public Product(HomeValueExProductEntity product) {
        this.id = product.getId();
        this.name = product.getName();
        this.cover_img = product.getCover_img();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.rating = product.getRating();
        this.shop_id = product.getShop_id();
        this.quantity = 1;
        this.created_at = product.getCreated_at();
        this.updated_at = product.getUpdated_at();
        this.favorite = product.isFavorite();
    }

    public Product() {
        quantity = 1;
    }

    public Product(long id, String name, String description, float rating, BigDecimal price, int category_id, int shop_id, String cover_img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category_id = category_id;
        this.shop_id = shop_id;
        this.rating = rating;
        this.cover_img = cover_img;
        quantity = 1;
    }

    public Product(long id, String name, BigDecimal price, float rating, String cover_img) {

        this.name = name;
        this.id = id;
        this.price = price;
        this.cover_img = cover_img;
        this.rating = rating;
        quantity = 1;
    }

    public Product(long id, String name, BigDecimal price, float rating, String cover_img, String description) {
        this.id = id;
        this.name = name;
        this.cover_img = cover_img;
        this.description = description;
        this.price = price;
        this.rating = rating;
        quantity = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Product)) return false;

        return (this.id == ((Product) o).getId());
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = (int) (hash * prime + id);
        hash = hash * prime + (name == null ? 0 : name.hashCode());
        hash = hash * prime + (description == null ? 0 : description.hashCode());

        return hash;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public long getShop_id() {
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
