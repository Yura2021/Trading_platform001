package com.example.trading_platform001.catalog_page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trading_platform001.R;
import com.example.trading_platform001.catalog_page.models.Category;
import com.example.trading_platform001.home_pages.HomeFragment;
import com.example.trading_platform001.models.LocalCategory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatalogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("NonConstantResourceId")
public class CatalogFragment extends Fragment {


    @BindView(R.id.layoutLeftCategory)
    LinearLayout layoutLeftCategory;
    @BindView(R.id.layoutRightCategory)
    LinearLayout layoutRightCategory;
    View v;
    ArrayList<Category> categories;
    ViewGroup viewGroup;
    Bundle bundle = new Bundle();
    BottomNavigationView btnNavView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CatalogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatalogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatalogFragment newInstance(String param1, String param2) {
        CatalogFragment fragment = new CatalogFragment();
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
        btnNavView = requireActivity().findViewById(R.id.bottomNavigationView);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                replaceFragment(new HomeFragment());
                btnNavView.setSelectedItemId(R.id.home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (v == null)
            v = inflater.inflate(R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, v);

        viewGroup = new LinearLayout(getContext());
        categories = new ArrayList<>();
        categories = LocalCategory.getCategory();

        ShowCategoryItem(inflater);
        return v;
    }

    public void ShowCategoryItem(LayoutInflater inflater)
    {
        for(int i = 0 ; i<categories.size();i++)
        {
            View row = inflater.inflate(R.layout.category_item_template,viewGroup, false);
            TextView name = row.findViewById(R.id.NameCategoryItemTemplate);
            ImageView image = row.findViewById(R.id.ImageCategoryItemTemplate);

            row.setOnClickListener(k->onClick(row));
            if(categories.get(i).getParent_id()==0) {
                image.setImageResource(R.drawable.ic_launcher_background);
                name.setText(categories.get(i).getName());
                if (i == 2 || i % 2 == 0) {
                    layoutLeftCategory.addView(row);
                } else {
                    layoutRightCategory.addView(row);
                }
            }
        }

    }
    public void onClick(View row) {
        for(int i = 0 ; i <categories.size();i++) {
            if (((TextView)row.findViewById(R.id.NameCategoryItemTemplate)).getText() == categories.get(i).getName()) {
                bundle.putInt("tagParenCategory", categories.get(i).getId());
                bundle.putString("NameParenCategory", categories.get(i).getName());
                replaceFragment(new ChildrenCategoryFragment(),bundle);
            }
        }
    }

    public void replaceFragment(Fragment fragment,Bundle bundle) {
       fragment.setArguments(bundle);
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fcContainerMain, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void replaceFragment(Fragment fragment) {
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fcContainerMain, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}