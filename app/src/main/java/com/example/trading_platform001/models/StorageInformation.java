package com.example.trading_platform001.models;

import android.content.Context;
import android.content.SharedPreferences;

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
    }

    public String GetStorage(String Key) {
        return sharedpreferences.getString(Key, null);
    }

    public void ClearStorage() {
        editor.clear();
        editor.commit();
    }

    public Boolean IsEmpty() {
        if (sharedpreferences.getString("Name", null) == null) {
            return false;
        } else {
            return true;
        }
    }
}
