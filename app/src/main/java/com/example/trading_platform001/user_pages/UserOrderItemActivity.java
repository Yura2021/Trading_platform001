package com.example.trading_platform001.user_pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.trading_platform001.R;
import com.example.trading_platform001.user_pages.models.ItemOrder;
import com.example.trading_platform001.user_pages.models.OrderInformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
@SuppressLint("NonConstantResourceId")
public class UserOrderItemActivity extends AppCompatActivity {

    OrderInformation order;
    Bundle bundle;

    @BindView(R.id.DataOrder)
    TextView textView;
    @BindView(R.id.NomerTTH)
    TextView NomerTTH;
    @BindView(R.id.PriceOrder)
    TextView PriceOrder;
    @BindView(R.id.PaymentOrder)
    TextView PaymentOrder;
    @BindView(R.id.AdressOrder)
    TextView AdressOrder;
    @BindView(R.id.RecipientOrder)
    TextView RecipientOrder;
    @BindView(R.id.PhoneOrder)
    TextView PhoneOrder;
    @BindView(R.id.OrderItem)
    LinearLayout OrderItem;
    @BindView(R.id.toolbarItemOrder)
    Toolbar toolbar;
    View view;
    List<ItemOrder> itemOrderList;
    Calendar calendar;
    ViewGroup viewGroup;
    LayoutInflater inflater;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_item);
        ButterKnife.bind(this);
        viewGroup = new LinearLayout(this);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        calendar= Calendar.getInstance();
        bundle = getIntent().getExtras();
        order = (OrderInformation) bundle.get("order");
        itemOrderList = (ArrayList<ItemOrder>)bundle.get("orderitem");
        ShowInformation();
        ShowData(textView);
        ShowItemOrder();

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> onClick(view));
    }
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        else
            finish();

    }
    @SuppressLint("SetTextI18n")
    public void ShowInformation()
    {
        toolbar.setTitle("???"+ order.getOrder_number().split(" ")[1]);
        NomerTTH.setText("?????????? TTH \n"+ order.getOrder_number().split(" ")[1]);
        PriceOrder.setText("?????????? ???? ????????????: "+order.getGrand_total()+" ???");
        if(order.getPayment_method().equals("paypal"))
        {
            PaymentOrder.setText("???????????? ????????????:\n"+order.getPayment_method());
        }
        else
        {
            PaymentOrder.setText("???????????? ????????????:\n???????????? ?????? ?????? ?????????????????? ????????????");
        }
        AdressOrder.setText("?????????? ????????????????:\n"+order.getAddress());
        RecipientOrder.setText("??????????????????:\n"+order.getShipping_fullname());
        PhoneOrder.setText("?????????????? ????????????????????:\n"+order.getShipping_phone());
    }

    @SuppressLint("SetTextI18n")
    public void ShowItemOrder()
    {
        for(int i = 0 ; i<itemOrderList.size();i++)
        {
            View row = inflater.inflate(R.layout.order_item_template,viewGroup, false);
            TextView name = row.findViewById(R.id.NameOrderItemTemplate);
            TextView count = row.findViewById(R.id.CountOrderItemTemplate);
            TextView price = row.findViewById(R.id.OrderPriceItemTemplate);
            ImageView image = row.findViewById(R.id.ImageOrderItemTemplate);


            name.setText(itemOrderList.get(i).getName());
            count.setText(itemOrderList.get(i).getQuantity()+" ????.");
            price.setText(itemOrderList.get(i).getPrice()+" ???");
            Picasso.get().load(Uri.parse(itemOrderList.get(i).getCover_img())).into(image);
            OrderItem.addView(row);
        }
    }

    @SuppressLint("SetTextI18n")
    public void ShowData(TextView textView)
    {
        calendar.setTime(this.order.getCreated_at());
        switch(calendar.get(Calendar.MONTH))
        {
            case 0: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ???????????? "+calendar.get(Calendar.YEAR));
                break;
            }
            case 1: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ???????????? "+calendar.get(Calendar.YEAR));
                break;
            }
            case 2: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ?????????????? "+calendar.get(Calendar.YEAR));
                break;
            }
            case 3: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ???????????? "+calendar.get(Calendar.YEAR));
                break;
            }
            case 4: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ???????????? "+calendar.get(Calendar.YEAR));
                break;
            }case 5: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ???????????? "+calendar.get(Calendar.YEAR));
            break;
        }
            case 6: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ?????????? "+calendar.get(Calendar.YEAR));
                break;
            }
            case 7: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ???????????? "+calendar.get(Calendar.YEAR));
                break;
            }
            case 8: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ?????????????? "+calendar.get(Calendar.YEAR));
                break;
            }
            case 9: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ???????????? "+calendar.get(Calendar.YEAR));
                break;
            }
            case 10: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ?????????????????? "+calendar.get(Calendar.YEAR));
                break;
            }
            case 11: {
                textView.setText("???????? ????????????????????:\n"+(calendar.get(Calendar.DAY_OF_MONTH)+1)+" ???????????? "+calendar.get(Calendar.YEAR));
                break;
            }
        }

    }
}