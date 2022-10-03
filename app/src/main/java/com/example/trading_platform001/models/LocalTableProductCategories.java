package com.example.trading_platform001.models;

import java.util.ArrayList;

public class LocalTableProductCategories {
    private static ArrayList<ProductCategoriesEntity> productCategoriesEntityId;

    public static ArrayList<ProductCategoriesEntity> getProductCategoriesID() {
        if (productCategoriesEntityId == null) {
            productCategoriesEntityId = new ArrayList<>();
        }
        return productCategoriesEntityId;
    }

    public static void setProductCategoriesID(ArrayList<ProductCategoriesEntity> productCategoriesEntityId) {
        LocalTableProductCategories.productCategoriesEntityId = productCategoriesEntityId;
    }

}
