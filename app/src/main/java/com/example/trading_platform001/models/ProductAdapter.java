package com.example.trading_platform001.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.trading_platform001.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter implements Filterable {
    Context context;
    public List<Product> listProduct;
    public final List<Product> dataListProduct;

    public ProductAdapter(Context context) {
        this.context = context;
        listProduct = new ArrayList<>();

        listProduct.add(new Product(0, "Продукт 1", BigDecimal.valueOf(212), 3f, R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product(1, "Продукт 2", BigDecimal.valueOf(112), 5f, R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product(2, "Подукт 3", BigDecimal.valueOf(122), 4f, R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product(3, "Продукт 4", BigDecimal.valueOf(312), 1f, R.drawable.ic_main_basket_btn_24));
        listProduct.add(new Product(4, "Продукт 5", BigDecimal.valueOf(152), 3f, R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product(5, "Продукт 6", BigDecimal.valueOf(912), 4.5f, R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product(6, "Продукт 7", BigDecimal.valueOf(142), 2.5f, R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product(7, "Продукт 8", BigDecimal.valueOf(172), 2f, R.drawable.ic_main_basket_btn_24));
        listProduct.add(new Product(8, "Продукт 9", BigDecimal.valueOf(172), 5f, R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product(9, "Продукт 10", BigDecimal.valueOf(172), 5f, R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product(10, "Продукт 11", BigDecimal.valueOf(152), 3.8f, R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product(11, "Продукт 12", BigDecimal.valueOf(272), 5f, R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product(12, "Продукт 13", BigDecimal.valueOf(372), 4.2f, R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product(13, "Продукт 14", BigDecimal.valueOf(672), 2.8f, R.drawable.ic_main_basket_btn_24));
        listProduct.add(new Product(14, "Продукт 15", BigDecimal.valueOf(192), 5f, R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product(15, "Продукт 16", BigDecimal.valueOf(142), 3.6f, R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product(16, "Продукт 17", BigDecimal.valueOf(1172), 5f, R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product(17, "Продукт 18", BigDecimal.valueOf(1172), 5f, R.drawable.ic_main_basket_btn_24));

        this.dataListProduct = listProduct;
    }


    @Override
    public int getCount() {

        return listProduct.size();
    }

    @Override
    public Product getItem(int position) {
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.item_product, parent, false);
        } else {
            grid = convertView;
        }

        ImageView imgProduct = grid.findViewById(R.id.imgProduct);
        TextView tvNameProduct = grid.findViewById(R.id.tvNameProduct);
        TextView tvPriceProduct = grid.findViewById(R.id.tvPriceProduct);
        RatingBar rbRating = grid.findViewById(R.id.rbRating);
        TextView positionProduct = grid.findViewById(R.id.tvIdProduct);
        imgProduct.setImageResource(listProduct.get(position).getImg_id());
        tvNameProduct.setText(listProduct.get(position).getName());
        tvPriceProduct.setText(String.valueOf(listProduct.get(position).getPrice()));
        rbRating.setRating(listProduct.get(position).getRating());
        positionProduct.setText(String.valueOf(position));

        return grid;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = dataListProduct.size();
                    filterResults.values = dataListProduct;
                } else {
                    String search = constraint.toString().toLowerCase();
                    List<Product> resultData = new ArrayList<>();
                    for (Product item : dataListProduct) {
                        if (item.getName().toLowerCase(Locale.ROOT).contains(search)) {
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