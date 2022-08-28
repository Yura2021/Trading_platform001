package com.example.trading_platform001.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trading_platform001.R;

import java.math.BigDecimal;
import java.util.List;

public class CartRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<CartItemsEntityModel> productEntityModel;
    private OnItemClickListener onItemClickListener;
    private final Context context;

    public CartRecyclerAdapter(Context context, List<CartItemsEntityModel> productEntityModel) {
        this.context = context;
        this.productEntityModel = productEntityModel;
    }

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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ReceiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
        viewHolder.name.setText(productEntityModel.get(position).getProduct().getName());
        viewHolder.description.setText(productEntityModel.get(position).getProduct().getDescription());
        BigDecimal a = productEntityModel.get(position).getProduct().getPrice();
        BigDecimal b = new BigDecimal(productEntityModel.get(position).getQuantity());

        viewHolder.price.setText(String.valueOf(a.multiply(b)));
        viewHolder.quantity.setText(String.valueOf(productEntityModel.get(position).getQuantity()));
        viewHolder.image.setImageResource(productEntityModel.get(position).getProduct().getImg_id());
    }

    @Override
    public int getItemCount() {
        return productEntityModel.size();
    }

    public class ReceiveViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView description, name, quantity, price;
        final Button minus, plus;

        public ReceiveViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            quantity = view.findViewById(R.id.quantity);
            price = view.findViewById(R.id.price);
            description = view.findViewById(R.id.description);
            minus = view.findViewById(R.id.minus);
            plus = view.findViewById(R.id.plus);

            itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(productEntityModel.get(getBindingAdapterPosition()));
            });
            minus.setOnClickListener(v -> {
                onItemClickListener.onItemMinusClicked(getBindingAdapterPosition(), productEntityModel.get(getBindingAdapterPosition()));
            });
            plus.setOnClickListener(v -> {
                onItemClickListener.onItemPlusClicked(getBindingAdapterPosition(), productEntityModel.get(getBindingAdapterPosition()));
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CartItemsEntityModel cartItemsEntityModel);

        void onItemPlusClicked(int position, CartItemsEntityModel cartItemsEntityModel);

        void onItemMinusClicked(int position, CartItemsEntityModel cartItemsEntityModel);

        void onUpdateList();
    }
}
