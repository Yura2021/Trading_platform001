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

        listProduct.add(new Product("Продукт 1",212,3f,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 2",112,5f,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Подукт 3",122,4f,R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product("Продукт 4",312,1f,R.drawable.ic_main_basket_btn_24));
        listProduct.add(new Product("Продукт 5",152,3f,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 6",912,4.5f,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Продукт 7",142,2.5f,R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product("Продукт 8",172,2f,R.drawable.ic_main_basket_btn_24));
        listProduct.add(new Product("Продукт 9",172,5f,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 10",172,5f,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Продукт 11",152,3.8f,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 12",272,5f,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Продукт 13",372,4.2f,R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product("Продукт 14",672,2.8f,R.drawable.ic_main_basket_btn_24));
        listProduct.add(new Product("Продукт 15",192,5f,R.drawable.ic_main_catalog_btn_24));
        listProduct.add(new Product("Продукт 16",142,3.6f,R.drawable.ic_main_person_btn_24));
        listProduct.add(new Product("Продукт 17",1172,5f,R.drawable.ic_main_home_btn_24));
        listProduct.add(new Product("Продукт 18",1172,5f,R.drawable.ic_main_basket_btn_24));;

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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.item_product, parent, false);
        } else {
            grid = convertView;
        }

        ImageView imgProduct =  grid.findViewById(R.id.imgProduct);
        TextView tvNameProduct =  grid.findViewById(R.id.tvNameProduct);
        TextView tvPriceProduct =  grid.findViewById(R.id.tvPriceProduct);
        RatingBar rbRating =  grid.findViewById(R.id.rbRating);
        TextView tvIdProduct =  grid.findViewById(R.id.tvIdProduct);
        imgProduct.setImageResource(listProduct.get(position).getImg_id());
        tvNameProduct.setText(listProduct.get(position).getName());
        tvPriceProduct.setText(listProduct.get(position).getPrice()+ " Грн");
        rbRating.setRating(listProduct.get(position).getRating());
        tvIdProduct.setText(String.valueOf(position));

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