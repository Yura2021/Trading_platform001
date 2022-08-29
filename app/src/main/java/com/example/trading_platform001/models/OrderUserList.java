package com.example.trading_platform001.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trading_platform001.R;

import java.sql.Time;

public class OrderUserList extends ArrayAdapter<String> {

    String[] OrderId;
    Time[] OrderTime;
    String[] OrderStatus;
    Drawable[] OrderImage;
    int[] PriceOrder;

    Context context;

    public OrderUserList(Context context, int textViewResourceId, String[] OrderId,Time[] OrderTime,String[] OrderStatus,int[] PriceOrder) {
        super(context, textViewResourceId, OrderId);
        this.context = context;
        this.OrderId = OrderId;
        this.OrderTime=OrderTime;
        this.OrderStatus=OrderStatus;
        this.PriceOrder=PriceOrder;
    }
    public OrderUserList(Context context, int textViewResourceId, String[] OrderId,Time[] OrderTime,String[] OrderStatus,int[] PriceOrder,Drawable[] OrderImage) {
        super(context, textViewResourceId, OrderId);
        this.context = context;
        this.OrderId = OrderId;
        this.OrderTime=OrderTime;
        this.OrderStatus=OrderStatus;
        this.PriceOrder=PriceOrder;
        this.OrderImage=OrderImage;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_order_item_template, parent, false);
        TextView id = (TextView) row.findViewById(R.id.id_orders);
        TextView data = (TextView) row.findViewById(R.id.data_orders);
        TextView status = (TextView) row.findViewById(R.id.statys_orders);
        TextView price = (TextView) row.findViewById(R.id.ordersSum);
        ImageView image = (ImageView) row.findViewById(R.id.OrderImage);
        TextView text = (TextView) row.findViewById(R.id.OrdertextView);

        id.setText(this.OrderId[position]);
        data.setText(this.OrderTime[position].toString());
        status.setText(this.OrderStatus[position]);
        price.setText(this.PriceOrder[position]+"₴");
        text.setText("Оплачено:");
        if(this.OrderImage==null)
        {
            image.setImageResource(R.drawable.ic_launcher_background);
        }
        else
        {
            image.setImageDrawable(this.OrderImage[position]);
        }

       // iconImageView.setImageDrawable(itemIcon[position]);

        return row;
    }
}
