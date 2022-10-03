package com.example.trading_platform001.filter_pages.models;

import java.util.HashMap;
import java.util.Map;

public class SaveFilterOption {
    private static Map<String, Boolean> saveCheck;

    public static Map<String, Boolean> getSaveCheck() {
        if (saveCheck == null)
            saveCheck = new HashMap<>();
        return saveCheck;
    }

    public static void setSaveCheck(Map<String, Boolean> saveCheck) {
        SaveFilterOption.saveCheck = saveCheck;
    }
}
