package com.example.trading_platform001.authorizations_pages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trading_platform001.R;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.StorageInformation;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class EmailVerificationActivity extends AppCompatActivity {
    @BindView(R.id.tbVerification)
    Toolbar tbVerification;
    @BindView(R.id.edCode)
    EditText edCode;
    @BindView(R.id.btnVerification)
    Button btnVerification;
    @BindView(R.id.btnSendCode)
    Button btnSendCode;
    Http http;
    StorageInformation storageInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        ButterKnife.bind(this);
        http = new Http(this, this);
        storageInformation = new StorageInformation(this);
        tbVerification.setTitle("Активація пошти");
        tbVerification.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        tbVerification.setNavigationOnClickListener(v -> onBackPressed());
        btnVerification.setOnClickListener(v -> verificationEmail());
        btnSendCode.setOnClickListener(v -> sendCode());
    }

    public void verificationEmail() {
        if(!edCode.getText().toString().isEmpty()){
            if(storageInformation.GetStorage("vrcode").trim().equals(edCode.getText().toString().trim())){
                http.sendActivateEmail();
            }else {
                alertFailToast("Не вірний код!!!");
            }

        }else {
            alertFailToast("В ведіть код!!!");
        }


    }

    public void sendCode() {
        if(storageInformation.GetStorage("email_verified_at").isEmpty()){
            http.sendVerificationCode();
        }else {
            alertFailToast("Пошта вже підтверджена!!!");
        }

    }

    public void alertSuccessToast(String s) {

        edCode.setEnabled(true);
        btnVerification.setEnabled(true);
        btnSendCode.setEnabled(false);
        LayoutInflater inflater = getLayoutInflater();
        View view_Warn = inflater.inflate(R.layout.warninng_toast_layout, this.findViewById(R.id.relativeLayout1), false);
        TextView textView = view_Warn.findViewById(R.id.tvText);
        ImageView imageView = view_Warn.findViewById(R.id.ivImage);
        imageView.setImageResource(R.drawable.ic_check);
        textView.setText(s);
        Toast toast = new Toast(this);
        toast.setView(view_Warn);
        toast.show();


    }

    public void alertFailToast(String s) {
        /*
        edCode.setEnabled(true);
        btnVerification.setEnabled(true);
        btnSendCode.setEnabled(false);

         */
        if (!s.isEmpty()) {
            LayoutInflater inflater = getLayoutInflater();
            View view_Warn = inflater.inflate(R.layout.warninng_toast_layout, this.findViewById(R.id.relativeLayout1), false);
            TextView textView = view_Warn.findViewById(R.id.tvText);
            textView.setText(s);
            Toast toast = new Toast(this);
            toast.setView(view_Warn);
            toast.show();
        }
    }
}