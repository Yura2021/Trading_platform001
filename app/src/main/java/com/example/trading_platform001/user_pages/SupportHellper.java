package com.example.trading_platform001.user_pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.trading_platform001.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SupportHellper extends AppCompatActivity {

    @BindView(R.id.toolbarSupportHellper)
    Toolbar toolbar;
    @BindView(R.id.SupportHellper)
    TextView textView;
    @BindView(R.id.SupportHellper1)
    TextView textView1;
    @BindView(R.id.SupportHellper2)
    TextView textView2;
    @BindView(R.id.SupportHellper3)
    TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_hellper);
        ButterKnife.bind(this);

        toolbar.setTitle("Служба підтримки");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v->finish());

        textView.setText("Номер Телефону\n+380-996-363-125");

        textView1.setText("Графік роботи\nПн-Нд 09:00-18:00");

        textView2.setText("Онлайн звязок\nЧерез сайт");

        textView3.setText("Графік роботи\nПн-Нд 00:00-23:59");
    }
}