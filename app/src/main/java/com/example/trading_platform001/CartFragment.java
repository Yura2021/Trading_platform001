package com.example.trading_platform001;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trading_platform001.models.CartHelper;
import com.example.trading_platform001.models.CartItemsEntityModel;
import com.example.trading_platform001.models.CartRecyclerAdapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.math.BigDecimal;


public class CartFragment extends Fragment implements CartRecyclerAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private Button btnBuy;
    private long id;
    private int id_imgProduct;
    private BigDecimal priceProduct;
    private String nameProduct;
    private float rating;
    public Activity context;
    private CartRecyclerAdapter productRecyclerAdapter;
    TextView tvSum;
    BottomNavigationView bottomNavigationView;

    private void getResultFragment() {
        Bundle result = getArguments();
        if (result != null) {

            id = result.getLong("id", -1);
            if (id != -1) {
                nameProduct = result.getString("tvNameProduct", "No name");
                priceProduct = new BigDecimal(result.getString("tvPriceProduct"));
                rating = result.getFloat("rbRating", 0f);
                id_imgProduct = result.getInt("id_imgProduct", -1);

            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        tvSum = view.findViewById(R.id.tvSum);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        btnBuy = view.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartHelper.getCartItems().size() > 0) {
                    Toast.makeText(context, "Total Quantity::  " + CartHelper.getCart().getTotalQuantity() + " Price:: " + CartHelper.getCart().getTotalPrice(), Toast.LENGTH_LONG).show();
                    CartHelper.getCart().clear();
                    BadgeDrawable badgeDrawable = bottomNavigationView.getBadge(R.id.cart);
                    if (badgeDrawable != null) {
                        badgeDrawable.setVisible(false);
                        badgeDrawable.clearNumber();
                    }
                    onUpdateList();
                }
            }
        });
        getResultFragment();
        onUpdateList();
        return view;
    }

    @Override
    public void onItemClick(CartItemsEntityModel cartItemsEntityModel) {

    }

    @Override
    public void onItemPlusClicked(int position, CartItemsEntityModel cartItemsEntityModel) {
        int quantity = cartItemsEntityModel.getQuantity();
        CartItemsEntityModel cartModel = new CartItemsEntityModel();
        cartModel.setProduct(cartItemsEntityModel.getProduct());
        quantity++;
        cartModel.setQuantity(quantity);
        productRecyclerAdapter.updateItem(position, cartModel);
        tvSum.setText("Sum: " + CartHelper.getCart().getTotalPrice());
    }

    @Override
    public void onItemMinusClicked(int position, CartItemsEntityModel cartItemsEntityModel) {
        int quantity = cartItemsEntityModel.getQuantity();
        CartItemsEntityModel cartModel = new CartItemsEntityModel();
        cartModel.setProduct(cartItemsEntityModel.getProduct());
        quantity--;
        cartModel.setQuantity(quantity);
        productRecyclerAdapter.updateItem(position, cartModel);
        tvSum.setText("Sum: " + CartHelper.getCart().getTotalPrice());
    }

    @Override
    public void onUpdateList() {
        productRecyclerAdapter = new CartRecyclerAdapter(context, CartHelper.getCartItems());
        productRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(productRecyclerAdapter);
        tvSum.setText("Sum: " + CartHelper.getCart().getTotalPrice());
        int size = CartHelper.getCartItems().size();
        if (size > 0) {
            bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.cart);
            badge.setVisible(true);
            badge.setNumber(size);
        }

    }
}