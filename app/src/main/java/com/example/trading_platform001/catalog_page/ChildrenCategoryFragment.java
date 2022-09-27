package com.example.trading_platform001.catalog_page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trading_platform001.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChildrenCategoryFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.NameBackCategoriChildren)
    TextView NameBackCategoriChildren;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.imageBackCategoriChildren)
    ImageView imageBackCategoriChildren;
    Bundle bundle;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public ChildrenCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_children_category, container, false);
        ButterKnife.bind(this, view);
        bundle = getArguments();
        if (bundle != null) {
            NameBackCategoriChildren.setText(bundle.getString("NameParenCategory"));
        }
        imageBackCategoriChildren.setOnClickListener(v->onClick());

        return view;
    }
    public  void onClick()
    {
        replaceFragment(new CatalogFragment());
    }
    public void replaceFragment(Fragment fragment) {
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_view_fragment, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}