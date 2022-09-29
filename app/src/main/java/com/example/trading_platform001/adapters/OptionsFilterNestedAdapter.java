package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trading_platform001.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionsFilterNestedAdapter extends RecyclerView.Adapter<OptionsFilterNestedAdapter.NestedViewHolder> {

    private List<String> mList;

    public OptionsFilterNestedAdapter(List<String> mList){
        this.mList = mList;
    }
    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_filter_nested_item , parent , false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.mTv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nestedItemTv)
        TextView mTv;
        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
