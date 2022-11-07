package com.example.trading_platform001.catalog_page.models;

public class Category {
    private int id;
    private int parent_id;
    private int order;
    private String name;
    private String slug;
    private String created_at;
    private String updated_at;
    private  String cover_img;
    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getSlug() {
        return slug;
    }

    public String getCover_img() {
        return cover_img;
    }
}
