package com.example.trading_platform001.user_pages.models;

import java.util.ArrayList;

public class OrderList {

    public boolean status;
    public String message;
    public ArrayList<Order> orders;

    public OrderList(Boolean status,String message,ArrayList<Order> orders)
    {
        this.orders=orders;
        this.message=message;
        this.status=status;
    }

    public ArrayList<OrderInformation> GetAllOrderInformation()
    {
        ArrayList<OrderInformation> orderInformations = new ArrayList<>();
        for(int i = 0 ; i <this.orders.size();i++)
        {
            orderInformations.add(this.orders.get(i).getOrder());
        }
        return orderInformations;
    }
    public ArrayList<ArrayList<ItemOrder>> GetAllItemOrder()
    {
        ArrayList<ArrayList<ItemOrder>> ItemOrder = new ArrayList<>();
        for(int i = 0 ; i <this.orders.size();i++)
        {
            ItemOrder.add(this.orders.get(i).getItemOrder());
        }
        return ItemOrder;
    }

}
