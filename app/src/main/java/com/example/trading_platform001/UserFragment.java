package com.example.trading_platform001;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.trading_platform001.models.CartHelper;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.MenuUserListAdapter;
import com.example.trading_platform001.models.StorageInformation;

import org.json.JSONException;
import org.json.JSONObject;


public class UserFragment extends Fragment {

    Button btnAuth, btnLogout;
    ListView listMenu;
    StorageInformation Storage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user,
                null);
        Storage = new StorageInformation(getContext());
        btnAuth = v.findViewById(R.id.btnAuth);
        btnLogout = v.findViewById(R.id.btnLogout);
        getUser();
        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AuthorizationMenuActivity.class));
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        listMenu = (ListView) v.findViewById(R.id.UserMenu);

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
        final Drawable[] IconMenu = new Drawable[]{
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

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {

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
            }
        });

        return v;
    }

    private void getUser() {
        String url = getString(R.string.api_server) + "/user";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(requireActivity(), url);
                http.setToken(true);
                http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String name = response.getString("name");
                                btnAuth.setText(name);
                                btnLogout.setEnabled(true);
                                btnAuth.setEnabled(false);

                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                        //else{Toast.makeText(requireActivity(),"Error "+code,Toast.LENGTH_SHORT).show();}
                    }
                });
            }
        }).start();
    }

    private void logout() {
        String url = getString(R.string.api_server) + "/logout";
        CartHelper.getCartItems().clear();
        Log.d("Value!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(requireActivity(), url);
                http.setMethod("POST");
                http.setToken(true);
                http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200) {
                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                            startActivity(intent);
                            String strName = getResources().getResourceName(R.string.authorization_button_name_authorization);
                            btnAuth.setText(strName);
                            btnAuth.setEnabled(true);
                            btnLogout.setEnabled(false);
                            requireActivity().finish();
                        } else {
                            Toast.makeText(requireActivity(), "Error " + code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

}