package com.example.trading_platform001.models;

import java.util.ArrayList;

public class LocalNewProducts {
    private static ArrayList<ProductEntity> newproducts;

    public static ArrayList<ProductEntity> getNewProducts() {
        if (newproducts == null) {
            newproducts = new ArrayList<>();
        }
        return newproducts;
    }

    public static boolean isNull() {
        return newproducts == null;
    }

    public static void setNewProducts(ArrayList<ProductEntity> products) {
        LocalNewProducts.newproducts = products;
    }


}
