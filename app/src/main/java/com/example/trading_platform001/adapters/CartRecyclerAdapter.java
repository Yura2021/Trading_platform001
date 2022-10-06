package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.carts_pages.models.CartItemsEntityModel;
import com.example.trading_platform001.interfaces.MyOnItemClickListener;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<CartItemsEntityModel> productEntityModel;
    private MyOnItemClickListener onItemClickListener;

    public CartRecyclerAdapter(List<CartItemsEntityModel> productEntityModel) {

        this.productEntityModel = productEntityModel;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItem(int position, CartItemsEntityModel cartItemsEntityModel) {
        if (cartItemsEntityModel.getQuantity() > 0) {
            productEntityModel.set(position, cartItemsEntityModel);
            CartHelper.getCart().update(cartItemsEntityModel.getProduct(), cartItemsEntityModel.getQuantity());
        } else {
            CartHelper.getCart().remove(productEntityModel.get(position).getProduct());
            onItemClickListener.onUpdateList();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ReceiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
        viewHolder.name.setText(productEntityModel.get(position).getProduct().getName());
        viewHolder.description.setText(productEntityModel.get(position).getProduct().getDescription());
        BigDecimal a = productEntityModel.get(position).getProduct().getPrice();
        BigDecimal b = new BigDecimal(productEntityModel.get(position).getQuantity());

        viewHolder.price.setText(String.valueOf(a.multiply(b)));
        viewHolder.quantity.setText(String.valueOf(productEntityModel.get(position).getQuantity()));
        Picasso.get().load(Uri.parse(productEntityModel.get(position).getProduct().getCover_img())).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return productEntityModel.size();
    }

    @SuppressLint("NonConstantResourceId")
    public class ReceiveViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.tvQuantity)
        TextView quantity;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.minus)
        ImageButton minus;
        @BindView(R.id.plus)
        ImageButton plus;

        public ReceiveViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            //itemView.setOnClickListener(v -> onItemClickListener.onItemClick(productEntityModel.get(getBindingAdapterPosition())));
            minus.setOnClickListener(v -> onItemClickListener.onItemMinusClicked(getBindingAdapterPosition(), productEntityModel));
            plus.setOnClickListener(v -> onItemClickListener.onItemPlusClicked(getBindingAdapterPosition(), productEntityModel));

        }
    }

    public void setOnItemClickListener(MyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
