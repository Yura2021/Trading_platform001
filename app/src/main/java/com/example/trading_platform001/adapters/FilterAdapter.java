package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
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

import androidx.appcompat.content.res.AppCompatResources;

import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartEntity;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
import com.example.trading_platform001.interfaces.MyOnClickAddCartItem;
import com.example.trading_platform001.models.ProductEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings("unchecked")
@SuppressLint("NonConstantResourceId")
public class FilterAdapter extends BaseAdapter implements Filterable {
    Context context;

    private ArrayList<HomeValueExProductEntity> listProduct;
    private ArrayList<HomeValueExProductEntity> dataListProduct;

    String url_imgProduct;
    String nameProduct, priceProduct;

    @BindView(R.id.imgProduct)
    ImageView imgProduct;
    @BindView(R.id.tvNameProduct)
    TextView tvNameProduct;
    @BindView(R.id.tvPriceProduct)
    TextView tvPriceProduct;
    CartEntity cart;
    private MyOnClickAddCartItem myOnClickAddCartItem;
    LayoutInflater inflater;
    private Map<String, Boolean> mapItemSearch;

    public void setMyOnClickAddCartItem(MyOnClickAddCartItem myOnClickAddCartItem) {
        this.myOnClickAddCartItem = myOnClickAddCartItem;
    }

    public ArrayList<HomeValueExProductEntity> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<HomeValueExProductEntity> listProduct) {
        this.listProduct = listProduct;
        this.dataListProduct = listProduct;
    }

    public FilterAdapter(Context context, ArrayList<HomeValueExProductEntity> listProduct) {
        this.listProduct = listProduct;
        this.context = context;

        this.dataListProduct = listProduct;
        cart = CartHelper.getCart();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public Map<String, Boolean> getMapItemSearch() {
        return mapItemSearch;
    }

    public void setMapItemSearch(Map<String, Boolean> mapItemSearch) {

        this.mapItemSearch = mapItemSearch;
    }

    @Override
    public int getCount() {
        return listProduct == null ? 0 : listProduct.size();
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

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_filter_item_product, parent, false);
            holder = new ViewHolder();
            holder.ivAddCart = convertView.findViewById(R.id.ivAddCart);
            holder.ivAddCart.setOnClickListener(v -> myOnClickAddCartItem.onClickAddCartItem(v, listProduct.get(position)));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ButterKnife.bind(this, convertView);

        nameProduct = listProduct.get(position).getName();
        priceProduct = String.valueOf(listProduct.get(position).getPrice());
        url_imgProduct = listProduct.get(position).getCover_img();
        ColorStateList csl = null;
        if (listProduct.get(position).isAddCard()) {
            csl = AppCompatResources.getColorStateList(context, R.color.colorPrimary);
        } else {
            csl = AppCompatResources.getColorStateList(context, R.color.color_button_card_of);
        }

        holder.ivAddCart.setImageTintList(csl);

        tvNameProduct.setText(nameProduct);
        tvPriceProduct.setText(priceProduct);
        Picasso.get().load(Uri.parse(url_imgProduct)).into(imgProduct);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    if (mapItemSearch.isEmpty()) {
                        filterResults.count = dataListProduct.size();
                        filterResults.values = dataListProduct;
                    } else {
                        filterResults.count = 0;
                        filterResults.values = null;
                    }

                } else {
                    ArrayList<HomeValueExProductEntity> resultData = new ArrayList<>();
                    ArrayList<HomeValueExProductEntity> resultData2 = new ArrayList<>();
                    String search = constraint.toString().toLowerCase(Locale.ROOT);
                    if (mapItemSearch.isEmpty()) {

                        for (HomeValueExProductEntity item : dataListProduct) {
                            if (item.getName().toLowerCase(Locale.ROOT).contains(search)) {
                                resultData.add(item);
                            }
                        }
                    } else {
                        for (HomeValueExProductEntity item : dataListProduct) {

                            for (Map.Entry<String, Boolean> entry : mapItemSearch.entrySet()) {
                                if (!resultData.contains(item) && item.getNameShop().equals(entry.getKey())) {
                                    resultData.add(item);
                                }
                            }

                        }

                        if (!search.equals(" ")) {

                            for (HomeValueExProductEntity item : resultData) {
                                if (item.getName().toLowerCase(Locale.ROOT).contains(search)) {
                                    resultData2.add(item);
                                }

                            }
                            filterResults.count = resultData2.size();
                            filterResults.values = resultData2;
                            return filterResults;

                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;


                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listProduct = (ArrayList<HomeValueExProductEntity>) results.values;
                notifyDataSetChanged();
            }
        };


    }

    private class ViewHolder {
        ImageView ivAddCart;
    }

}