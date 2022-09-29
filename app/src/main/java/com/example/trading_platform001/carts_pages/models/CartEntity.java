package com.example.trading_platform001.carts_pages.models;

import com.example.trading_platform001.interfaces.Saleable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartEntity implements Serializable {
    private static final long serialVersionUID;

    static {
        serialVersionUID = 42L;
    }

    private final Map<Saleable, Integer> cartItemMap = new HashMap<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private int totalQuantity = 0;

    public void add(final Saleable sellable, int quantity) {

        if (cartItemMap.containsKey(sellable)) {
            cartItemMap.put(sellable, cartItemMap.get(sellable) + quantity);
        } else {
            cartItemMap.put(sellable, quantity);
        }
        totalPrice = totalPrice.add(sellable.getPrice().multiply(BigDecimal.valueOf(quantity)));
        totalQuantity += quantity;
    }

    public void update(final Saleable sellable, int quantity) {
        if (cartItemMap.containsKey(sellable) && quantity > 0) {

            int productQuantity = cartItemMap.get(sellable);
            BigDecimal productPrice = sellable.getPrice().multiply(BigDecimal.valueOf(productQuantity));

            cartItemMap.put(sellable, quantity);

            totalQuantity = totalQuantity - productQuantity + quantity;
            totalPrice = totalPrice.subtract(productPrice).add(sellable.getPrice().multiply(BigDecimal.valueOf(quantity)));

        }
    }

    public void remove(final Saleable sellable) {

        if (cartItemMap.containsKey(sellable)) {
            int quantity = cartItemMap.get(sellable);
            cartItemMap.remove(sellable);
            totalPrice = totalPrice.subtract(sellable.getPrice().multiply(BigDecimal.valueOf(quantity)));
            totalQuantity -= quantity;
        }


    }

    public void clear() {
        cartItemMap.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    public int getQuantity(final Saleable sellable) {
        return cartItemMap.containsKey(sellable) ? cartItemMap.get(sellable) : 0;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public Map<Saleable, Integer> getItemWithQuantity() {
        Map<Saleable, Integer> cartItemMap = new HashMap<>();
        cartItemMap.putAll(this.cartItemMap);
        return cartItemMap;
    }

}
