package com.example.trading_platform001.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trading_platform001.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class MenuUserListAdapter extends ArrayAdapter<String> {

    String[] itemName;
    Drawable[] itemIcon;
    Context context;
    @BindView(R.id.text_view_menu)
    TextView label;
    @BindView(R.id.image_view_icon)
    ImageView iconImageView;

    public MenuUserListAdapter(Context context, int textViewResourceId, String[] itemName, Drawable[] itemIcon) {
        super(context, textViewResourceId, itemName);
        this.context = context;
        this.itemName = itemName;
        this.itemIcon = itemIcon;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder")
        View row = inflater.inflate(R.layout.listmenu, parent, false);
        ButterKnife.bind(this,row);
        label.setText(itemName[position]);
        iconImageView.setImageDrawable(itemIcon[position]);

        return row;
    }

}
