package com.example.trading_platform001.products_pages.product_details_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trading_platform001.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class CharacteristicFragment extends Fragment {
    View view;
    @BindView(R.id.llCharacteristicMain)
    LinearLayout llCharacteristicMain;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_characteristic, container, false);
        ButterKnife.bind(this, view);
        for (int i = 0; i < 10; i++) {
            addLayautStringCharacteristic("Parameter Name", "DescdasDD  ASDasfd asFa sfaS ASF SAF ASFWERFWERQWER RWE WQERQWEREWKDahjsfklhjasklfhdklhfl hhfjahjh KDHFJDahgfjhdription");
        }

        return view;
    }

    private void addLayautStringCharacteristic(String name, String description) {
        LinearLayout linearLayout = new LinearLayout(view.getContext());
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llParam.setMargins(0,5,0,5);
        linearLayout.setLayoutParams(llParam);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white, view.getContext().getTheme()));
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
        tvDescription.setGravity(Gravity.LEFT);
        tvDescription.setText(description);
        linearLayout.addView(tvName);
        linearLayout.addView(tvDescription);
        llCharacteristicMain.addView(linearLayout);
    }
}