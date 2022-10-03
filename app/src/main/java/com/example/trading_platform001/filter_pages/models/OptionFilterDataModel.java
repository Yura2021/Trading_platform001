package com.example.trading_platform001.filter_pages.models;

import java.util.List;

public class OptionFilterDataModel {

    private List<GroupElementNested> nestedList;
    private String itemText;
    private boolean isExpandable;

    public OptionFilterDataModel(List<GroupElementNested> itemList, String itemText) {
        this.nestedList = itemList;
        this.itemText = itemText;
        isExpandable = false;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public List<GroupElementNested> getNestedList() {
        return nestedList;
    }

    public String getItemText() {
        return itemText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }
}
