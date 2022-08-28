package com.example.trading_platform001;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trading_platform001.models.CartHelper;
import com.example.trading_platform001.models.StorageInformation;

public class UserInformation extends AppCompatActivity {


    StorageInformation Storage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        context = this;
        Storage = new StorageInformation(this);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);

        textView2.setText("Ім`я\n" + Storage.GetStorage("Name"));
        textView3.setText("Електроний пошта\n" + Storage.GetStorage("Email"));
        TextView textView8 = (TextView) findViewById(R.id.textView8);
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.ClearStorage();
                CartHelper.getCartItems().clear();
                startActivity(new Intent(context, MainActivity.class));
            }
        });
    }


}