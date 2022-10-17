package com.example.trading_platform001.user_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.MenuUserListAdapter;
import com.example.trading_platform001.authorizations_pages.AuthorizationMenuActivity;
import com.example.trading_platform001.home_pages.HomeFragment;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.StorageInformation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class UserFragment extends Fragment {

    @BindView(R.id.UserMenu)
    ListView listMenu;
    StorageInformation Storage;
    View v;
    Http http;
    List<String> MenuItem;
    List<Drawable> IconMenu;
    MenuUserListAdapter adapter;
    BottomNavigationView btnNavView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (v == null)
            v = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, v);
        MenuItem = new ArrayList<>();
        IconMenu = new ArrayList<>();
        Storage = new StorageInformation(getContext());
        http = new Http(v.getContext());
        MenuItem=GetListName();
        IconMenu = GetListIco();

        if (Storage.IsEmpty()) {
            IconMenu.remove(0);
            MenuItem.remove(0);
        }
        adapter = new MenuUserListAdapter(getContext(), R.layout.listmenu, MenuItem, IconMenu);
        listMenu.setAdapter(adapter);
        listMenu.setOnItemClickListener((parent, itemClicked, position, id) -> {

            String selectedItem = MenuItem.get(position);
            switch (selectedItem) {
                case "Вхід": {
                    startActivity(new Intent(getContext(), AuthorizationMenuActivity.class));
                    break;
                }
                case "Обліковий запис": {
                    if (Storage.IsEmpty()) {
                        startActivity(new Intent(getContext(), UserInformation.class));
                    } else {
                        startActivity(new Intent(getContext(), AuthorizationMenuActivity.class));
                    }
                    break;
                }
                case "Мої замовлення":
                    if (Storage.IsEmpty()) {
                     replaceFragment(new User_OrderList_Informations());
                    } else {
                        startActivity(new Intent(getContext(), AuthorizationMenuActivity.class));
                    }
                    break;
            }
        });

        return v;
    }
    public List<String>GetListName()
    {
        List<String> MenuItem = new ArrayList<>();
        MenuItem.add(getContext().getResources().getString(R.string.authorization_button_name_exit));
        MenuItem.add(getContext().getResources().getString(R.string.Account));
        MenuItem.add(getContext().getResources().getString(R.string.My_orders));
        MenuItem.add(getContext().getResources().getString(R.string.My_mesegges));
        MenuItem.add(getContext().getResources().getString(R.string.My_correspondence));
        MenuItem.add(getContext().getResources().getString(R.string.My_purse));
        MenuItem.add(getContext().getResources().getString(R.string.comparison));
        MenuItem.add(getContext().getResources().getString(R.string.Sales));
        MenuItem.add(getContext().getResources().getString(R.string.action));
        MenuItem.add(getContext().getResources().getString(R.string.support));
        MenuItem.add(getContext().getResources().getString(R.string.Work_time));
        MenuItem.add(getContext().getResources().getString(R.string.Information));
        return MenuItem;
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public List<Drawable>GetListIco()
    {
        Resources.Theme theme = getContext().getTheme();
        List<Drawable> IconMenu = new ArrayList<>();
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_null, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_accaunt, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_orders_ico, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_messeng_ico, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_correspondence, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_purse, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_comparison, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_sales, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_action, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_support, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_shop, theme));
        IconMenu.add(getContext().getResources().getDrawable(R.drawable.my_information, theme));
        return IconMenu;
    }
    public void replaceFragment(Fragment fragment) {
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fcContainerMain, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}