package com.example.trading_platform001.user_pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.user_pages.models.OrderInformation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserOrderItemActivity extends AppCompatActivity {

    OrderInformation order;
    Bundle bundle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.imageViewBack)
    ImageView imageView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.TTHOrderItem)
    TextView textView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.TextViewName)
    TextView TextViewName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.StatusOrderItem)
    TextView statusOrder;
    View view;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_item);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        order = (OrderInformation) bundle.get("order");

        textView.setText("Номер TTH \n"+ order.getOrder_number().split(" ")[1]);
        TextViewName.setText("№"+ order.getOrder_number().split(" ")[1]);
        statusOrder.setText("Статус:\n"+order.getStatus());
        imageView.setOnClickListener(v->onClick(view));

    }
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        else
            finish();

    }

}