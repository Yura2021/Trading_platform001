package com.example.trading_platform001.products_pages.product_details_fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trading_platform001.R;
import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
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
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvNameSeller)
    TextView tvNameSeller;
    View view;
    Bundle resultBundle;
    HomeValueExProductEntity resultProduct;

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
        if (resultBundle != null) {
            Intent intent = new Intent(AllInfoProductFragment.this.getContext(), DetailsProductActivity.class);
            intent.putExtras(resultBundle);
        }

    }

    private void getResultFragment() {
        resultBundle = getArguments();
        if (resultBundle != null) {
            resultProduct = resultBundle.getParcelable("ParceHomeValueExProductEntity");
            tvNameSeller.setText(resultProduct.getNameShop());
            Picasso.get().load(Uri.parse(resultProduct.getCover_img())).into(imgProduct);
            tvNameProduct.setText(resultProduct.getName());
            tvPriceProduct.setText(String.valueOf(resultProduct.getPrice()));
            tvDescription.setText(resultProduct.getDescription());
        }

    }
}