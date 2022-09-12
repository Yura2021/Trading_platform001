package com.example.trading_platform001.user_pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.user_pages.models.Order;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserOrderItemActivity extends AppCompatActivity {

    Order order;
    Bundle bundle;
    @BindView(R.id.imageViewBack)
    ImageView imageView;
    @BindView(R.id.TTHOrderItem)
    TextView textView;
    @BindView(R.id.TextViewName)
    TextView TextViewName;
    @BindView(R.id.StatusOrderItem)
    TextView statusOrder;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_item);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        order = (Order) bundle.get("order");

        textView.setText("Номер TTH \n"+order.getOrder_number().split(" ")[1].toString());
        TextViewName.setText("№"+order.getOrder_number().split(" ")[1].toString());
        statusOrder.setText("Статус:\n"+order.getStatus());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0)
                    fm.popBackStack();
                else
                 finish();

            }
        });

    }
}