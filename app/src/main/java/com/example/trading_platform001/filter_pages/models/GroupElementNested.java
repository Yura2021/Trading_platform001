package com.example.trading_platform001.filter_pages.models;

import android.widget.CheckBox;
import android.widget.TextView;

public class GroupElementNested {
    CheckBox checkBox;
    TextView textView;
    public GroupElementNested(CheckBox checkBox, TextView textView) {
        this.checkBox = checkBox;
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

}
