package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.filter_pages.models.GroupElementNested;
import com.example.trading_platform001.filter_pages.models.OptionFilterDataModel;
import com.example.trading_platform001.interfaces.NestedOnCheckedCheckBoxFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionsFilterItemAdapter extends RecyclerView.Adapter<OptionsFilterItemAdapter.ItemViewHolder>  {

    List<OptionFilterDataModel> mList;
    List<GroupElementNested> list;
    private Map<String, Boolean> saveCheck;
    private NestedOnCheckedCheckBoxFilter onCheckedCheckBoxFilter;

    public void setOnCheckedCheckBoxFilter(NestedOnCheckedCheckBoxFilter onCheckedCheckBoxFilter) {
        this.onCheckedCheckBoxFilter = onCheckedCheckBoxFilter;
    }

    public OptionsFilterItemAdapter() {
        list = new ArrayList<>();
    }

    public OptionsFilterItemAdapter(List<OptionFilterDataModel> mList,Map<String, Boolean> saveCheck) {
        list = new ArrayList<>();
        this.mList = mList;
        this.saveCheck=saveCheck;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_filter_each_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        OptionFilterDataModel model = mList.get(position);
        holder.mTextView.setText(model.getItemText());

        boolean isExpandable = model.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable) {
            holder.mArrowImage.setImageResource(R.drawable.arrow_up);
        } else {
            holder.mArrowImage.setImageResource(R.drawable.arrow_down);
        }

        OptionsFilterNestedAdapter adapter = new OptionsFilterNestedAdapter(list,saveCheck);
        adapter.setOnCheckedCheckBoxFilter(onCheckedCheckBoxFilter);
        holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.nestedRecyclerView.setAdapter(adapter);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setExpandable(!model.isExpandable());
                list = model.getNestedList();
                notifyItemChanged(holder.getBindingAdapterPosition());
                Log.d("OptionsFilterNestedAdapter ","saveCheck.size:  "+saveCheck.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




    @SuppressLint("NonConstantResourceId")
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.linear_layout)
        LinearLayout linearLayout;
        @BindView(R.id.expandable_layout)
        LinearLayout expandableLayout;
        @BindView(R.id.itemTv)
        TextView mTextView;
        @BindView(R.id.arro_imageview)
        ImageView mArrowImage;
        @BindView(R.id.child_rv)
        RecyclerView nestedRecyclerView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
