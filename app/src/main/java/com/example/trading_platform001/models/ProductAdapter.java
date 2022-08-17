package com.example.trading_platform001.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.trading_platform001.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter implements Filterable {
    Context context;
    public List<Product> listProduct;
    public final List<Product> dataListProduct;

    public ProductAdapter(Context context,List<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
        this.dataListProduct = listProduct;
    }


    @Override
    public int getCount() {

        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View grid;
        if (convertView == null) {
            grid = new View(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.item_product, parent, false);
        } else {
            grid = convertView;
        }

        ImageView imageView =  grid.findViewById(R.id.imgProduct);
        TextView textView =  grid.findViewById(R.id.tvNameProduct);
        TextView textView2 =  grid.findViewById(R.id.tvIdProduct);
        imageView.setImageResource(listProduct.get(position).getImg_id());
        textView.setText(listProduct.get(position).getName());
        textView2.setText(String.valueOf(position));

        return grid;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null ||constraint.length() == 0){
                    filterResults.count = dataListProduct.size();
                    filterResults.values = dataListProduct;
                }
                else{
                    String search = constraint.toString().toLowerCase();
                    List<Product> resultData = new ArrayList<>();
                    for (Product item:dataListProduct) {
                        if(item.getName().toLowerCase(Locale.ROOT).contains(search)){
                            resultData.add(item);
                        }
                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listProduct = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}