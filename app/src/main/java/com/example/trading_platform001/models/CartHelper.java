package com.example.trading_platform001.models;

import com.example.trading_platform001.interfaces.Saleable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartHelper {
    private static CartEntity cartEntity = new CartEntity();

    public static CartEntity getCart() {
        if (cartEntity == null) {
            cartEntity = new CartEntity();
        }

        return cartEntity;
    }

    public static List<CartItemsEntityModel> getCartItems() {
        List<CartItemsEntityModel> cartItems = new ArrayList<>();
        Map<Saleable, Integer> itemMap = getCart().getItemWithQuantity();

        for (Map.Entry<Saleable, Integer> entry : itemMap.entrySet()) {
            CartItemsEntityModel cartItem = new CartItemsEntityModel();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }

        return cartItems;
    }

}
