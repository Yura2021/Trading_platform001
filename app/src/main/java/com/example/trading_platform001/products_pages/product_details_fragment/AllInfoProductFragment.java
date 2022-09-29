package com.example.trading_platform001.products_pages.product_details_fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.products_pages.DetailsProductActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class AllInfoProductFragment extends Fragment {
    @BindView(R.id.imgProduct)
    ImageView imgProduct;
    @BindView(R.id.tvNameProduct)
    TextView tvNameProduct;
    @BindView(R.id.tvPriceProduct)
    TextView tvPriceProduct;
    @BindView(R.id.rbRating)
    RatingBar rbRating;
    long id;
    String url_imgProduct;
    String nameProduct, priceProduct;
    float rating;
    View view;
    Bundle result;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_all_info_product, container, false);
        ButterKnife.bind(this, view);
        getResultFragment();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(result!=null) {
            Intent intent = new Intent(AllInfoProductFragment.this.getContext(), DetailsProductActivity.class);
            intent.putExtras(result);
        }

    }

    private void getResultFragment() {

        result = getArguments();
        if (result != null) {
            id = result.getLong("id", -1);
            if (id != -1) {
                nameProduct = result.getString("tvNameProduct", "No name");
                priceProduct = result.getString("tvPriceProduct");
                rating = result.getFloat("rbRating", 0f);
                url_imgProduct = result.getString("imgProduct", "no image");
                imgProduct.setImageURI(Uri.parse(url_imgProduct));
                Picasso.get().load(Uri.parse(url_imgProduct)).into(imgProduct);
                tvNameProduct.setText(nameProduct);
                tvPriceProduct.setText(String.valueOf(priceProduct));
                rbRating.setRating(rating);
            }
        }

    }
}