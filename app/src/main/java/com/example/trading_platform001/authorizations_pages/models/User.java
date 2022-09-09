package com.example.trading_platform001.authorizations_pages.models;

import android.annotation.SuppressLint;

public class User {
    private long id;
    private int role_id;
    private String name;
    private String remember_token;

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String email;
    private String avatar;
   /// private Timestamp email_verified_at;
   /// private ArrayList<Object> settings;
   /// private Date created_at;
    //private Date updated_at;

    public User(){}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {this.email = email;}

    public String getEmail() {
        return email;
    }

}
