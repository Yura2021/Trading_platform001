package com.example.trading_platform001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.LocalStorage;
import com.example.trading_platform001.models.StorageInformation;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInformation extends AppCompatActivity {


    StorageInformation Storage;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        context=this;
        Storage= new StorageInformation(this);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);

        textView2.setText("Ім`я\n"+Storage.GetStorage("Name"));
        textView3.setText("Електроний пошта\n"+Storage.GetStorage("Email"));
        TextView textView8 = (TextView) findViewById(R.id.textView8);
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.ClearStorage();
                startActivity(new Intent(context,MainActivity.class));
            }
        });
    }


}