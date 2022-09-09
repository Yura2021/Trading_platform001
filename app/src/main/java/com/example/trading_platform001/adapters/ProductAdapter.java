package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
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
import com.example.trading_platform001.models.ProductEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings("unchecked")
@SuppressLint("NonConstantResourceId")
public class ProductAdapter extends BaseAdapter implements Filterable {
    Context context;
    public ArrayList<ProductEntity> listProduct;
    public final ArrayList<ProductEntity> dataListProduct;
    View grid;

    @BindView(R.id.imgProduct)
    ImageView imgProduct;
    @BindView(R.id.tvNameProduct)
    TextView tvNameProduct ;
    @BindView(R.id.tvPriceProduct)
    TextView tvPriceProduct ;
    @BindView(R.id.rbRating)
    RatingBar rbRating ;
    @BindView(R.id.tvIdProduct)
    TextView positionProduct;

    public ProductAdapter(Context context, ArrayList<ProductEntity> dataListProduct) {
        this.context = context;
        listProduct = dataListProduct;
        if (listProduct == null)
            listProduct = new ArrayList<>();

        this.dataListProduct = listProduct;
    }


    @Override
    public int getCount() {

        return listProduct.size();
    }

    @Override
    public ProductEntity getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub


        if (convertView == null) {
            grid = new View(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.item_product, parent, false);
        } else {
            grid = convertView;
        }
        ButterKnife.bind(this,grid);

        tvNameProduct.setText(listProduct.get(position).getName());
        tvPriceProduct.setText(String.valueOf(listProduct.get(position).getPrice()));
        rbRating.setRating(listProduct.get(position).getRating());
        positionProduct.setText(String.valueOf(position));
        Picasso.get().load(Uri.parse(listProduct.get(position).getCover_img())).into(imgProduct);
        return grid;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = dataListProduct.size();
                    filterResults.values = dataListProduct;
                } else {
                    String search = constraint.toString().toLowerCase();
                    ArrayList<ProductEntity> resultData = new ArrayList<>();
                    for (ProductEntity item : dataListProduct) {
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
                listProduct = (ArrayList<ProductEntity>) results.values;
                notifyDataSetChanged();
            }
        };

    }
}