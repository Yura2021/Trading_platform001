package com.example.trading_platform001.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trading_platform001.R;
import com.example.trading_platform001.user_pages.models.Order;

import java.util.List;

public class OrderUserListAdapter extends ArrayAdapter<Order> implements Filterable {
    Drawable[] OrderImage;
    List<Order> orderList;
    public final List<Order> dataListProduct;
    Context context;


    public OrderUserListAdapter(Context context, int textViewResourceId,List<Order> orderList) {
        super(context, textViewResourceId,orderList);
        this.context = context;
        this.orderList = orderList;
        this.dataListProduct=orderList;
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

        id.setText("№ "+this.orderList.get(position).getOrder_number().split(" ")[1].toString());
        data.setText(this.orderList.get(position).getCreated_at().toString());
        if(this.orderList.get(position).getStatus().equals("pending"))
        {
            status.setText("Оплачено");
        }
        else
        {
            status.setText("Не оплачено");
        }

        price.setText(this.orderList.get(position).getGrand_total() + "₴");
        text.setText("Сума до сплати:");
        if (this.OrderImage == null) {
            image.setImageResource(R.drawable.ic_launcher_background);
        } else {
            image.setImageDrawable(this.OrderImage[position]);
        }
        return row;
    }

    public void updateReceiptsList(List<Order> orderList) {
        this.clear();
        this.orderList.clear();
        this.orderList.addAll(orderList);
        this.notifyDataSetChanged();
    }
    /* public void SearchItem(String text)
    {
        this.dataListProduct.clear();
        this.dataListProduct.addAll(dataListProduct);
        List<Order> temp = new ArrayList<>();



            this.orderList.clear();
            this.orderList.addAll(temp);
            this.notifyDataSetChanged();

    }*/
}
