package com.example.trading_platform001.catalog_page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trading_platform001.R;
import com.example.trading_platform001.catalog_page.models.Category;
import com.example.trading_platform001.models.LocalCategory;

import java.util.ArrayList;

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
    ArrayList<Category> categories;
    ViewGroup viewGroup;
    @BindView(R.id.layoutLeftCategoryChildren)
    LinearLayout layoutLeftCategory;
    @BindView(R.id.layoutRightCategoryChildren)
    LinearLayout layoutRightCategory;
    int parent_id;

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
            parent_id=bundle.getInt("tagParenCategory");
        }
        imageBackCategoriChildren.setOnClickListener(v->onClick());
        categories= LocalCategory.getCategory();
        ShowCategoryItem(inflater);

        return view;
    }



    public void ShowCategoryItem(LayoutInflater inflater)
    {
        for(int i = 0 ; i<categories.size();i++)
        {
            View row = inflater.inflate(R.layout.category_item_template,viewGroup, false);
            TextView name = row.findViewById(R.id.NameCategoryItemTemplate);
            ImageView image = row.findViewById(R.id.ImageCategoryItemTemplate);

            //row.setOnClickListener(k->onClick(row));

            if(categories.get(i).getParent_id()==parent_id) {
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