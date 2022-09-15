package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.example.trading_platform001.user_pages.models.ItemOrder;
import com.example.trading_platform001.user_pages.models.Order;
import com.example.trading_platform001.user_pages.models.OrderInformation;
import com.example.trading_platform001.user_pages.models.OrderList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderUserListAdapter extends ArrayAdapter<Order> implements Filterable {

    private ArrayList<Order> orderInformations;
    private ArrayList<Order> filterInformations;
    private Activity context;
    private Calendar calendar;
    private ModelFilter filter;
    private LayoutInflater inflater;

    public OrderUserListAdapter(Activity context, int textViewResourceId,ArrayList<Order> orderInformations) {
        super(context, textViewResourceId,orderInformations);
        this.context = context;
        this.calendar= Calendar.getInstance();
        this.orderInformations=new ArrayList<>();
        this.orderInformations.addAll(orderInformations);
        this.filterInformations=new ArrayList<>();
        this.filterInformations.addAll(this.orderInformations);
        inflater = context.getLayoutInflater();
        getFilter();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new ModelFilter();
        }
        return filter;
    }

    static class ViewHolder {
        protected TextView id;
        protected TextView data;
        protected TextView status;
        protected TextView price;
        protected ImageView image;
        protected TextView text;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=null;
        ViewHolder viewHolder =null;
        if(convertView == null)
        {
            view= inflater.inflate(R.layout.list_order_item_template,null);
            viewHolder = new ViewHolder();
            viewHolder.id = view.findViewById(R.id.id_orders);
            viewHolder.data = view.findViewById(R.id.data_orders);
            viewHolder.status = view.findViewById(R.id.statys_orders);
            viewHolder.price = view.findViewById(R.id.ordersSum);
            viewHolder.image = view.findViewById(R.id.OrderImage);
            viewHolder.text = view.findViewById(R.id.OrdertextView);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder= ((ViewHolder) view.getTag());
        }
        viewHolder.id.setText("№ "+this.filterInformations.get(position).getOrder().getOrder_number().split(" ")[1]);
        calendar.setTime(this.filterInformations.get(position).getOrder().getCreated_at());
        switch(calendar.get(Calendar.MONTH))
        {
            case 0: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" січеня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 1: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" лютого "+calendar.get(Calendar.YEAR));
                break;
            }
            case 2: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" береня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 3: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" квітня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 4: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" травня "+calendar.get(Calendar.YEAR));
                break;
            }case 5: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" чеврня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 6: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" липня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 7: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" серпня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 8: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" вересня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 9: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" жовтня "+calendar.get(Calendar.YEAR));
                break;
            }
            case 10: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" листопада "+calendar.get(Calendar.YEAR));
                break;
            }
            case 11: {
                viewHolder.data.setText((calendar.get(Calendar.DAY_OF_MONTH)+1)+" грудня "+calendar.get(Calendar.YEAR));
                break;
            }
        }
        if(this.filterInformations.get(position).getOrder().getStatus().equals("pending"))
        {
            viewHolder.status.setText("Оплачено");
        }
        else
        {
            viewHolder.status.setText("Не оплачено");
        }

        viewHolder.price.setText(this.filterInformations.get(position).getOrder().getGrand_total() + "₴");
        viewHolder.text.setText("Сума до сплати:");
        if(filterInformations.get(position).getItemOrder().get(0).getCover_img()!=null)
        {
            Picasso.get().load(Uri.parse(filterInformations.get(position).getItemOrder().get(0).getCover_img())).into(viewHolder.image);

        }

        return view;
    }
    public void updateReceiptsList(OrderList orderList) {
        this.orderInformations.clear();
        this.orderInformations.addAll(orderList.getOrders());
        this.filterInformations.clear();
        this.filterInformations.addAll(orderList.getOrders());
        this.notifyDataSetChanged();
        getFilter().filter("-");
    }

    private class ModelFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint.toString().length() > 0)
            {
                ArrayList<Order> filteredItems = new ArrayList<Order>();

                for(int i = 0, l = orderInformations.size(); i < l; i++)
                {
                    if(orderInformations.get(i).getOrder().getOrder_number().split(" ")[1].toLowerCase().contains(constraint))
                        filteredItems.add(orderInformations.get(i));
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = orderInformations;
                    result.count = orderInformations.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filterInformations = (ArrayList<Order>) results.values;
            notifyDataSetChanged();
            clear();
           addAll(filterInformations);
            notifyDataSetInvalidated();
        }
    }
}
