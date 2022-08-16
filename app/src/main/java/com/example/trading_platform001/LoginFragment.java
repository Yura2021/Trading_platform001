package com.example.trading_platform001;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    EditText edEmail,edPassword,edDialogEmail;
    Button btnLogin;
    String strEmail, strPassword;
    LocalStorage localStorage;
    TextView recPass;
    Dialog dialog;
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
        recPass = view.findViewById(R.id.recPass);
        localStorage = new LocalStorage(requireActivity());
        recPass.setOnClickListener(v -> {
            recoveryPassword();
        });
        dialog = new Dialog(requireActivity());
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
        edEmail.setEnabled(false);
        edPassword.setEnabled(false);
        JSONObject params = new JSONObject();
        try {
            params.put("email",strEmail);
            params.put("password",strPassword);

        }catch (JSONException ex){
            ex.printStackTrace();
            alertFail("");
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
                                alertFail("");
                                Thread.sleep(5000);
                                requireActivity().finish();


                            }catch (JSONException | InterruptedException ex){
                                ex.printStackTrace();
                                alertFail("");
                            }

                        }
                        else if(code ==422){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            }catch (JSONException ex){
                                ex.printStackTrace();
                                alertFail("");
                            }
                        }
                        else if(code ==401){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            }catch (JSONException ex){
                                ex.printStackTrace();
                                alertFail("");
                            }
                        }
                        else{
                            Toast.makeText(requireActivity(),"Error "+code,Toast.LENGTH_SHORT).show();
                            alertFail("");
                        }
                    }
                });
            }
        }).start();
    }

    private void alertFail(String s) {
        btnLogin.setEnabled(true);
        edEmail.setEnabled(true);
        edPassword.setEnabled(true);
        if(!s.isEmpty())
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
    private void recoveryPassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.fragment_recover_password_dialog, null);
        edDialogEmail = view.findViewById(R.id.edDialogEmail);
        builder.setTitle("Відновлення паролю");
        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        edEmail.setText(edDialogEmail.getText().toString());
                    }
                });

         builder.show();

    }

}