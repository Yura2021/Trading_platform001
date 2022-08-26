package com.example.trading_platform001;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.trading_platform001.models.CartEntity;
import com.example.trading_platform001.models.CartHelper;
import com.example.trading_platform001.models.Product;

import java.math.BigDecimal;


public class ShowProductFullscreenFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ImageView imgProduct;
    private TextView tvNameProduct, tvPriceProduct, tvIdProduct;
    private RatingBar rbRating;
    private Button addCart;
    private long id;
    private int id_imgProduct;
    private String nameProduct,priceProduct;
    private float rating;

    public ShowProductFullscreenFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ShowProductFullscreenFragment newInstance(String param1, String param2) {
        ShowProductFullscreenFragment fragment = new ShowProductFullscreenFragment();
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

    private void getResultFragment() {
        Bundle result = getArguments();
        if (result != null) {

            id = result.getLong("id", -1);
            if (id != -1) {
                nameProduct = result.getString("tvNameProduct", "No name");
                priceProduct = result.getString("tvPriceProduct");
                rating = result.getFloat("rbRating", 0f);
                id_imgProduct = result.getInt("imgProduct", -1);
                imgProduct.setImageResource(id_imgProduct);
                tvNameProduct.setText(nameProduct);
                tvPriceProduct.setText(String.valueOf(priceProduct));
                rbRating.setRating(rating);
                tvIdProduct.setText(String.valueOf(id));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_product_fullscreen, container, false);
        imgProduct = view.findViewById(R.id.imgProduct);
        tvNameProduct = view.findViewById(R.id.tvNameProduct);
        tvPriceProduct = view.findViewById(R.id.tvPriceProduct);
        rbRating = view.findViewById(R.id.rbRating);
        tvIdProduct = view.findViewById(R.id.tvIdProduct);
        addCart = view.findViewById(R.id.btnAddCart);
        getResultFragment();


        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("value id  ",String.valueOf(id));
                Bundle bundle = new Bundle();
                if (id != -1) {
                    bundle.putLong("id", id);
                    bundle.putInt("id_imgProduct", id_imgProduct);
                    Log.d("value id  ",String.valueOf(id_imgProduct));
                    bundle.putString("tvNameProduct", nameProduct);
                    bundle.putString("tvPriceProduct", priceProduct);
                    bundle.putFloat("rbRating", rating);
                    CartEntity cart = CartHelper.getCart();
                    BigDecimal dec = new BigDecimal(priceProduct);
                    Product product = new Product(id,nameProduct,dec ,rating,id_imgProduct);
                    cart.add(product, 1);


                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.layout_view_fragment, CartFragment.class,bundle,"cart")
                            .setReorderingAllowed(true)
                            .addToBackStack("replacement")
                            .commit();

                }
            }
        });
        return view;
    }
}