package com.example.trading_platform001.user_pages.models;

import java.util.ArrayList;

public class Order {
    private ArrayList<ItemOrder> item_order;
    private OrderInformation order;

    public OrderInformation getOrder() {
        return this.order;
    }

    public ArrayList<ItemOrder> getItemOrder() {
        return this.item_order;
    }
}
