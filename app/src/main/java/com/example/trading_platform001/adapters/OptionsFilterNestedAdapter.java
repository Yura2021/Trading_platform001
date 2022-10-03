package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.filter_pages.models.GroupElementNested;
import com.example.trading_platform001.interfaces.NestedOnCheckedCheckBoxFilter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionsFilterNestedAdapter extends RecyclerView.Adapter<OptionsFilterNestedAdapter.NestedViewHolder> {

    private final List<GroupElementNested> mList;
    private final Map<String, Boolean> saveCheck;
    private NestedOnCheckedCheckBoxFilter onCheckedCheckBoxFilter;

    public void setOnCheckedCheckBoxFilter(NestedOnCheckedCheckBoxFilter onCheckedCheckBoxFilter) {
        this.onCheckedCheckBoxFilter = onCheckedCheckBoxFilter;
    }

    public OptionsFilterNestedAdapter(List<GroupElementNested> mList, Map<String, Boolean> saveCheck) {
        this.mList = mList;
        this.saveCheck = saveCheck;
    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_filter_nested_item, parent, false);
        return new NestedViewHolder(view);
    }

    String key;

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        key = mList.get(position).getCheckBox().getText().toString();
        holder.mTv.setText(key);
        if (saveCheck.containsKey(key))
            holder.mTv.setChecked(true);
        else
            holder.mTv.setChecked(mList.get(position).getCheckBox().isChecked());
        holder.tvCountElement.setText(mList.get(position).getTextView().getText());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @SuppressLint("NonConstantResourceId")
    public class NestedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nestedItemCb)
        CheckBox mTv;
        @BindView(R.id.tvCountElement)
        TextView tvCountElement;

        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTv.setOnCheckedChangeListener((buttonView, isChecked) -> onCheckedCheckBoxFilter.onCheckedCheckBoxFilter((CheckBox) buttonView, isChecked, tvCountElement));
        }
    }
}
