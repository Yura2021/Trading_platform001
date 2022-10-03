package com.example.trading_platform001.models;

import java.sql.Timestamp;

public class ProductCategoriesEntity {
    private int id;
    private int product_id;
    private int category_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    public ProductCategoriesEntity() {

    }

    public ProductCategoriesEntity(int id, int product_id, int category_id, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.product_id = product_id;
        this.category_id = category_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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
}
