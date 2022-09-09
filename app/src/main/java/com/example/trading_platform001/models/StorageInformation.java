package com.example.trading_platform001.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.trading_platform001.authorizations_pages.models.User;

public class StorageInformation {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    static final String MyPREFERENCES = "MyPrefs";

    public StorageInformation(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        this.editor = sharedpreferences.edit();
    }

    public void SetStorage(String Key, String Value) {
        this.editor.putString(Key, Value);
        this.editor.commit();
    }

    public void SetStorageUser(User user) {
        this.SetStorage("Name", user.getName());
        this.SetStorage("Email", user.getEmail());
        this.SetStorage("Remember_token", user.getRemember_token());
    }

    public String GetStorage(String Key) {
        return sharedpreferences.getString(Key, null);
    }

    public void ClearStorage() {
        editor.clear();
        editor.commit();
    }

    public Boolean IsEmpty() {
        return sharedpreferences.getString("Name", null) != null;
    }
}
