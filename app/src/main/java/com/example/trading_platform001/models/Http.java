package com.example.trading_platform001.models;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Http {
    Context context;
    private String method = "GET", data = null, response = null;
    private final String url;
    private Integer statusCode = 0;
    private Boolean token = false;
    private final LocalStorage localStorage;

    public Http(Context context, String url) {
        this.context = context;
        this.url = url;
        this.localStorage = new LocalStorage(context);
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setToken(Boolean token) {
        this.token = token;
    }

    public Context getContext() {
        return context;
    }

    public String getResponse() {
        return response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStringToken() {
        return localStorage.getToken();
    }

    public void send() {
        try {
            URL sUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) sUrl.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            if (token) {
                String strToken = localStorage.getToken();
                if (strToken.isEmpty())
                    strToken = "No token";
                connection.setRequestProperty("Authorization", "Bearer " + strToken);
            }
            if (data != null) {
                OutputStream os = connection.getOutputStream();
                os.write(data.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

            }
            statusCode = connection.getResponseCode();
            InputStreamReader isr;
            if (statusCode >= 200 && statusCode <= 299) {
                //success response
                isr = new InputStreamReader(connection.getInputStream());
            } else {
                // Error response
                isr = new InputStreamReader(connection.getErrorStream());
            }
            BufferedReader br = new BufferedReader(isr);
            //StringBuffer
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            response = sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
