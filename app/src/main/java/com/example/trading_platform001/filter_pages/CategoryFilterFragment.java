package com.example.trading_platform001.filter_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.FilterAdapter;
import com.example.trading_platform001.carts_pages.models.CartEntity;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.catalog_page.ChildrenCategoryFragment;
import com.example.trading_platform001.catalog_page.models.Category;
import com.example.trading_platform001.filter_pages.models.SaveFilterOption;
import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
import com.example.trading_platform001.interfaces.MyOnClickAddCartItem;
import com.example.trading_platform001.models.LocalCategory;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.LocalShops;
import com.example.trading_platform001.models.LocalTableProductCategories;
import com.example.trading_platform001.models.Product;
import com.example.trading_platform001.models.ProductCategoriesEntity;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.models.ShopEntity;
import com.example.trading_platform001.products_pages.DetailsProductActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class CategoryFilterFragment extends Fragment implements MyOnClickAddCartItem {
    @NonNull
    @BindView(R.id.svSearch)
    SearchView searchView;
    @BindView(R.id.grid_product)
    GridView gridview;
    @SuppressLint("StaticFieldLeak")
    public static FilterAdapter productAdapter;
    BottomNavigationView btnNavView;
    View view;
    BadgeDrawable badgeDrawable;
    @BindView(R.id.btnSort)
    Button btnSort;
    @BindView(R.id.btnFilter)
    Button btnFilter;
    @BindView(R.id.tbFilter)
    Toolbar tbFilter;
    private String nameCatecory;
    ArrayList<HomeValueExProductEntity> listProduct;
    Bundle bundle;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_category_filter, container, false);
        ButterKnife.bind(this, view);
        bundle = getArguments();
        btnSort.setOnClickListener(v -> settingDialogSort());
        btnFilter.setOnClickListener(v -> settingDialogFilter());
        searchView.clearFocus();

        if (bundle != null) {
            nameCatecory = bundle.getString("NameParenCategory");
            bundle.putInt("tagParenCategory", bundle.getInt("tagParenCategory"));
        }


        tbFilter.setTitle(nameCatecory);
        tbFilter.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        tbFilter.setNavigationOnClickListener(v -> replaceFragment(new ChildrenCategoryFragment(), bundle));

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
        compliteFilter();
        if (productAdapter == null)
            productAdapter = new FilterAdapter(view.getContext(), listProduct);
        productAdapter.setMapItemSearch(SaveFilterOption.getSaveCheck());
        productAdapter.setMyOnClickAddCartItem(this);
        updateFilterSaveItem();
        gridview.setAdapter(productAdapter);

        return view;
    }

/*
    @Override
    public void onResume() {
        super.onResume();
        updateFilterSaveItem();
    }


 */
    private void settingDialogSort() {

        // new SortDialogFragment().show(getChildFragmentManager(), SortDialogFragment.TAG);
        SortDialogFragment dialogFragment = new SortDialogFragment();
        dialogFragment.show(getParentFragmentManager(), "My  Fragment");

    }

    private void compliteFilter() {

        listProduct = new ArrayList<>();
        Optional<Category> category = LocalCategory.getCategory().stream().filter(s -> Objects.equals(s.getName(), nameCatecory)).findFirst();
        if (category.isPresent()) {
            int category_id = category.get().getId();

            List<ProductCategoriesEntity> list = LocalTableProductCategories.getProductCategoriesID().stream().filter(k -> k.getCategory_id() == category_id).collect(Collectors.toList());
            for (ProductCategoriesEntity item : list) {
                Optional<ProductEntity> prod = LocalProducts.getProducts().stream().filter(s -> s.getId() == item.getProduct_id()).findFirst();
                if (prod.isPresent()) {
                    Optional<ShopEntity> shopEntity = LocalShops.getShops().stream().filter(i -> i.getId() == prod.get().getShop_id()).findFirst();
                    shopEntity.ifPresent(entity -> listProduct.add(new HomeValueExProductEntity(prod.get(), entity.getName())));

                }
            }

            if (productAdapter != null)
                productAdapter.setListProduct(listProduct);
        }


    }

    private void updateFilterSaveItem() {

        if (!SaveFilterOption.getSaveCheck().isEmpty()) {
            productAdapter.setMapItemSearch(SaveFilterOption.getSaveCheck());
            productAdapter.getFilter().filter(" ");
        }

    }

    private void settingDialogFilter() {
        updateFilterSaveItem();
        FilterDialogFragment dialogFragment = new FilterDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getParentFragmentManager(), "MyFragment");

    }


    private final GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {


        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            HomeValueExProductEntity homeValueExProductEntity = new HomeValueExProductEntity(productAdapter.getItem(position));

            Optional<ShopEntity> shopEntity = LocalShops.getShops().stream().filter(i -> i.getId() == productAdapter.getItem(position).getShop_id()).findFirst();
            shopEntity.ifPresent(entity -> homeValueExProductEntity.setNameShop(entity.getName()));
            Log.d("CategoryFilterFragment","position "+position);
            Intent intent = new Intent(CategoryFilterFragment.this.getContext(), DetailsProductActivity.class);
            intent.putExtra("ParceHomeValueExProductEntity", homeValueExProductEntity);
            startActivity(intent);

        }

    };

    @Override
    public void onClickAddCartItem(View v, HomeValueExProductEntity productEntity) {
        Log.d("CategoryFilterFragment","onClickAddCartItem "+productEntity.getName());
        CartEntity cart = CartHelper.getCart();
        if (!productEntity.isAddCard()) {
            Optional<ProductEntity> prod = LocalProducts.getProducts().stream().filter(s -> Objects.equals(s.getId(), productEntity.getId())).findFirst();
            prod.ifPresent(product -> {
                product.setAddCard(true);
                productEntity.setAddCard(true);
            });
        }
        Product product = new Product(productEntity);
        cart.add(product, 1);
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
        productAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Товар доданий у кошик", Toast.LENGTH_SHORT).show();
    }

    public void replaceFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fcContainerMain, fragment);
        fragmentTransaction.commit();
    }
}