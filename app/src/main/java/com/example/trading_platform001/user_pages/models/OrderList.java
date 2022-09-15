package com.example.trading_platform001.user_pages.models;

import java.util.ArrayList;

public class OrderList  {

    private boolean status;
    private String message;
    private ArrayList<Order> orders;
    public  OrderList(){
        this.orders=new ArrayList<>();
    }

    public OrderList(Boolean status,String message,ArrayList<Order> orders)
    {
        this.orders=orders;
        this.message=message;
        this.status=status;
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

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void add(Order order)
    {
        this.orders.add(order);
    }
}
