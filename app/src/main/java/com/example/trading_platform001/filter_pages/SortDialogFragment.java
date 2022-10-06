package com.example.trading_platform001.filter_pages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.trading_platform001.R;
import com.example.trading_platform001.models.ProductEntity;

import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class SortDialogFragment extends DialogFragment {
    View view;
    @BindView(R.id.rgGroup)
    RadioGroup radioGroup;
    @BindView(R.id.btnClose)
    Button btnClose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_dialog_sort, container, false);
        ButterKnife.bind(this, view);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            //RadioButton rb = group.findViewById(checkedId);
            sortProduct(checkedId);

        });
        CategoryFilterFragment.productAdapter.getListProduct();
        btnClose.setOnClickListener(v -> dismiss());
        return view;
    }

    private void sortProduct(int id) {
        switch (id) {
            case R.id.rbCheapToExpensive:
                //Toast.makeText(view.getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
               // LocalProducts.getProducts().sort(Comparator.comparing(ProductEntity::getPrice));
                CategoryFilterFragment.productAdapter.getListProduct().sort(Comparator.comparing(ProductEntity::getPrice));
                updateAdapter();
                break;
            case R.id.rbExpensiveToCheap:
                //LocalProducts.getProducts().sort(((o1, o2) -> o2.getPrice().compareTo(o1.getPrice())));
                CategoryFilterFragment.productAdapter.getListProduct().sort(((o1, o2) -> o2.getPrice().compareTo(o1.getPrice())));
                updateAdapter();
                //Toast.makeText(view.getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbSortNewProduct:
                //LocalProducts.getProducts().sort(Comparator.comparing(ProductEntity::getCreated_at));
                CategoryFilterFragment.productAdapter.getListProduct().sort(Comparator.comparing(ProductEntity::getCreated_at));
                updateAdapter();
                //Toast.makeText(view.getContext(), rb.getText(), Toast.LENGTH_SHORT).show();

                break;
            case R.id.rbSortRating:
                //LocalProducts.getProducts().sort(Comparator.comparing(ProductEntity::getRating));
                CategoryFilterFragment.productAdapter.getListProduct().sort(Comparator.comparing(ProductEntity::getRating));
                updateAdapter();
                //Toast.makeText(view.getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    void updateAdapter() {
       // CategoryFilterFragment.productAdapter.getListProduct();
        CategoryFilterFragment.productAdapter.notifyDataSetChanged();
    }
}
