package com.example.trading_platform001.carts_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.example.trading_platform001.carts_pages.models.CartItemsEntityModel;
import com.example.trading_platform001.main_pages.MainActivity;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.ProductEntity;
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
import java.util.Objects;
import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class OrderActivity extends AppCompatActivity {
    private static final String PUBLISH_KEY = "pk_test_51Lc59aFu7opmpVrhs994HUGKEybp5utIf1RViqf59Td3x7fA8i3zdIo5XzCOZD6W6xXFMh2IcuJYnTjA4qd1y3AJ00BETWtNNM";
    private static final String SECRET_KEY = "Bearer sk_test_51Lc59aFu7opmpVrh7nPCO0DylS6a2uYM9CC6iZxJJzzZQaNC4en9ZTkxM5GeGnPMwSuMxt4HCtHrZW67XZVg4CXd00CstmRok2";

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
    @BindView(R.id.checkBoxCashPayment)
    CheckBox checkBoxCashPayment;
    @BindView(R.id.toolbarOrder)
    Toolbar toolbar;
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
        PaymentConfiguration.init(getApplicationContext(), PUBLISH_KEY);
        funPlayShop();
        http = new Http(this);
        storageInformation = new StorageInformation(this);
        btnBuy.setOnClickListener(v -> ChekedInfromOrder());
        tvSum.setText(CartHelper.getCart().getTotalPrice() + " " + getString(R.string.money_ua));
        tvSumGeneral.setText(CartHelper.getCart().getTotalPrice() + " " + getString(R.string.money_ua));
          if (storageInformation.GetStorage("Name") != null) {
            NameeditText.setText(storageInformation.GetStorage("Name"));
        }

        toolbar.setTitle("Обліковий запис");

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v->finish());
    }

    public void ChekedInfromOrder()
    {
        if(!NameeditText.getText().toString().isEmpty())
        {
           if(!RegioneditText.getText().toString().isEmpty())
           {
               if(!CitieditText.getText().toString().isEmpty())
               {
                   if(!StreeteditText.getText().toString().isEmpty())
                   {
                       if(!PhoneeditText.getText().toString().isEmpty())
                       {
                           if(!ZipCodeeditText.getText().toString().isEmpty())
                           {
                               if(checkBoxCartPayment.isChecked()||checkBoxCashPayment.isChecked())
                               {
                                   operationOrder();
                               }
                               else
                               {Toast.makeText(this, "Вкажіть спосіб оплати", Toast.LENGTH_SHORT).show();}
                           }
                           else
                           {Toast.makeText(this, "Вкажіть поштовий код", Toast.LENGTH_SHORT).show();}
                       }
                       else
                       {Toast.makeText(this, "Вкажіть номер телефона", Toast.LENGTH_SHORT).show();}
                   }
                   else
                   {Toast.makeText(this, "Вкажіть вулицю для доставки", Toast.LENGTH_SHORT).show();}
               }
               else
               {Toast.makeText(this, "Вкажіть місто для доставки", Toast.LENGTH_SHORT).show();}
           }
           else
           {Toast.makeText(this, "Вкажіть область для доставки", Toast.LENGTH_SHORT).show();}
        }
        else {Toast.makeText(this, "Вкажіть імя замовника", Toast.LENGTH_SHORT).show();}
    }
    void funPlayShop() {
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
            for (CartItemsEntityModel item:CartHelper.getCartItems()) {
                Optional<ProductEntity> prod = LocalProducts.getProducts().stream().filter(s -> Objects.equals(s.getId(), item.getProduct().getId())).findFirst();
                prod.ifPresent(product -> {
                    product.setAddCard(false);
                });
            }
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
        }else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
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
                ClientSecret = object.getString("client_secret");
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
        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                String res = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                Toast.makeText(this, "Тимчасова помилка сервісу з оформленням заявки!!!", Toast.LENGTH_LONG).show();
                finish();

            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

        }
    }

}