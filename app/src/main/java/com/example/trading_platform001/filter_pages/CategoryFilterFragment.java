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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
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
import com.example.trading_platform001.catalog_page.models.Category;
import com.example.trading_platform001.home_pages.HomeFragment;
import com.example.trading_platform001.interfaces.MyOnClickAddCartItem;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.LocalCategory;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.LocalTableProductCategories;
import com.example.trading_platform001.models.Product;
import com.example.trading_platform001.models.ProductCategoriesEntity;
import com.example.trading_platform001.models.ProductEntity;
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
    ImageView imgProduct;
    TextView tvNameProduct, tvPriceProduct;
    RatingBar rbRating;
    BadgeDrawable badgeDrawable;
    ImageView ibAddCart;
    @BindView(R.id.btnSort)
    Button btnSort;
    @BindView(R.id.btnFilter)
    Button btnFilter;
    @BindView(R.id.tbFilter)
    Toolbar tbFilter;
    private long itemId;
    private String url_imgProduct, nameCatecory;
    private String nameProduct, priceProduct;
    private float rating;
    boolean favorite;
    int productPosition;
    ArrayList<ProductEntity> listProduct;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_category_filter, container, false);
        ButterKnife.bind(this, view);
        btnSort.setOnClickListener(v -> settingDialogSort(container));
        btnFilter.setOnClickListener(v -> settingDialogFilter(container));
        searchView.clearFocus();
        nameCatecory = "Мережеве обладнання";
        compliteFilter();
        if (productAdapter == null)
            productAdapter = new FilterAdapter(view.getContext(), listProduct);
        tbFilter.setTitle(nameCatecory);
        tbFilter.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        tbFilter.setNavigationOnClickListener(v -> replaceFragment(new HomeFragment()));
        productAdapter.setMyOnClickAddCartItem(this);
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

        return view;
    }


    private void settingDialogSort(ViewGroup container) {

        // From another Fragment or Activity where you wish to show this
        // PurchaseConfirmationDialogFragment.
        // new SortDialogFragment().show(getChildFragmentManager(), SortDialogFragment.TAG);
        SortDialogFragment dialogFragment = new SortDialogFragment();
        dialogFragment.show(getParentFragmentManager(), "My  Fragment");
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dSortView = inflater.inflate(R.layout.fragment_dialog_sort, container, false);
        builder.setTitle("Сортування");
        builder.setView(dSortView).setNegativeButton("Відмінити", (dialog, id) -> Toast.makeText(CategoryFilterFragment.this.getContext(), "Налаштування Відмінено", Toast.LENGTH_SHORT).show())
                .setPositiveButton("Прийняти", (dialog, id) -> Toast.makeText(CategoryFilterFragment.this.getContext(), "Налаштування Прийняті", Toast.LENGTH_SHORT).show());
        builder.show();

         */

    }

    private void compliteFilter() {
        listProduct = new ArrayList<>();
        Optional<Category> category = LocalCategory.getCategory().stream().filter(s -> Objects.equals(s.getName(), nameCatecory)).findFirst();
        if (category.isPresent()) {
            int category_id = category.get().getId();

            List<ProductCategoriesEntity> list = LocalTableProductCategories.getProductCategoriesID().stream().filter(k -> k.getCategory_id() == category_id).collect(Collectors.toList());
            for (ProductCategoriesEntity item : list) {
                Optional<ProductEntity> prod = LocalProducts.getProducts().stream().filter(s -> s.getId() == item.getProduct_id()).findFirst();
                prod.ifPresent(product -> listProduct.add(product));
            }
            Log.d("Id ", " " + category_id);
        }
    }

    private void settingDialogFilter(ViewGroup container) {

        FilterDialogFragment dialogFragment = new FilterDialogFragment();
        dialogFragment.show(getParentFragmentManager(), "My  Fragment");

    }


    private final GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {


        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            imgProduct = v.findViewById(R.id.imgProduct);
            tvNameProduct = v.findViewById(R.id.tvNameProduct);
            tvPriceProduct = v.findViewById(R.id.tvPriceProduct);
            rbRating = v.findViewById(R.id.rbRating);
            ibAddCart = v.findViewById(R.id.ivAddCart);


            itemId = productAdapter.getItem(position).getId();
            productPosition = position;
            favorite = LocalProducts.getProducts().get(productPosition).isFavorite();
            nameProduct = tvNameProduct.getText().toString();
            priceProduct = tvPriceProduct.getText().toString();
            rating = rbRating.getRating();
            url_imgProduct = productAdapter.getItem(position).getCover_img();

            Intent intent = new Intent(CategoryFilterFragment.this.getContext(), DetailsProductActivity.class);
            intent.putExtra("id", itemId);
            intent.putExtra("position", productPosition);
            intent.putExtra("imgProduct", url_imgProduct);
            intent.putExtra("tvNameProduct", nameProduct);
            intent.putExtra("tvPriceProduct", priceProduct);
            intent.putExtra("rbRating", rating);
            startActivity(intent);


        }

    };

    @Override
    public void onClickAddCartItem(View v, ProductEntity productEntity) {

        /*
        imgProduct = v.findViewById(R.id.imgProduct);
        tvNameProduct = v.findViewById(R.id.tvNameProduct);
        tvPriceProduct = v.findViewById(R.id.tvPriceProduct);
        rbRating = v.findViewById(R.id.rbRating);
        ibAddCart = v.findViewById(R.id.ivAddCart);
        itemId = productAdapter.getItem(position).getId();
        productPosition = position;
        favorite = LocalProducts.getProducts().get(productPosition).isFavorite();
        nameProduct = tvNameProduct.getText().toString();
        priceProduct = tvPriceProduct.getText().toString();
        rating = rbRating.getRating();
        url_imgProduct = productAdapter.getItem(position).getCover_img();
*/
        CartEntity cart = CartHelper.getCart();
        Product product = new Product(productEntity.getId(), productEntity.getName(), productEntity.getPrice(), productEntity.getRating(), productEntity.getCover_img());
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

        Toast.makeText(getContext(), "Товар доданий у кошик", Toast.LENGTH_SHORT).show();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fcContainerMain, fragment);
        fragmentTransaction.commit();
    }
}