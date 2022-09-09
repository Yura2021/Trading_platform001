package com.example.trading_platform001.carts_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trading_platform001.main_pages.MainActivity;
import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class OrderActivity extends AppCompatActivity {
    String SECRET_KEY = "sk_test_51Lc59aFu7opmpVrhzFf6bzG1Jhzc3mu9hLwIsdaKZaR37btWvE0Q9q6anpRMRgfdET9Lth0jaJXsalStuQfFTbqi00s6fbDvcU";
    String PUBLISH_KEY = "pk_test_51Lc59aFu7opmpVrhs994HUGKEybp5utIf1RViqf59Td3x7fA8i3zdIo5XzCOZD6W6xXFMh2IcuJYnTjA4qd1y3AJ00BETWtNNM";
    PaymentSheet paymentSheet;
    String customerID;
    String EphericalKey;
    String ClientSecret;
    @BindView(R.id.btnBuy)
    Button btnBuy;
    @BindView(R.id.tvSum)
    TextView tvSum;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        funPlayShop();
        btnBuy.setOnClickListener(v -> operationOrder());
        tvSum.setText(CartHelper.getCart().getTotalPrice()+" $");


    }

    void funPlayShop() {

        PaymentConfiguration.init(this, PUBLISH_KEY);
        paymentSheet = new PaymentSheet(this, this::onPaymentResult);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers", response -> {
            try {
                JSONObject object = new JSONObject(response);
                customerID = object.getString("id");
                getEphericalKey(customerID);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> parseVolleyError(error)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Authorization", "Bearer " + SECRET_KEY);
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void operationOrder() {
        if (CartHelper.getCartItems().size() > 0) {
            paymentFlow();
        }
    }


    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Платіж опрацьовано успішно", Toast.LENGTH_SHORT).show();
            if (CartHelper.getCartItems().size() > 0) {
                CartHelper.getCart().clear();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void getEphericalKey(String _customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys", response -> {
            try {
                JSONObject object = new JSONObject(response);
                EphericalKey = object.getString("id");
                Toast.makeText(getBaseContext(), EphericalKey, Toast.LENGTH_SHORT).show();
                getClientSecret(customerID, EphericalKey);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> parseVolleyError(error)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Authorization", "Bearer " + SECRET_KEY);
                    header.put("Stripe-Version", "2022-08-01");
                }
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String _customerID, String _ephericalKey) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents", response -> {
            try {
                JSONObject object = new JSONObject(response);
                ClientSecret = object.getString("client_secret");
                Toast.makeText(getBaseContext(), ClientSecret, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> parseVolleyError(error)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Authorization", "Bearer " + SECRET_KEY);

                }
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                params.put("amount", CartHelper.getCart().getTotalPrice().toString() + "00");
                params.put("currency", "eur");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void paymentFlow() {
        paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("Rozotka",
                new PaymentSheet.CustomerConfiguration(
                        customerID,
                        EphericalKey
                )));
    }

    public void parseVolleyError(VolleyError error) {

        String responseBody;
        if (error.networkResponse.data != null) {
            try {
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(responseBody);
                JSONArray errors = data.getJSONArray("errors");
                JSONObject jsonMessage = errors.getJSONObject(0);
                String message = jsonMessage.getString("message");
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                Log.d("Token error", message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

}