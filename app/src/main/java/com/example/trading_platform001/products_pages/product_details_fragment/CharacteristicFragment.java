package com.example.trading_platform001.products_pages.product_details_fragment;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.trading_platform001.R;
import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
import com.example.trading_platform001.models.ProductAtribute;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class CharacteristicFragment extends Fragment {
    View view;
    @BindView(R.id.llCharacteristicMain)
    LinearLayout llCharacteristicMain;
    Bundle resultBundle;
    HomeValueExProductEntity resultProduct;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_characteristic, container, false);
        ButterKnife.bind(this, view);
        getResultFragment();

        Type listType = new TypeToken<ArrayList<ProductAtribute>>() {}.getType();

        if (resultProduct.getProduct_attributes() != null && !resultProduct.getProduct_attributes().isEmpty()) {
            List<ProductAtribute> productAtributeList = new Gson().fromJson(resultProduct.getProduct_attributes(), listType);

            for (ProductAtribute item : productAtributeList) {
                addLayautStringCharacteristic(item.getKey(), item.getValue());
            }
        } else {
            addLayautStringCharacteristic("Немає характеристики", "немає значення характеристики");
        }


        return view;
    }

    private void getResultFragment() {
        resultBundle = getArguments();
        if (resultBundle != null) {
            resultProduct = resultBundle.getParcelable("ParceHomeValueExProductEntity");
        }

    }

    private void addLayautStringCharacteristic(String name, String description) {
        CardView cardView = new CardView(view.getContext());
        LinearLayout linearLayout = new LinearLayout(view.getContext());
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        llParam.setMargins(5, 5, 5, 5);
        cardView.setLayoutParams(llParam);
        cardView.setPadding(5, 5, 5, 5);
        cardView.setCardElevation(4f);
        cardView.setUseCompatPadding(true);
        cardView.setRadius(10f);
        Resources.Theme theme = view.getContext().getTheme();
        cardView.setCardBackgroundColor(getResources().getColor(R.color.white, theme));
        llParam.setMargins(0, 5, 0, 5);
        linearLayout.setLayoutParams(llParam);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white, theme));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(10, 5, 5, 5);
        linearLayout.setWeightSum(2);

        TextView tvName = new TextView(view.getContext(), null, R.style.MyAppTheme);
        LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        tvName.setLayoutParams(tvParam);
        tvName.setText(name);
        TextView tvDescription = new TextView(view.getContext(), null, R.style.MyAppTheme);
        tvDescription.setLayoutParams(tvParam);
        tvDescription.setGravity(Gravity.START);
        tvDescription.setText(description);
        linearLayout.addView(tvName);
        linearLayout.addView(tvDescription);
        cardView.addView(linearLayout);
        llCharacteristicMain.addView(cardView);
    }
}