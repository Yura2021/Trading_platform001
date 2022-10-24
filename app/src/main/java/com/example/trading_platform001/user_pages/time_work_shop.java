package com.example.trading_platform001.user_pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.trading_platform001.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class time_work_shop extends AppCompatActivity {

    @BindView(R.id.toolbarTimeWork)
    Toolbar toolbar;
    @BindView(R.id.TimeWork)
    TextView textView;
    @BindView(R.id.TimeWork1)
    TextView textView1;
    @BindView(R.id.TimeWork2)
    TextView textView2;
    @BindView(R.id.TimeWork3)
    TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_work_shop);
        ButterKnife.bind(this);

        toolbar.setTitle("Адреса та час роботи");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v->finish());

        textView.setText("Тип магазину\nІнтернет магазин");

        textView1.setText("Графік роботи\nПн-Нд 00:00-23:59");

        textView2.setText("Час відправки товару\nПн-Нд 09:00-18:00");

        textView3.setText("Адреса\nвідсутня");
    }
}