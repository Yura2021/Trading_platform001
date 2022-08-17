package com.example.trading_platform001;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
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
    private List<Product> listProduct;
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

        listProduct = new ArrayList<>();
        listProduct.add(new Product("Продукт 1",212,3,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 2",112,5,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Подукт 3",122,4,R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product("Продукт 4",312,1,R.drawable.ic_main_basket_btn_24));
        listProduct.add(new Product("Продукт 5",152,3,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 6",912,4.5,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Продукт 7",142,2.5,R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product("Продукт 8",172,2,R.drawable.ic_main_basket_btn_24));
        listProduct.add(new Product("Продукт 9",172,5,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 10",172,5,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Продукт 11",152,3.8,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 12",272,5,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Продукт 13",372,4.2,R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product("Продукт 14",672,2.8,R.drawable.ic_main_basket_btn_24));
        listProduct.add(new Product("Продукт 15",192,5,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 16",142,3.6,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Продукт 17",1172,5,R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product("Продукт 18",1172,5,R.drawable.ic_main_basket_btn_24));

        searchView = view.findViewById(R.id.svSearch);
        searchView.clearFocus();
        gridview =view.findViewById(R.id.grid_product);
        productAdapter = new ProductAdapter(view.getContext(),listProduct);

        //gridview.setOnItemClickListener(gridviewOnItemClickListener);
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

    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            // TODO Auto-generated method stub
/*
            // Sending image id to FullScreenActivity
            Intent i = new Intent(requireActivity(), FullImageActivity.class);
            // passing array index
            i.putExtra("id", position);
            startActivity(i);
            */

        }
    };


}
