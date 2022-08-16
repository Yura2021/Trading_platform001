package com.example.trading_platform001;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    EditText edEmail,edPassword;
    Button btnLogin;
    String strEmail, strPassword;
    LocalStorage localStorage;

    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        edEmail = view.findViewById(R.id.edEmail);
        edPassword = view.findViewById(R.id.edPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        localStorage = new LocalStorage(requireActivity());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        return view;

    }
    private void checkLogin() {
        strEmail= edEmail.getText().toString();
        strPassword = edPassword.getText().toString();
        if(strPassword.isEmpty()||strEmail.isEmpty()){
            alertFail("Необхідно вказати адресу електронної пошти та пароль");
        }
        else{
            sendLogin();
        }
    }

    private void sendLogin() {
        btnLogin.setEnabled(false);
        JSONObject params = new JSONObject();
        try {
            params.put("email",strEmail);
            params.put("password",strPassword);

        }catch (JSONException ex){
            ex.printStackTrace();
            btnLogin.setEnabled(true);
        }
        String data = params.toString();
        String url = getString(R.string.api_server)+"/login";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(requireActivity(),url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String token = response.getString("token");
                                Log.d("Http response token:  ",token);
                                localStorage.setToken(token);
                                Intent intent = new Intent(requireActivity(),MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(requireActivity(),"Успіх",Toast.LENGTH_SHORT).show();
                                btnLogin.setEnabled(true);
                                Thread.sleep(5000);
                                requireActivity().finish();


                            }catch (JSONException | InterruptedException ex){
                                ex.printStackTrace();
                                btnLogin.setEnabled(true);
                            }

                        }
                        else if(code ==422){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            }catch (JSONException ex){
                                ex.printStackTrace();
                                btnLogin.setEnabled(true);
                            }
                        }
                        else if(code ==401){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            }catch (JSONException ex){
                                ex.printStackTrace();
                                btnLogin.setEnabled(true);
                            }
                        }
                        else{
                            Toast.makeText(requireActivity(),"Error "+code,Toast.LENGTH_SHORT).show();
                            btnLogin.setEnabled(true);
                        }
                    }
                });
            }
        }).start();
    }

    private void alertFail(String s) {
        btnLogin.setEnabled(true);
        new AlertDialog.Builder(this.getContext())
                .setTitle("Не вдалося")
                .setIcon(R.drawable.ic_warning)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


}