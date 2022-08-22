package com.example.trading_platform001.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trading_platform001.AuthorizationMenuActivity;
import com.example.trading_platform001.R;
import com.example.trading_platform001.UserInformation;

public class MenuUserListAdapter extends ArrayAdapter<String> {

    String[] itemName;
    int[] itemIcon;
    Context context;


    public MenuUserListAdapter(Context context, int textViewResourceId, String[] itemName) {
        super(context, textViewResourceId, itemName);
        this.context = context;
        this.itemName = itemName;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listmenu, parent, false);
        TextView label = (TextView) row.findViewById(R.id.text_view_menu);
        ImageView iconImageView = (ImageView) row.findViewById(R.id.image_view_icon);
        label.setText(itemName[position]);

        switch (itemName[position]) {
            case "Вхід": {
               // iconImageView.setImageResource(R.drawable.dbc8ca135fe568c03bd135dc5c134453);
                break;
            }
            case "Обліковий запис": {
                iconImageView.setImageResource(R.drawable.my_accaunt_ico);
                break;
            }
            case "Мої замовлення":
            {
                iconImageView.setImageResource(R.drawable.my_orders_ico);
                break;
            }
            case "1":
            {
                break;
            }
            case "2":
            {
                break;
            }
            case "3":
            {
                break;
            }
        }
        return row;
    }

}
