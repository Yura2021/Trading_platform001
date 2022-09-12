package com.example.trading_platform001.user_pages.models;

import java.util.ArrayList;

public class OrderList {
    private static ArrayList<Order> orderList;

    public static ArrayList<Order> getOrderList() {
        return orderList;
    }

    public static void setOrderList(ArrayList<Order> orderList) {
        OrderList.orderList = orderList;
    }

}
