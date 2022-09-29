package com.example.trading_platform001.filter_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.FilterAdapter;
import com.example.trading_platform001.carts_pages.models.CartEntity;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.interfaces.MyOnClickAddCartItem;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.Product;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.products_pages.DetailsProductActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class CategoryFilterFragment extends Fragment implements MyOnClickAddCartItem {
    @NonNull
    @BindView(R.id.svSearch)
    SearchView searchView;
    @BindView(R.id.grid_product)
    GridView gridview;
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
    private long itemId;
    private String url_imgProduct;
    private String nameProduct, priceProduct;
    private float rating;
    boolean favorite;
    int productPosition;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_category_filter, container, false);
        ButterKnife.bind(this, view);
        btnSort.setOnClickListener(v -> settingDialogSort(container));
        btnFilter.setOnClickListener(v -> settingDialogFilter(container));
        searchView.clearFocus();
        if (productAdapter == null)
            productAdapter = new FilterAdapter(view.getContext(), LocalProducts.getProducts());
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
}