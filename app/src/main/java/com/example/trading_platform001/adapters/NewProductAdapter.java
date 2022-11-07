package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartEntity;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.models.ProductEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings("unchecked")
@SuppressLint("NonConstantResourceId")
public class NewProductAdapter extends BaseAdapter {
    Context context;
    public ArrayList<ProductEntity> listProduct;
    public final ArrayList<ProductEntity> dataListProduct;
    View grid;

    long itemId;
    String url_imgProduct;
    String nameProduct, priceProduct;
    int productPosition;
    @BindView(R.id.imgProduct)
    ImageView imgProduct;
    @BindView(R.id.tvNameProduct)
    TextView tvNameProduct;
    @BindView(R.id.tvPriceProduct)
    TextView tvPriceProduct;
    CartEntity cart;


    public NewProductAdapter(Context context, ArrayList<ProductEntity> dataListProduct) {
        this.context = context;
        listProduct = dataListProduct;
        if (listProduct == null)
            listProduct = new ArrayList<>();

        this.dataListProduct = listProduct;
        cart = CartHelper.getCart();

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
        if (convertView == null) {
            grid = new View(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.item_product, parent, false);
        } else {
            grid = convertView;
        }
        ButterKnife.bind(this, grid);
        itemId = getItem(position).getId();
        productPosition = position;
        nameProduct = listProduct.get(position).getName();
        priceProduct = String.valueOf(listProduct.get(position).getPrice());
        url_imgProduct = listProduct.get(position).getCover_img();

        tvNameProduct.setText(nameProduct);
        tvPriceProduct.setText(priceProduct);
        Picasso.get().load(Uri.parse(url_imgProduct)).into(imgProduct);

        return grid;
    }

}