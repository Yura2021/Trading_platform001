package com.example.trading_platform001.products_pages;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.CartFragment;
import com.example.trading_platform001.carts_pages.models.CartEntity;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.models.Product;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class ShowProductFullscreenFragment extends Fragment {
    @BindView(R.id.imgProduct)
    ImageView imgProduct;
    @BindView(R.id.tvNameProduct)
    TextView tvNameProduct;
    @BindView(R.id.tvPriceProduct)
    TextView tvPriceProduct;
    @BindView(R.id.tvIdProduct)
    TextView tvIdProduct;
    @BindView(R.id.rbRating)
    RatingBar rbRating;
    @BindView(R.id.btnAddCart)
    Button addCart;
    private long id;
    private String url_imgProduct;
    private String nameProduct, priceProduct;
    private float rating;
    View view;

    private void getResultFragment() {
        Bundle result = getArguments();
        if (result != null) {

            id = result.getLong("id", -1);
            if (id != -1) {
                nameProduct = result.getString("tvNameProduct", "No name");
                priceProduct = result.getString("tvPriceProduct");
                rating = result.getFloat("rbRating", 0f);
                url_imgProduct = result.getString("imgProduct", "no image");
                imgProduct.setImageURI(Uri.parse(url_imgProduct));
                Picasso.get().load(Uri.parse(url_imgProduct)).into(imgProduct);
                tvNameProduct.setText(nameProduct);
                tvPriceProduct.setText(String.valueOf(priceProduct));
                rbRating.setRating(rating);
                tvIdProduct.setText(String.valueOf(id));
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_show_product_fullscreen, container, false);
        ButterKnife.bind(this, view);
        getResultFragment();

        addCart.setOnClickListener(v -> addCartItem());
        return view;
    }

    private void addCartItem() {
        Bundle bundle = new Bundle();
        if (id != -1) {
            bundle.putLong("id", id);
            bundle.putString("url_imgProduct", url_imgProduct);
            bundle.putString("tvNameProduct", nameProduct);
            bundle.putString("tvPriceProduct", priceProduct);
            bundle.putFloat("rbRating", rating);
            CartEntity cart = CartHelper.getCart();
            BigDecimal dec = new BigDecimal(priceProduct);
            Product product = new Product(id, nameProduct, dec, rating, url_imgProduct);
            cart.add(product, 1);

            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.cart);
            badge.setVisible(true);
            badge.setNumber(CartHelper.getCartItems().size());

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.layout_view_fragment, CartFragment.class, bundle, "cart")
                    .setReorderingAllowed(true)
                    .addToBackStack("replacement")
                    .commit();

        }
    }
}