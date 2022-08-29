package com.example.trading_platform001.models;

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

public class MenuUserListAdapter extends ArrayAdapter<String> {

    String[] itemName;
    Drawable[] itemIcon;
    Context context;


    public MenuUserListAdapter(Context context, int textViewResourceId, String[] itemName) {
        super(context, textViewResourceId, itemName);
        this.context = context;
        this.itemName = itemName;
    }

    public MenuUserListAdapter(Context context, int textViewResourceId, String[] itemName, Drawable[] itemIcon) {
        super(context, textViewResourceId, itemName);
        this.context = context;
        this.itemName = itemName;
        this.itemIcon = itemIcon;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_menu_template, parent, false);
        TextView label = (TextView) row.findViewById(R.id.text_view_menu);
        ImageView iconImageView = (ImageView) row.findViewById(R.id.image_view_icon);
        label.setText(itemName[position]);
        iconImageView.setImageDrawable(itemIcon[position]);

        return row;
    }

}
