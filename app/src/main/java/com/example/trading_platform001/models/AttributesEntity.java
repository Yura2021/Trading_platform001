package com.example.trading_platform001.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.sql.Timestamp;


public class AttributesEntity implements Parcelable {

    private long id;
    private String name;
    private Timestamp created_at;
    private Timestamp updated_at;
    public AttributesEntity() {
    }

    public AttributesEntity(long id, String name, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;

    }

    public AttributesEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }


    protected AttributesEntity(Parcel in) {
        id = in.readLong();
        name = in.readString();
        created_at = Timestamp.valueOf(in.readString());
        updated_at = Timestamp.valueOf(in.readString());

    }

    public static final Creator<AttributesEntity> CREATOR = new Creator<AttributesEntity>() {
        @Override
        public AttributesEntity createFromParcel(Parcel in) {
            return new AttributesEntity(in);
        }

        @Override
        public AttributesEntity[] newArray(int size) {
            return new AttributesEntity[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(String.valueOf(created_at));
        dest.writeString(String.valueOf(updated_at));
    }
}
