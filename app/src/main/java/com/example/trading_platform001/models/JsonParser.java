package com.example.trading_platform001.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public static ArrayList getClassOrder(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.optJSONArray("order");
        ArrayList<Order> orderArrayList= new ArrayList<>();
        if(jsonArray!=null) {
            for (int i = 0;i<jsonArray.length(); i++) {
             orderArrayList.add(new Order(jsonArray.getJSONObject(i).getString("order_number").split(" ")[1].toString()
                     ,jsonArray.getJSONObject(i).getInt("grand_total")
                     ,new Time(10)
                     ,jsonArray.getJSONObject(i).getString("status")));
            }
        }
        return orderArrayList;
    }
}
