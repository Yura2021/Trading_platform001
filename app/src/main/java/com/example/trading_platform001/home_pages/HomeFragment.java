package com.example.trading_platform001.home_pages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.products_pages.ShowProductFullscreenFragment;
import com.example.trading_platform001.adapters.ProductAdapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends Fragment {

    @BindView(R.id.svSearch)
    SearchView searchView;
    @BindView(R.id.grid_product)
    GridView gridview;
    ProductAdapter productAdapter;
    BottomNavigationView btnNavView;
    View view;
    ImageView imgProduct;
    TextView tvNameProduct, tvPriceProduct, tvIdProduct;
    RatingBar rbRating;
    BadgeDrawable badgeDrawable;

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

        return view;
    }


    private final GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {


        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            Bundle bundle = new Bundle();
            if (imgProduct == null)
                imgProduct = v.findViewById(R.id.imgProduct);
            if (tvNameProduct == null)
                tvNameProduct = v.findViewById(R.id.tvNameProduct);
            if (tvPriceProduct == null)
                tvPriceProduct = v.findViewById(R.id.tvPriceProduct);
            if (rbRating == null)
                rbRating = v.findViewById(R.id.rbRating);
            if (tvIdProduct == null)
                tvIdProduct = v.findViewById(R.id.tvIdProduct);
            bundle.putLong("id", productAdapter.getItem(position).getId());
            bundle.putString("imgProduct", productAdapter.getItem(position).getCover_img());
            bundle.putString("tvNameProduct", tvNameProduct.getText().toString());
            bundle.putString("tvPriceProduct", tvPriceProduct.getText().toString());
            bundle.putFloat("rbRating", rbRating.getRating());

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.layout_view_fragment, ShowProductFullscreenFragment.class, bundle)
                    .setReorderingAllowed(true)
                    .addToBackStack("replacement")
                    .commit();


        }
    };

}

