package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
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
import com.example.trading_platform001.carts_pages.models.CartEntity;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.catalog_page.models.Category;
import com.example.trading_platform001.interfaces.MyOnClickAddCartItem;
import com.example.trading_platform001.models.LocalCategory;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.LocalTableProductCategories;
import com.example.trading_platform001.models.ProductCategoriesEntity;
import com.example.trading_platform001.models.ProductEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings("unchecked")
@SuppressLint("NonConstantResourceId")
public class FilterAdapter extends BaseAdapter implements Filterable {
    Context context;

    public ArrayList<ProductEntity> getListProduct() {
        return listProduct;
    }

    public ArrayList<ProductEntity> listProduct;
    public final ArrayList<ProductEntity> dataListProduct;
    View grid;

    long itemId;
    String url_imgProduct;
    String nameProduct, priceProduct;
    float rating;
    boolean favorite;
    int productPosition;
    @BindView(R.id.imgProduct)
    ImageView imgProduct;
    @BindView(R.id.tvNameProduct)
    TextView tvNameProduct;
    @BindView(R.id.tvPriceProduct)
    TextView tvPriceProduct;
    @BindView(R.id.rbRating)
    RatingBar rbRating;
    CartEntity cart;
    private MyOnClickAddCartItem myOnClickAddCartItem;
    LayoutInflater inflater;

    public void setMyOnClickAddCartItem(MyOnClickAddCartItem myOnClickAddCartItem) {
        this.myOnClickAddCartItem = myOnClickAddCartItem;
    }

    public void setListProduct(ArrayList<ProductEntity> listProduct) {
        this.listProduct = listProduct;
    }

    public FilterAdapter(Context context, ArrayList<ProductEntity> dataListProduct) {
        listProduct = dataListProduct;
        if (listProduct == null)
            listProduct = new ArrayList<>();

        this.context = context;

        this.dataListProduct = listProduct;
        cart = CartHelper.getCart();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        itemId = getItem(position).getId();
        productPosition = position;
        favorite = LocalProducts.getProducts().get(productPosition).isFavorite();
        nameProduct = listProduct.get(position).getName();
        priceProduct = String.valueOf(listProduct.get(position).getPrice());
        rating = listProduct.get(position).getRating();
        url_imgProduct = listProduct.get(position).getCover_img();


        tvNameProduct.setText(nameProduct);
        tvPriceProduct.setText(priceProduct);
        rbRating.setRating(rating);
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

    private class ViewHolder {
        ImageView ivAddCart;
    }

}