package com.example.trading_platform001.models;

import com.example.trading_platform001.catalog_page.models.Category;

import java.util.ArrayList;

public class LocalCategory {
    private static ArrayList<Category> categories;

    public static ArrayList<Category> getCategory() {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        return categories;
    }

    public static boolean isNull() {
        return categories == null;
    }

    public static void setCategory(ArrayList<Category> categories) {
        LocalCategory.categories = categories;
    }

}
