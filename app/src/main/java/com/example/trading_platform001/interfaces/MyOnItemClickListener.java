package com.example.trading_platform001.interfaces;

import com.example.trading_platform001.carts_pages.models.CartItemsEntityModel;

import java.util.List;

public interface MyOnItemClickListener {
    void onItemClick(CartItemsEntityModel cartItemsEntityModel);
    void onItemPlusClicked(int position,  List<CartItemsEntityModel> carts);

    void onItemMinusClicked(int position,  List<CartItemsEntityModel> carts);

    void onUpdateList();
}
