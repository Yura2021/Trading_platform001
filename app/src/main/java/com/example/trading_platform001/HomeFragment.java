package com.example.trading_platform001;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.trading_platform001.models.Product;
import com.example.trading_platform001.models.ProductAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SearchView searchView;
    GridView gridview;
    ProductAdapter productAdapter;
    ImageView imgProduct;
    TextView tvNameProduct,tvPriceProduct,tvIdProduct;
    RatingBar rbRating;
    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = view.findViewById(R.id.svSearch);
        searchView.clearFocus();
        gridview =view.findViewById(R.id.grid_product);
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
                Log.d("GridOnItemClick","onQueryTextChange");
                return true;
            }
        });

        gridview.setAdapter(productAdapter);
        return view;
    }

    private final GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            // TODO Auto-generated method stub
            Log.d("GridOnItemClick","True "+parent.getCount());
            Intent i = new Intent(requireActivity(), ShowProductFullscreenActivity.class);
            // passing array index
            imgProduct =  v.findViewById(R.id.imgProduct);
            tvNameProduct =  v.findViewById(R.id.tvNameProduct);
            tvPriceProduct =  v.findViewById(R.id.tvPriceProduct);
            rbRating =  v.findViewById(R.id.rbRating);
            tvIdProduct =  v.findViewById(R.id.tvIdProduct);

            i.putExtra("id", position);
            i.putExtra("imgProduct", productAdapter.getItem(position).getImg_id());
            i.putExtra("tvNameProduct", tvNameProduct.getText());
            i.putExtra("tvPriceProduct", tvPriceProduct.getText());
            i.putExtra("rbRating", rbRating.getRating());
            i.putExtra("tvIdProduct", tvIdProduct.getText());

           startActivity(i);



        }
    };


}
