package com.example.trading_platform001.models;

import java.util.ArrayList;

public class LocalProducts {
    private static ArrayList<ProductEntity> products;

    public static ArrayList<ProductEntity> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public static boolean isNull() {
        return products == null;
    }

    public static void setProducts(ArrayList<ProductEntity> products) {
        LocalProducts.products = products;
    }


}
