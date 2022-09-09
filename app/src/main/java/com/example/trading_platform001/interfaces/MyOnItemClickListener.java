package com.example.trading_platform001.interfaces;

import com.example.trading_platform001.carts_pages.models.CartItemsEntityModel;

public interface MyOnItemClickListener {
    void onItemClick(CartItemsEntityModel cartItemsEntityModel);

    void onItemPlusClicked(int position, CartItemsEntityModel cartItemsEntityModel);

    void onItemMinusClicked(int position, CartItemsEntityModel cartItemsEntityModel);

    void onUpdateList();
}
