package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trading_platform001.R;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.user_pages.models.ItemOrder;
import com.example.trading_platform001.user_pages.models.OrderInformation;
import com.example.trading_platform001.user_pages.models.OrderList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrderUserListAdapter extends ArrayAdapter<OrderInformation> implements Filterable {

    List<OrderInformation> orderInformations;
    ArrayList<ArrayList<ItemOrder>> itemOrders;
    Context context;
    Calendar calendar;


    public OrderUserListAdapter(Context context, int textViewResourceId,List<OrderInformation> orderInformations,  ArrayList<ArrayList<ItemOrder>> itemOrders) {
        super(context, textViewResourceId,orderInformations);
        this.context = context;
        this.orderInformations = orderInformations;
        this.itemOrders = itemOrders;
        this.calendar= Calendar.getInstance();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder")
        View row = inflater.inflate(R.layout.list_order_item_template, parent, false);
        TextView id = row.findViewById(R.id.id_orders);
        TextView data = row.findViewById(R.id.data_orders);
        TextView status = row.findViewById(R.id.statys_orders);
        TextView price = row.findViewById(R.id.ordersSum);
        ImageView image = row.findViewById(R.id.OrderImage);
        TextView text = row.findViewById(R.id.OrdertextView);

        id.setText("№ "+this.orderInformations.get(position).getOrder_number().split(" ")[1]);
        calendar.setTime(this.orderInformations.get(position).getCreated_at());
        switch(calendar.get(Calendar.MONTH))
        {
            case 0: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" січеня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 1: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" лютого "+calendar.get(Calendar.YEAR));
                break;
            }
            case 2: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" береня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 3: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" квітня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 4: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" травня "+calendar.get(Calendar.YEAR));
                break;
            }case 5: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" чеврня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 6: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" липня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 7: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" серпня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 8: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" вересня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 9: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" жовтня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 10: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" листопада "+calendar.get(Calendar.YEAR));
                break;
            }
            case 11: {
                data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" грудня "+calendar.get(Calendar.YEAR));
                break;
            }
        }
        if(this.orderInformations.get(position).getStatus().equals("pending"))
        {
            status.setText("Оплачено");
        }
        else
        {
            status.setText("Не оплачено");
        }

        price.setText(this.orderInformations.get(position).getGrand_total() + "₴");
        text.setText("Сума до сплати:");
        if(itemOrders.get(position).get(0).getCover_img()==null)
        {

        }
        else
        {
            Picasso.get().load(Uri.parse(itemOrders.get(position).get(0).getCover_img())).into(image);
        }
        return row;
    }

    public void updateReceiptsList(OrderList orderList) {
        this.orderInformations.clear();
        this.itemOrders.addAll(orderList.GetAllItemOrder());
        this.orderInformations.addAll(orderList.GetAllOrderInformation());
        this.notifyDataSetChanged();
    }
    public void updateReceiptsList(List<OrderInformation> OrderInformation) {

        this.clear();
        this.orderInformations.clear();
        this.orderInformations.addAll(OrderInformation);
        this.notifyDataSetChanged();
    }
    public void searchItemList(String text,List<OrderInformation> orderList)
    {
        ArrayList<OrderInformation> orderInformations_= new ArrayList<>();
        if(!text.isEmpty()) {
            String str = "OrederNumber " + text;
            for (int i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).getOrder_number().contains(str)) {
                    orderInformations_.add(orderList.get(i));
                }
            }
            this.updateReceiptsList(orderInformations_);
        }
    }

    public List<OrderInformation> getOrderInformations() {
        return orderInformations;
    }
}
