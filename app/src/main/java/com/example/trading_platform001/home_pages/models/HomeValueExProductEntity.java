package com.example.trading_platform001.home_pages.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.trading_platform001.models.ProductEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class HomeValueExProductEntity extends ProductEntity implements Parcelable {
    String nameShop;
    String nameCategory;
    int idPosition;

    public HomeValueExProductEntity() {
    }

    public HomeValueExProductEntity(ProductEntity product, String nameShop) {
        super(product.getId(), product.getName(), product.getCover_img(), product.getDescription(), product.getPrice(), product.getRating(), product.getShop_id(), product.getCreated_at(), product.getUpdated_at(), product.getFavorite(), product.isFavorite());
        this.nameShop = nameShop;
    }

    public HomeValueExProductEntity(ProductEntity product) {
        super(product.getId(), product.getName(), product.getCover_img(), product.getDescription(), product.getPrice(), product.getRating(), product.getShop_id(), product.getCreated_at(), product.getUpdated_at(), product.getFavorite(), product.isFavorite());
    }

    public HomeValueExProductEntity(long id, String name, String cover_img, String description, BigDecimal price, float rating, long shop_id, Timestamp created_at, Timestamp updated_at, int favorite, boolean isFavorite) {
        super(id, name, cover_img, description, price, rating, shop_id, created_at, updated_at, favorite, isFavorite);
    }

    public HomeValueExProductEntity(long id, String name, String description, float rating, BigDecimal price, int shop_id) {
        super(id, name, description, rating, price, shop_id);
    }

    public HomeValueExProductEntity(long id, String name, BigDecimal price, float rating) {
        super(id, name, price, rating);
    }


    protected HomeValueExProductEntity(Parcel in) {
        super(in);
        nameShop = in.readString();
        nameCategory = in.readString();
        idPosition = in.readInt();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(nameShop);
        dest.writeString(nameCategory);
        dest.writeInt(idPosition);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomeValueExProductEntity> CREATOR = new Creator<HomeValueExProductEntity>() {
        @Override
        public HomeValueExProductEntity createFromParcel(Parcel in) {
            return new HomeValueExProductEntity(in);
        }

        @Override
        public HomeValueExProductEntity[] newArray(int size) {
            return new HomeValueExProductEntity[size];
        }
    };

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public int getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }
}
