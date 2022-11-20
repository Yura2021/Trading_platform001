package com.example.trading_platform001.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.CartRecyclerAdapter;
import com.example.trading_platform001.adapters.OrderUserListAdapter;
import com.example.trading_platform001.authorizations_pages.EmailVerificationActivity;
import com.example.trading_platform001.authorizations_pages.LoginFragment;
import com.example.trading_platform001.authorizations_pages.RegistrationFragment;
import com.example.trading_platform001.authorizations_pages.models.User;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.carts_pages.models.CartItemsEntityModel;
import com.example.trading_platform001.catalog_page.models.Category;
import com.example.trading_platform001.main_pages.MainActivity;
import com.example.trading_platform001.user_pages.models.Order;
import com.example.trading_platform001.user_pages.models.OrderInformation;
import com.example.trading_platform001.user_pages.models.OrderList;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Http {
    private final Context context;
    private final String url;
    private User user;
    private boolean isStatusCode;
    private final StorageInformation storage;
    private String strToken;
    private LoginFragment loginFragment;
    private RegistrationFragment registrationFragment;
    private EmailVerificationActivity emailVerificationActivity;

    public Http(Context context) {
        this.context = context;
        this.url = context.getString(R.string.api_server);
        this.storage = new StorageInformation(context);
        isStatusCode = true;
    }

    public Http(Context context, Object obj) {
        this.context = context;
        this.url = context.getString(R.string.api_server);
        this.storage = new StorageInformation(context);
        isStatusCode = true;
        if (obj.getClass() == LoginFragment.class) {
            loginFragment = (LoginFragment) obj;
        } else if (obj.getClass() == RegistrationFragment.class) {
            registrationFragment = (RegistrationFragment) obj;
        } else if (obj.getClass() == EmailVerificationActivity.class) {
            emailVerificationActivity = (EmailVerificationActivity) obj;
        }


    }

    public void sendVerificationCode() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/email/verification-notification", response -> {
            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                String vr_code = obj.getString("vrcode");
                String message = obj.getString("message");
                storage.SetStorage("vrcode", vr_code);
                Log.d("code", vr_code);
                if (emailVerificationActivity != null)
                    emailVerificationActivity.alertSuccessToast(message);

            } catch (JSONException e) {
                e.printStackTrace();
                if (emailVerificationActivity != null)
                    emailVerificationActivity.alertFailToast("Невірний код");
            }

        }, this::parseVolleyErrorRegister) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                    header.put("Authorization", "Bearer " + storage.GetStorage("Remember_token"));
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void sendActivateEmail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/verify-email", response -> {
            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                storage.SetStorage("vrcode", "");
                String message = obj.getString("message");
                if (emailVerificationActivity != null)
                    emailVerificationActivity.alertSuccessToast(message);
                if (storage.GetStorage("email_verified_at").isEmpty())
                    getUser();

            } catch (JSONException e) {
                e.printStackTrace();
                if (emailVerificationActivity != null)
                    emailVerificationActivity.alertFailToast("Невірний код");
            }

        }, this::parseVolleyErrorRegister) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                    header.put("Authorization", "Bearer " + storage.GetStorage("Remember_token"));
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void getUser() {
        strToken = storage.GetStorage("Remember_token");
        if (strToken.isEmpty())
            strToken = "No token";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/user", response -> {
            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                user = new Gson().fromJson(obj.getString("user"), User.class);
                user.setRemember_token(strToken);
                storage.SetStorageUser(user);
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, this::parseVolleyErrorRegister) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                    header.put("Authorization", "Bearer " + storage.GetStorage("Remember_token"));
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void sendRegister(String strName, String strEmail, String strPassword, String strConfirmationPassword) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/register", response -> {
            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                //String str_array = obj.getString("attributes");
                if (registrationFragment != null)
                    registrationFragment.alertSuccessToast();

            } catch (JSONException e) {
                //e.printStackTrace();
                if (registrationFragment != null)
                    registrationFragment.alertFailToast("Невірний Email або вже зареестрований");
            }

        }, this::parseVolleyErrorRegister) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", strName);
                params.put("email", strEmail);
                params.put("password", strPassword);
                params.put("password_confirmation", strConfirmationPassword);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void getAllShop() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/shops", response -> {

            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                String str_array = obj.getString("shops");

                Type listType = new TypeToken<ArrayList<ShopEntity>>() {
                }.getType();
                LocalShops.setShops(new Gson().fromJson(str_array, listType));


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, this::parseVolleyError) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void getAllProductCategoriesID() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/cataidprodid", response -> {

            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                String str_array = obj.getString("productIDCategoryID");
                Type listType = new TypeToken<ArrayList<ProductCategoriesEntity>>() {
                }.getType();
                LocalTableProductCategories.setProductCategoriesID(new Gson().fromJson(str_array, listType));


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, this::parseVolleyError) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void getAllNewProduct() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/newproducts", response -> {

            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                String str_array = obj.getString("allnewproducts");

                Type listType = new TypeToken<ArrayList<ProductEntity>>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                LocalNewProducts.setNewProducts(gson.fromJson(str_array, listType));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, this::parseVolleyError) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void getAllAttributes() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/attribute", response -> {

            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                String str_array = obj.getString("attributes");
                String str_array_value = obj.getString("attributesvalue");

                Type listType = new TypeToken<ArrayList<AttributesEntity>>() {
                }.getType();
                Type listTypeValue = new TypeToken<ArrayList<AttributeValuesEntity>>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                LocalAttributes.setAttributes(gson.fromJson(str_array, listType));
                LocalAttributes.setAttributeValues(gson.fromJson(str_array_value, listTypeValue));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, this::parseVolleyError) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void logout() {

        strToken = storage.GetStorage("Remember_token");
        if (strToken.isEmpty()) {
            strToken = "No token";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/logout", response -> {
            storage.ClearStorage();
        }, this::parseVolleyError) {
            @NonNull
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                    header.put("Authorization", "Bearer " + strToken);
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


    private void parseVolleyErrorLogin(VolleyError error) {

        if (loginFragment != null)
            loginFragment.alertFailToast("Невірний логін чи пароль");

    }

    private void parseVolleyError(VolleyError error) {

        String responseBody;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(responseBody);
                //JSONObject errors = data.getJSONObject("errors");
                String message = data.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private void parseVolleyErrorRegister(VolleyError error) {

        String responseBody;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(responseBody);
                //JSONObject errors = data.getJSONObject("errors");
                String message = data.getString("message");
                if (registrationFragment != null)
                    registrationFragment.alertFailToast("Невірний Email або вже зареестрований");
            } catch (JSONException e) {
                e.printStackTrace();
                if (registrationFragment != null)
                    registrationFragment.alertFailToast("Невірний Email або вже зареестрований");
            }
        }


    }

    public void sendLogin(String strEmail, String strPassword) {
        isStatusCode = true;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/login", response -> {
            try {

                JSONObject obj = new JSONObject(response);
                String customerToken = obj.getString("token");
                user = new Gson().fromJson(obj.getString("user"), User.class);
                user.setRemember_token(customerToken);
                storage.SetStorageUser(user);
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);


            } catch (JSONException e) {
                // e.printStackTrace();
                if (loginFragment != null)
                    loginFragment.alertFailToast("Невірний логін чи пароль");
            }
        }, this::parseVolleyErrorLogin) {

            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", strEmail);
                params.put("password", strPassword);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


    public void sendRecPass(String strEmail) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/forgot-password", response -> {

        }, this::parseVolleyError) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", strEmail);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void GetOrdersUser(OrderUserListAdapter adapter) {
        strToken = storage.GetStorage("Remember_token");
        if (strToken.isEmpty())
            strToken = "No token";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/orders ", response -> {
            try {
                JSONObject obj = new JSONObject(response);
                Type listType = new TypeToken<ArrayList<Order>>() {
                }.getType();
                OrderList orderList = new OrderList((boolean) obj.get("status"), obj.getString("message"), new Gson().fromJson(obj.getString("order"), listType));
                adapter.updateReceiptsList(orderList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, this::parseVolleyError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                    header.put("Authorization", "Bearer " + strToken);
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void setOrderUser(OrderInformation order, List<CartItemsEntityModel> cartItemsEntityModels) {
        strToken = storage.GetStorage("Remember_token");
        if (strToken.isEmpty())
            strToken = "No token";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/setorder", response -> {
        }, this::parseVolleyError) {

            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("CardOrder", new Gson().toJson(order));
                params.put("CatItem", new Gson().toJson(cartItemsEntityModels));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                    header.put("Authorization", "Bearer " + strToken);
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void GetCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/category", response -> {
            try {
                JSONObject obj = new JSONObject(response);
                Type listType = new TypeToken<ArrayList<Category>>() {
                }.getType();
                LocalCategory.setCategory(new Gson().fromJson(obj.getString("category"), listType));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, this::parseVolleyError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Content-Length", "application/json");
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
