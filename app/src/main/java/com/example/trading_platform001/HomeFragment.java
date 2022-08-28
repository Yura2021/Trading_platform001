package com.example.trading_platform001;

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

import androidx.fragment.app.Fragment;

import com.example.trading_platform001.models.ProductAdapter;


public class HomeFragment extends Fragment {

    SearchView searchView;
    GridView gridview;
    ProductAdapter productAdapter;
    ImageView imgProduct;
    TextView tvNameProduct, tvPriceProduct, tvIdProduct;
    RatingBar rbRating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = view.findViewById(R.id.svSearch);
        searchView.clearFocus();
        gridview = view.findViewById(R.id.grid_product);
        productAdapter = new ProductAdapter(view.getContext());

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

        gridview.setAdapter(productAdapter);
        return view;
    }

    private final GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            Bundle bundle = new Bundle();
            imgProduct = v.findViewById(R.id.imgProduct);
            tvNameProduct = v.findViewById(R.id.tvNameProduct);
            tvPriceProduct = v.findViewById(R.id.tvPriceProduct);
            rbRating = v.findViewById(R.id.rbRating);
            tvIdProduct = v.findViewById(R.id.tvIdProduct);
            bundle.putLong("id", productAdapter.getItem(position).getId());
            bundle.putInt("imgProduct", productAdapter.getItem(position).getImg_id());
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
