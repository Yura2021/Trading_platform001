package com.example.trading_platform001.main_pages;


import android.annotation.SuppressLint;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trading_platform001.R;
import com.example.trading_platform001.catalog_page.CatalogFragment;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.user_pages.UserFragment;
import com.example.trading_platform001.carts_pages.CartFragment;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.home_pages.HomeFragment;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.products_pages.ShowProductFullscreenFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView btnNavView;
    @BindView(R.id.loadPB)
    ProgressBar progressBar;
    private String url;
    BadgeDrawable badgeDrawable;
    Http http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        http= new Http(this);
        url = getString(R.string.api_server);
        if (LocalProducts.isNull()) {
            getAllProduct();
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            if (progressBar.isIndeterminate()) {
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.INVISIBLE);
                replaceFragment(new HomeFragment());
            }


        }
        btnNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.catalog:
                    replaceFragment(new CatalogFragment());
                    break;
                case R.id.cart:
                    replaceFragment(new CartFragment());

                    break;
                case R.id.person:
                    replaceFragment(new UserFragment());
                    break;
            }
            return true;
        });
        int size = CartHelper.getCartItems().size();
        if (badgeDrawable == null)
            badgeDrawable = btnNavView.getOrCreateBadge(R.id.cart);
        if (size > 0) {
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(size);
        } else {
            badgeDrawable.setVisible(false);
            badgeDrawable.clearNumber();

        }
        if(LocalProducts.isNull()) {
            http.GetCategory();
        }
    }


    public void getAllProduct() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/products", response -> {

            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                String str_array = obj.getString("products");

                Type listType = new TypeToken<ArrayList<ProductEntity>>() {
                }.getType();
                LocalProducts.setProducts(new Gson().fromJson(str_array, listType));
                progressBar.setVisibility(View.GONE);
                replaceFragment(new HomeFragment());

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> parseVolleyError(error)) {
            @NonNull
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");

                }
                return header;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void parseVolleyError(VolleyError error) {

        String responseBody;

        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(responseBody);
                JSONArray errors = data.getJSONArray("errors");
                JSONObject jsonMessage = errors.getJSONObject(0);
                String message = jsonMessage.getString("message");
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                // alertFail(message);
                Log.d("Token error", message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_view_fragment, fragment);
        fragmentTransaction.commit();
    }


}