package com.example.trading_platform001.models;

import java.util.ArrayList;
import java.util.List;

public class LocalShops  {
    private static List<ShopEntity> shops;

    public static List<ShopEntity> getShops() {
        if (shops == null) {
            shops = new ArrayList<>();
        }
        return shops;
    }

    public static boolean isNull() {
        return shops == null;
    }

    public static void setShops(List<ShopEntity> shops) {
        LocalShops.shops = shops;
    }


}
