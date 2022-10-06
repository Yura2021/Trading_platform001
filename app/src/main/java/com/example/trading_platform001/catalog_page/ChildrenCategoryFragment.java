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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trading_platform001.R;
import com.example.trading_platform001.catalog_page.models.Category;
import com.example.trading_platform001.filter_pages.CategoryFilterFragment;
import com.example.trading_platform001.filter_pages.FilterDialogFragment;
import com.example.trading_platform001.models.LocalCategory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class ChildrenCategoryFragment extends Fragment {


    @BindView(R.id.toolbarCategoryCildren)
    Toolbar toolbar;
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
            toolbar.setTitle(bundle.getString("NameParenCategory"));
            parent_id=bundle.getInt("tagParenCategory");
        }
        categories= LocalCategory.getCategory();
        ShowCategoryItem(inflater);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> replaceFragment(new CatalogFragment()));
        return view;
    }



    public void ShowCategoryItem(LayoutInflater inflater)
    {
        categories=sortArrayCategory(categories);
        for(int i = 0 ; i<categories.size();i++)
        {
            View row = inflater.inflate(R.layout.category_item_template,viewGroup, false);
            TextView name = row.findViewById(R.id.NameCategoryItemTemplate);
            ImageView image = row.findViewById(R.id.ImageCategoryItemTemplate);

            row.setOnClickListener(k->onClick(row));

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

    public ArrayList<Category> sortArrayCategory(ArrayList<Category> categori)
    {
        ArrayList<Category> categoryArrayList= new ArrayList<>();
        for(int i =0;i<categori.size();i++ )
        {
            if(categori.get(i).getParent_id()==parent_id)
            {
                categoryArrayList.add(categori.get(i));
            }
        }
        return categoryArrayList;
    }
    public void onClick(View row)
    {
        for(int i = 0 ; i <categories.size();i++) {
            if (((TextView)row.findViewById(R.id.NameCategoryItemTemplate)).getText() == categories.get(i).getName()) {
                bundle.putInt("tagParenCategory", bundle.getInt("tagParenCategory"));
                bundle.putString("NameParenCategory", categories.get(i).getName());
                replaceFragment(new CategoryFilterFragment(),bundle);
            }
        }
    }

    public void replaceFragment(Fragment fragment) {
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fcContainerMain, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
    public void replaceFragment(Fragment fragment,Bundle bundle) {
        fragment.setArguments(bundle);
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fcContainerMain, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}