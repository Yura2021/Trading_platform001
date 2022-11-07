package com.example.trading_platform001.home_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.NewProductAdapter;
import com.example.trading_platform001.adapters.ProductAdapter;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
import com.example.trading_platform001.models.LocalNewProducts;
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
    NewProductAdapter newProductAdapter;
    BottomNavigationView btnNavView;
    View view;
    BadgeDrawable badgeDrawable;
    @BindView(R.id.image_slider)
    ImageSlider imageSlider;
    @BindView(R.id.new_grid_product)
    GridView new_grid_product;
    @BindView(R.id.container_new_product)
    LinearLayout container_new_product;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        assert searchView != null;
        searchView.clearFocus();
        if (productAdapter == null)
            productAdapter = new ProductAdapter(view.getContext(), LocalProducts.getProducts());
        btnNavView = requireActivity().findViewById(R.id.bottomNavigationView);
        gridview.setOnItemClickListener(gridviewOnItemClickListener);
        new_grid_product.setOnItemClickListener(new_grid_productOnItemClickListener);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                productAdapter.getFilter().filter(newText);
                //updateGridViewHeight();
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
        if (!LocalNewProducts.isNull() && LocalNewProducts.getNewProducts().size() > 0) {
            container_new_product.setVisibility(View.VISIBLE);
            if (newProductAdapter == null)
                newProductAdapter = new NewProductAdapter(view.getContext(), LocalNewProducts.getNewProducts());
            new_grid_product.setAdapter(newProductAdapter);
        } else {
            container_new_product.setVisibility(View.INVISIBLE);
        }


        gridview.setAdapter(productAdapter);
       // updateGridViewHeight();
        ArrayList<SlideModel> imageList = new ArrayList<>();
        for (ProductEntity item : LocalProducts.getProducts()) {
            imageList.add(new SlideModel(item.getCover_img(), ScaleTypes.FIT));
        }

        imageSlider.setImageList(imageList, ScaleTypes.FIT);
        return view;
    }

    public void updateGridViewHeight(){
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llParam.height=Integer.parseInt(String.valueOf((productAdapter.getSearchSize()/2)*400));
        Log.d("updateGridViewHeight()",String.valueOf(productAdapter.getCount()));
        Log.d("updateGridViewHeight()",String.valueOf( productAdapter.getSearchSize()));
        gridview.setLayoutParams(llParam);

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

    private final GridView.OnItemClickListener new_grid_productOnItemClickListener = new GridView.OnItemClickListener() {


        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            HomeValueExProductEntity homeValueExProductEntity = new HomeValueExProductEntity(newProductAdapter.getItem(position));
            Optional<ShopEntity> shopEntity = LocalShops.getShops().stream().filter(i -> i.getId() == newProductAdapter.getItem(position).getShop_id()).findFirst();
            shopEntity.ifPresent(entity -> homeValueExProductEntity.setNameShop(entity.getName()));

            Intent intent = new Intent(HomeFragment.this.getContext(), DetailsProductActivity.class);
            intent.putExtra("ParceHomeValueExProductEntity", homeValueExProductEntity);
            startActivity(intent);

        }

    };
}

