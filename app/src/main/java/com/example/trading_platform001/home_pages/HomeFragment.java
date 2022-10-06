package com.example.trading_platform001.home_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.ProductAdapter;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.LocalShops;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.models.ShopEntity;
import com.example.trading_platform001.products_pages.DetailsProductActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Optional;

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
    BadgeDrawable badgeDrawable;
    @BindView(R.id.image_slider)
    ImageSlider imageSlider;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        assert searchView != null;
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

    @Override
    public void onStart() {
        super.onStart();
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
    }


    private final GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {


        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            HomeValueExProductEntity homeValueExProductEntity = new HomeValueExProductEntity(productAdapter.getItem(position));
            Optional<ShopEntity> shopEntity = LocalShops.getShops().stream().filter(i -> i.getId() == productAdapter.getItem(position).getShop_id()).findFirst();
            shopEntity.ifPresent(entity -> homeValueExProductEntity.setNameShop(entity.getName()));

            Intent intent = new Intent(HomeFragment.this.getContext(), DetailsProductActivity.class);
            intent.putExtra("ParceHomeValueExProductEntity", homeValueExProductEntity);
            startActivity(intent);

        }

    };


}

