package com.example.trading_platform001.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class User {
    private long id;
    private int role_id;
    private String name;
    private String email;
    private String avatar;
    private Timestamp email_verified_at;
    private ArrayList<Object> settings;
    private Date created_at;
    private Date updated_at;

    public User() {
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
