package com.example.trading_platform001.models;

import com.example.trading_platform001.interfaces.Saleable;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Saleable, Serializable {
    private static final long serialVersionUID = -4_073_256_626_483_275_668L;

    private long id;
    private String name;
    private String description;
    private BigDecimal price = BigDecimal.ZERO;
    private float rating;
    private int category_id;
    private int shop_id;
    private int img_id;
    private int quantity;

    public Product() {quantity=1;}

    public Product(long id, String name, String description,float rating ,BigDecimal price, int category_id, int shop_id, int img_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category_id = category_id;
        this.shop_id = shop_id;
        this.img_id = img_id;
        this.rating = rating;
        quantity=1;
    }

    public Product(long id,String name, BigDecimal price, float rating,int img_id) {

        this.name = name;
        this.id = id;
        this.price = price;
        this.img_id = img_id;
        this.rating = rating;
        quantity=1;
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
        hash = hash * prime + (price == null ? 0 : price.hashCode());
        hash = hash * prime + (description == null ? 0 : description.hashCode());

        return hash;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
