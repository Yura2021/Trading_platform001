package com.example.trading_platform001.models;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public static User getClassUser(JSONObject response) throws JSONException {
        JSONObject userJson = response.getJSONObject("user");
        ///long id = userJson.getLong("id");
        //int role_id = userJson.getInt("role_id");
        String name = userJson.getString("name");
        String email = userJson.getString("email");
        // String imageUrl = userJson.getString("profile_image_url");
        return new User(name, email);
    }
}
