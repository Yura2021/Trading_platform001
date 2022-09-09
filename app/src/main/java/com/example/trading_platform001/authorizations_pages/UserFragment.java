package com.example.trading_platform001.authorizations_pages;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trading_platform001.main_pages.MainActivity;
import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.adapters.MenuUserListAdapter;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.StorageInformation;


import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class UserFragment extends Fragment {
    @BindView(R.id.btnAuth)
    Button btnAuth;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.UserMenu)
    ListView listMenu;
    StorageInformation Storage;
    View v;
    Http http;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (v == null)
            v = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, v);
        Storage = new StorageInformation(getContext());
        http = new Http(v.getContext());
        // getUser();
        btnAuth.setOnClickListener(v -> requiredAuthorization());
        btnLogout.setOnClickListener(v -> logout());


        final String[] MenuItem = new String[]{
                getResources().getString(R.string.authorization_button_name_exit),
                getResources().getString(R.string.Account),
                getResources().getString(R.string.My_orders),
                getResources().getString(R.string.My_mesegges),
                getResources().getString(R.string.My_correspondence),
                getResources().getString(R.string.My_purse),
                getResources().getString(R.string.comparison),
                getResources().getString(R.string.Sales),
                getResources().getString(R.string.action),
                getResources().getString(R.string.support),
                getResources().getString(R.string.Work_time),
                getResources().getString(R.string.Information),
        };
        Resources.Theme theme = getContext().getTheme();
        @SuppressLint("UseCompatLoadingForDrawables") final Drawable[] IconMenu = new Drawable[]{
                getResources().getDrawable(R.drawable.my_null, theme),
                getResources().getDrawable(R.drawable.my_accaunt_ico, theme),
                getResources().getDrawable(R.drawable.my_orders_ico, theme),
                getResources().getDrawable(R.drawable.my_messeng_ico, theme),
                getResources().getDrawable(R.drawable.my_correspondence, theme),
                getResources().getDrawable(R.drawable.my_purse, theme),
                getResources().getDrawable(R.drawable.my_comparison, theme),
                getResources().getDrawable(R.drawable.my_sales, theme),
                getResources().getDrawable(R.drawable.my_action, theme),
                getResources().getDrawable(R.drawable.my_support, theme),
                getResources().getDrawable(R.drawable.my_shop, theme),
                getResources().getDrawable(R.drawable.my_information, theme)
        };
        if (Storage.IsEmpty()) {
            MenuItem[0] = null;
            IconMenu[0] = null;
        } else {
            MenuItem[0] = getResources().getString(R.string.authorization_button_name_exit);
            IconMenu[0] = getResources().getDrawable(R.drawable.my_null, theme);
        }

        MenuUserListAdapter adapter = new MenuUserListAdapter(getContext(), R.layout.listmenu, MenuItem, IconMenu);
        listMenu.setAdapter(adapter);

        listMenu.setOnItemClickListener((parent, itemClicked, position, id) -> {

            String selectedItem = MenuItem[position];
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
            }
        });

        return v;
    }

    private void requiredAuthorization() {
        startActivity(new Intent(getContext(), AuthorizationMenuActivity.class));
    }

    private void getUser() {
        http.getUser();
    }

    private void logout() {

        CartHelper.getCartItems().clear();
        http.logout();

        Intent intent = new Intent(requireActivity(), MainActivity.class);
        startActivity(intent);
        String strName = getResources().getResourceName(R.string.authorization_button_name_authorization);
        btnAuth.setText(strName);
        btnAuth.setEnabled(true);
        btnLogout.setEnabled(false);
        requireActivity().finish();


    }

}