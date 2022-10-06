package com.example.trading_platform001.carts_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.main_pages.MainActivity;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.StorageInformation;
import com.example.trading_platform001.user_pages.models.OrderInformation;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class OrderActivity extends AppCompatActivity {
    private static final String SECRET_KEY = "Bearer sk_test_51Lc59aFu7opmpVrhzFf6bzG1Jhzc3mu9hLwIsdaKZaR37btWvE0Q9q6anpRMRgfdET9Lth0jaJXsalStuQfFTbqi00s6fbDvcU";
    private static final String PUBLISH_KEY = "pk_test_51Lc59aFu7opmpVrhs994HUGKEybp5utIf1RViqf59Td3x7fA8i3zdIo5XzCOZD6W6xXFMh2IcuJYnTjA4qd1y3AJ00BETWtNNM";
    PaymentSheet paymentSheet;
    String customerID;
    String EphericalKey;
    String ClientSecret;
    @BindView(R.id.btnBuy)
    Button btnBuy;
    @BindView(R.id.tvSum)
    TextView tvSum;
    @BindView(R.id.tvSumGeneral)
    TextView tvSumGeneral;
    @BindView(R.id.imageBackOrder)
    ImageView imageView;
    @BindView(R.id.editNameOrderCart)
    EditText NameeditText;
    @BindView(R.id.editRegionOrderCart)
    EditText RegioneditText;
    @BindView(R.id.editCitiOrderCart)
    EditText CitieditText;
    @BindView(R.id.editStreetOrderCart)
    EditText StreeteditText;
    @BindView(R.id.editPhoneOrderCart)
    EditText PhoneeditText;
    @BindView(R.id.editZipCodeOrderCart)
    EditText ZipCodeeditText;
    @BindView(R.id.checkBoxCartPayment)
    CheckBox checkBoxCartPayment;
    StorageInformation storageInformation;
    OrderInformation orderInformation;
    Http http;
    String payment;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        funPlayShop();
        http = new Http(this);
        storageInformation = new StorageInformation(this);
        btnBuy.setOnClickListener(v -> operationOrder());
        tvSum.setText(CartHelper.getCart().getTotalPrice() + " " + getString(R.string.money_ua));
        tvSumGeneral.setText(CartHelper.getCart().getTotalPrice() + " " + getString(R.string.money_ua));
        imageView.setOnClickListener(v -> onclick());

        if (storageInformation.GetStorage("Name") != null) {
            NameeditText.setText(storageInformation.GetStorage("Name"));
        }
    }

    private void onclick() {
        finish();
    }

    void funPlayShop() {

        PaymentConfiguration.init(this, PUBLISH_KEY);
        paymentSheet = new PaymentSheet(this, this::onPaymentResult);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers", response -> {
            try {
                JSONObject object = new JSONObject(response);
                customerID = object.getString("id");
                getEphericalKey();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, this::parseVolleyError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Authorization", SECRET_KEY);
                }
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setOrder() {
        orderInformation = new OrderInformation(CartHelper.getCart().getTotalPrice().intValue(), Calendar.getInstance().getTime(),
                NameeditText.getText().toString(), StreeteditText.getText().toString(),
                CitieditText.getText().toString(), RegioneditText.getText().toString(),
                ZipCodeeditText.getText().toString(), PhoneeditText.getText().toString(),
                CartHelper.getCartItems().size(), payment);

        http.setOrderUser(orderInformation, CartHelper.getCartItems());
    }

    private void operationOrder() {
        Log.d("paymentFlow()", "ClientSecret:" + ClientSecret + " customerID: " + customerID + " EphericalKey: " + EphericalKey);
        if (checkBoxCartPayment.isChecked()) {
            payment = "paypal";//можна змінити на інший ти карти
            if (CartHelper.getCartItems().size() > 0) {
                paymentFlow();
            }
        } else {
            payment = "cash_on_delivery";
            ClearCart();
        }
    }


    public void ClearCart() {
        setOrder();
        if (CartHelper.getCartItems().size() > 0) {
            CartHelper.getCart().clear();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Платіж опрацьовано успішно", Toast.LENGTH_SHORT).show();
            ClearCart();
        }
    }

    private void getEphericalKey() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys", response -> {
            try {
                JSONObject object = new JSONObject(response);
                EphericalKey = object.getString("id");
               // Toast.makeText(this, EphericalKey, Toast.LENGTH_SHORT).show();
                getClientSecret();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, this::parseVolleyError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Authorization", SECRET_KEY);
                    header.put("Stripe-Version", "2022-08-01");
                }
                return header;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getClientSecret() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents", response -> {
            try {
                JSONObject object = new JSONObject(response);
                Log.d("object", object.toString());
                ClientSecret = object.getString("client_secret");
               // Log.d("paymentFlow() 1", "CartHelper.getCart().getTotalPrice(): " + CartHelper.getCart().getTotalPrice().toString() + "  ClientSecret:" + ClientSecret + " customerID: " + customerID + " EphericalKey: " + EphericalKey);
                Toast.makeText(getBaseContext(), ClientSecret, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, this::onErrorResponse) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                {
                    header.put("Authorization", SECRET_KEY);

                }
                return header;
            }

            @Override
            protected Map<String, String> getParams() {
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
        if (!ClientSecret.isEmpty() && !customerID.isEmpty() && !EphericalKey.isEmpty()) {
            paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("Rozotka2",
                    new PaymentSheet.CustomerConfiguration(
                            customerID,
                            EphericalKey
                    )));
        }


    }

    public void parseVolleyError(VolleyError error) {

        String responseBody;
        if (error.networkResponse.data != null) {
            try {
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(responseBody);
                JSONArray errors = data.getJSONArray("error");
                JSONObject jsonMessage = errors.getJSONObject(0);
                String message = jsonMessage.getString("message");
                Log.d("error", message);
                Toast.makeText(this, "Тимчасова помилка сервысу з оформленням заявки!!!", Toast.LENGTH_LONG).show();
                onBackPressed();
                finish();
                // Log.d("Token error", message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public void onErrorResponse(VolleyError error) {

        // As of f605da3 the following should work
        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                String res = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                Log.d("object", response.toString());
                // Now you can use any deserializer to make sense of data
                Toast.makeText(this, "Тимчасова помилка сервысу з оформленням заявки!!!", Toast.LENGTH_LONG).show();
                //Log.d("paymentFlow() 2", "CartHelper.getCart().getTotalPrice(): " + CartHelper.getCart().getTotalPrice().toString() + "  ClientSecret:" + ClientSecret + " customerID: " + customerID + " EphericalKey: " + EphericalKey);
                // JSONObject obj = new JSONObject(res);
                // onBackPressed();
                finish();

            } catch (UnsupportedEncodingException e1) {
                ///| JSONException e1
                // Couldn't properly decode data to string
                e1.printStackTrace();
            } // returned data is not JSONObject?

        }
    }

}