package com.example.trading_platform001.user_pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.main_pages.MainActivity;
import com.example.trading_platform001.R;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.StorageInformation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInformation extends AppCompatActivity {


    StorageInformation storage;
    Context context;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView8)
    TextView textView8;
    Http http;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        context = this;
        ButterKnife.bind(this);
        if (http == null)
            http = new Http(this);
        storage = new StorageInformation(this);

        String name = storage.GetStorage("Name");
        String email = storage.GetStorage("Email");
        textView2.setText("Ім`я\n" + (name.isEmpty() ? "no name" : name));
        textView3.setText("Електроний пошта\n" + (email.isEmpty() ? "no email" : email));
        textView8.setOnClickListener(v -> redirectMainActivity());

    }

    private void redirectMainActivity() {
        CartHelper.getCartItems().clear();
        http.logout();
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

}