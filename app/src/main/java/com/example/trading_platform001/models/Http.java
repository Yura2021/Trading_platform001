package com.example.trading_platform001.models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.OrderUserListAdapter;
import com.example.trading_platform001.authorizations_pages.LoginFragment;
import com.example.trading_platform001.authorizations_pages.models.User;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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

    public Http(Context context) {
        this.context = context;
        this.url = context.getString(R.string.api_server);
        this.storage = new StorageInformation(context);
        isStatusCode = true;
    }

    public Http(Context context, LoginFragment loginFragment) {
        this.context = context;
        this.url = context.getString(R.string.api_server);
        this.storage = new StorageInformation(context);
        isStatusCode = true;
        this.loginFragment = loginFragment;
    }

    public void sendRegister(String strName, String strEmail, String strPassword, String strConfirmationPassword) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/register", response -> {
        }, this::parseVolleyError) {
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
        if (strToken.isEmpty())
            strToken = "No token";
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

    public void getUser() {
        strToken = storage.GetStorage("Remember_token");
        if (strToken == null && strToken.isEmpty())
            strToken = "No token";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/user", response -> {

            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
                String name = obj.getString("name");
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
                    header.put("Authorization", "Bearer " + strToken);
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private static void parseVolleyError(VolleyError error, Context context) {

        String responseBody;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(responseBody);
                JSONArray errors = data.getJSONArray("errors");
                JSONObject jsonMessage = errors.getJSONObject(0);
                String message = jsonMessage.getString("message");
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // alertFail(message);
                Log.d("Token error", message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


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
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                alertFail(message);
                Log.d("Token error", message);
            } catch (JSONException e) {
                e.printStackTrace();
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
                alertFail("");

            } catch (JSONException e) {
                e.printStackTrace();
                if (loginFragment != null)
                    loginFragment.alertFail("Невірний логін чи пароль 2");
            }
        }, this::onErrorResponse) {

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

    private void alertFail(String s) {

        if (!s.isEmpty())
            new AlertDialog.Builder(context)
                    .setTitle("Не вдалося")
                    .setIcon(R.drawable.ic_warning)
                    .setMessage(s)
                    .setPositiveButton("OK", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
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

    public void onErrorResponse(VolleyError error) {
        int statusCode = error.networkResponse.statusCode;
        NetworkResponse response = error.networkResponse;
        Log.d("testerror", "" + statusCode + " " + Arrays.toString(response.data));
        if (loginFragment != null)
            loginFragment.alertFail("Невірний логін чи пароль");
        // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
        // For AuthFailure, you can re login with user credentials.
        // For ClientError, 400 & 401, Errors happening on client side when sending api request.
        // In this case you can check how client is forming the api and debug accordingly.
        // For ServerError 5xx, you can do retry or handle accordingly.
        if (error instanceof NetworkError) {
        } else if (error instanceof ClientError) {
            isStatusCode = false;
        } else if (error instanceof ServerError) {
            isStatusCode = false;
        } else if (error instanceof AuthFailureError) {
            isStatusCode = false;
        } else if (error instanceof ParseError) {
            isStatusCode = false;
        } else if (error instanceof NoConnectionError) {
            isStatusCode = false;
        } else if (error instanceof TimeoutError) {
            isStatusCode = false;
        }


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
        }, this::onErrorResponse) {

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
        }, this::onErrorResponse) {

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
        }, this::onErrorResponse) {

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
