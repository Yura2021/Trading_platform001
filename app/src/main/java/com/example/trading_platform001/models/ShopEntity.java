package com.example.trading_platform001.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Timestamp;

public class ShopEntity implements Parcelable {
    private long id;
    private String name;
    private long user_id;
    private int is_active;
    private String description;
    private float rating;
    private Timestamp created_at;
    private Timestamp updated_at;

    public ShopEntity() {
    }

    public ShopEntity(long id, String name, long user_id, int is_active, String description, float rating, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
        this.is_active = is_active;
        this.description = description;
        this.rating = rating;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected ShopEntity(Parcel in) {
        id = in.readLong();
        name = in.readString();
        user_id = in.readLong();
        is_active = in.readInt();
        description = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<ShopEntity> CREATOR = new Creator<ShopEntity>() {
        @Override
        public ShopEntity createFromParcel(Parcel in) {
            return new ShopEntity(in);
        }

        @Override
        public ShopEntity[] newArray(int size) {
            return new ShopEntity[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(user_id);
        dest.writeInt(is_active);
        dest.writeString(description);
        dest.writeFloat(rating);
    }
}
