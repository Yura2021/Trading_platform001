package com.example.trading_platform001.user_pages.models;

import java.io.Serializable;
import java.util.Date;

public class OrderInformation implements Serializable {
    private int id;
    private String order_number;
    private int user_id;
    private String status;
    private int grand_total;
    private int item_count;
    private int is_paid;
    private String payment_method;
    private String shipping_fullname;
    private String shipping_address;
    private String shipping_city;
    private String shipping_state;
    private String shipping_zipcode;
    private String shipping_phone;
    private Object notes;
    private String billing_fullname;
    private String billing_address;
    private String billing_city;
    private String billing_state;
    private String billing_zipcode;
    private String billing_phone;
    private Date created_at;
    private Date updated_at;

    public OrderInformation(String order_number, int grand_total, Date created_at, String status){
        this.order_number=order_number;
        this.grand_total=grand_total;
        this.created_at=created_at;
        this.status=status;
    }

    public String getOrder_number() {
        return this.order_number;
    }

    public int getGrand_total() {
        return this.grand_total;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public String getStatus() {
        return this.status;
    }

    public String getPayment_method() {
        return payment_method;
    }
    public String getAddress()
    {
        return this.shipping_state+","+shipping_city+","+shipping_address;
    }

    public String getShipping_fullname() {
        return shipping_fullname;
    }

    public String getShipping_phone() {
        return shipping_phone;
    }
}
