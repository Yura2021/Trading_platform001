package com.example.trading_platform001.user_pages.models;

import java.util.ArrayList;

public class Order {
    public ArrayList<ItemOrder> item_order;
    public OrderInformation order;

    public OrderInformation getOrder() {
        return order;
    }

    public ArrayList<ItemOrder> getItemOrder() {
        return item_order;
    }
}
