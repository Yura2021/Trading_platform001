package com.example.trading_platform001.home_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.ProductAdapter;
import com.example.trading_platform001.carts_pages.models.CartEntity;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.LocalShops;
import com.example.trading_platform001.models.Product;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.models.ShopEntity;
import com.example.trading_platform001.products_pages.DetailsProductActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends Fragment {

    @Nullable
    @BindView(R.id.svSearch1)
    SearchView searchView;
    @BindView(R.id.grid_product)
    GridView gridview;
    ProductAdapter productAdapter;
    BottomNavigationView btnNavView;
    View view;
    ImageView imgProduct;
    TextView tvNameProduct, tvPriceProduct;
    RatingBar rbRating;
    BadgeDrawable badgeDrawable;
    @BindView(R.id.image_slider)
    ImageSlider imageSlider;

    ImageView ibAddCart;
    private long itemId;
    private String url_imgProduct, description;
    private String nameProduct, nameShop, priceProduct;
    private float rating;
    boolean favorite;
    int productPosition;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        searchView.clearFocus();
        if (productAdapter == null)
            productAdapter = new ProductAdapter(view.getContext(), LocalProducts.getProducts());

        btnNavView = requireActivity().findViewById(R.id.bottomNavigationView);
        gridview.setOnItemClickListener(gridviewOnItemClickListener);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return true;
            }
        });
        int size = CartHelper.getCartItems().size();
        if (badgeDrawable == null)
            badgeDrawable = btnNavView.getOrCreateBadge(R.id.cart);
        if (size > 0) {
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(size);
        } else {
            badgeDrawable.setVisible(false);
            badgeDrawable.clearNumber();

        }
        gridview.setAdapter(productAdapter);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        for (ProductEntity item : LocalProducts.getProducts()) {
            imageList.add(new SlideModel(item.getCover_img(), ScaleTypes.FIT));
        }

        imageSlider.setImageList(imageList, ScaleTypes.FIT);
        return view;
    }


    private void addCartItem() {

        CartEntity cart = CartHelper.getCart();
        BigDecimal dec = new BigDecimal(priceProduct);
        Product product = new Product(itemId, nameProduct, dec, rating, url_imgProduct);
        cart.add(product, 1);
        Toast.makeText(this.getContext(), "Товар доданий у кошик", Toast.LENGTH_SHORT).show();


    }

    private final GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {


        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            imgProduct = v.findViewById(R.id.imgProduct);
            tvNameProduct = v.findViewById(R.id.tvNameProduct);
            tvPriceProduct = v.findViewById(R.id.tvPriceProduct);
            rbRating = v.findViewById(R.id.rbRating);
            Intent intent = new Intent(HomeFragment.this.getContext(), DetailsProductActivity.class);

            itemId = productAdapter.getItem(position).getId();
            productPosition = position;
            favorite = LocalProducts.getProducts().get(productPosition).isFavorite();
            nameProduct = tvNameProduct.getText().toString();
            priceProduct = tvPriceProduct.getText().toString();
            rating = rbRating.getRating();
            url_imgProduct = productAdapter.getItem(position).getCover_img();
            description = productAdapter.getItem(position).getDescription();
            description = productAdapter.getItem(position).getDescription();
            nameShop = LocalShops.getShops().stream().filter(i -> i.getId() == productAdapter.getItem(position).getShop_id()).collect(Collectors.toList()).get(0).getName();

            intent.putExtra("id", itemId);
            intent.putExtra("position", productPosition);
            intent.putExtra("nameShop", nameShop);
            intent.putExtra("imgProduct", url_imgProduct);
            intent.putExtra("tvNameProduct", nameProduct);
            intent.putExtra("tvPriceProduct", priceProduct);
            intent.putExtra("rbRating", rating);
            intent.putExtra("description", description);
            startActivity(intent);

        }

    };


}

