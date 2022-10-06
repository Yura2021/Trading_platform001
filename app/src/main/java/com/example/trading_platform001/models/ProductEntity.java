package com.example.trading_platform001.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

public class ProductEntity implements Parcelable {

    private long id;
    private String name;
    private String cover_img;
    private String description;
    private BigDecimal price = BigDecimal.ZERO;
    private float rating;
    private long shop_id;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int favorite;
    private boolean isFavorite;
    public ProductEntity() {
    }

    public ProductEntity(long id, String name, String cover_img, String description, BigDecimal price, float rating, long shop_id, Timestamp created_at, Timestamp updated_at, int favorite, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.cover_img = cover_img;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.shop_id = shop_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.favorite = favorite;
        this.isFavorite = isFavorite;
    }

    public ProductEntity(long id, String name, String description, float rating, BigDecimal price, int shop_id) {
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


    protected ProductEntity(Parcel in) {
        id = in.readLong();
        name = in.readString();
        cover_img = in.readString();
        description = in.readString();
        price = BigDecimal.valueOf(Integer.parseInt(in.readString()));
        rating = in.readFloat();
        shop_id = in.readLong();
        created_at = Timestamp.valueOf(in.readString());
        updated_at = Timestamp.valueOf(in.readString());
        favorite = in.readInt();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<ProductEntity> CREATOR = new Creator<ProductEntity>() {
        @Override
        public ProductEntity createFromParcel(Parcel in) {
            return new ProductEntity(in);
        }

        @Override
        public ProductEntity[] newArray(int size) {
            return new ProductEntity[size];
        }
    };

    public Timestamp getCreated_at() {
        return created_at;
    }


    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    public int getFavorite() {
        return favorite;
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
        this.cover_img = cover_img;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(cover_img);
        dest.writeString(description);
        dest.writeString(String.valueOf(price));
        dest.writeFloat(rating);
        dest.writeLong(shop_id);
        dest.writeString(String.valueOf(created_at));
        dest.writeString(String.valueOf(updated_at));
        dest.writeInt(favorite);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
