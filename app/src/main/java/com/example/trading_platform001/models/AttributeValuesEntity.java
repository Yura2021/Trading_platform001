package com.example.trading_platform001.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Timestamp;


public class AttributeValuesEntity implements Parcelable {

    private long id;
    private long attribute_id;
    private String value;
    private Timestamp created_at;
    private Timestamp updated_at;
    public AttributeValuesEntity() {
    }

    public AttributeValuesEntity(long id, long attribute_id, String value) {
        this.id = id;
        this.attribute_id = attribute_id;
        this.value = value;
    }

    public AttributeValuesEntity(long id, long attribute_id, String value, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.attribute_id = attribute_id;
        this.value = value;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected AttributeValuesEntity(Parcel in) {
        id = in.readLong();
        attribute_id = in.readLong();
        value = in.readString();
        created_at = Timestamp.valueOf(in.readString());
        updated_at = Timestamp.valueOf(in.readString());

    }

    public static final Creator<AttributeValuesEntity> CREATOR = new Creator<AttributeValuesEntity>() {
        @Override
        public AttributeValuesEntity createFromParcel(Parcel in) {
            return new AttributeValuesEntity(in);
        }

        @Override
        public AttributeValuesEntity[] newArray(int size) {
            return new AttributeValuesEntity[size];
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

    public long getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(long attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(attribute_id);
        dest.writeString(value);
        dest.writeString(String.valueOf(created_at));
        dest.writeString(String.valueOf(updated_at));
    }
}
