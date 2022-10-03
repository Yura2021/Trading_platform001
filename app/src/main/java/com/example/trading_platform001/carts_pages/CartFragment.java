package com.example.trading_platform001.carts_pages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.carts_pages.models.CartItemsEntityModel;
import com.example.trading_platform001.adapters.CartRecyclerAdapter;
import com.example.trading_platform001.interfaces.MyOnItemClickListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class CartFragment extends Fragment implements MyOnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnOrder)
    Button btnOrder;
    long id;
    int id_imgProduct;
    BigDecimal priceProduct;
    String nameProduct;
    float rating;
    public Activity context;
    private CartRecyclerAdapter productRecyclerAdapter;
    @BindView(R.id.tvSum)
    TextView tvSum;
    View view;
    BadgeDrawable badgeDrawable;
    BottomNavigationView btnNavView;

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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        btnOrder.setOnClickListener(v -> redirectOrders());
        btnNavView = requireActivity().findViewById(R.id.bottomNavigationView);
        getResultFragment();
        onUpdateList();
        return view;
    }

    private void redirectOrders() {
        if (CartHelper.getCartItems().size() > 0) {
            Intent intent = new Intent(getContext(), OrderActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(CartItemsEntityModel cartItemsEntityModel) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemPlusClicked(int position, List<CartItemsEntityModel> carts) {
        if (carts != null && position != -1 && position < carts.size()) {
            CartItemsEntityModel cartModel = new CartItemsEntityModel();
            cartModel.setProduct(carts.get(position).getProduct());
            cartModel.setQuantity(carts.get(position).getQuantity() + 1);
            productRecyclerAdapter.updateItem(position, cartModel);
            tvSum.setText("Sum: " + CartHelper.getCart().getTotalPrice());
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemMinusClicked(int position, List<CartItemsEntityModel> carts) {
        if (carts != null && position != -1 && position < carts.size()) {
            CartItemsEntityModel cartModel = new CartItemsEntityModel();
            cartModel.setProduct(carts.get(position).getProduct());
            cartModel.setQuantity(carts.get(position).getQuantity() - 1);
            productRecyclerAdapter.updateItem(position, cartModel);
            tvSum.setText("Sum: " + CartHelper.getCart().getTotalPrice());
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onUpdateList() {
        productRecyclerAdapter = new CartRecyclerAdapter(CartHelper.getCartItems());
        productRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(productRecyclerAdapter);
        tvSum.setText("Sum: " + CartHelper.getCart().getTotalPrice());
        int size = CartHelper.getCartItems().size();
        if (badgeDrawable == null)
            badgeDrawable = btnNavView.getOrCreateBadge(R.id.cart);
        if (size > 0) {
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(size);
        }else {
            badgeDrawable.setVisible(false);
            badgeDrawable.clearNumber();

        }

    }
}