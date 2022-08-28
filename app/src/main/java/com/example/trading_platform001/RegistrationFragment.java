package com.example.trading_platform001;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.trading_platform001.models.Http;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistrationFragment extends Fragment {

    EditText edName, edEmail, edPassword, edConfirmation;
    Button btnRegister;
    String strName, strEmail, strPassword, strConfirmation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        edName = view.findViewById(R.id.edName);
        edEmail = view.findViewById(R.id.edEmail);
        edPassword = view.findViewById(R.id.edPassword);
        edConfirmation = view.findViewById(R.id.edConfirmation);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRegister();
            }
        });
        return view;
    }

    private void checkRegister() {
        strName = edName.getText().toString();
        strEmail = edEmail.getText().toString();
        strPassword = edPassword.getText().toString();
        strConfirmation = edConfirmation.getText().toString();
        if (strName.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty()) {
            alertFail("Необхідно вказати ім’я, адресу електронної пошти та пароль.");
        } else if (!strPassword.equals(strConfirmation)) {
            alertFail("Пароль і підтвердження пароля не збігаються.");
        } else {
            sendRegister();
        }
    }

    private void sendRegister() {
        edName.setEnabled(false);
        edEmail.setEnabled(false);
        edPassword.setEnabled(false);
        edConfirmation.setEnabled(false);
        btnRegister.setEnabled(false);
        JSONObject params = new JSONObject();
        try {
            params.put("name", strName);
            params.put("email", strEmail);
            params.put("password", strPassword);
            params.put("password_confirmation", strPassword);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        String data = params.toString();

        String url = getString(R.string.api_server) + "/register";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(RegistrationFragment.this.getContext(), url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 201 || code == 200) {
                            alertSuccess("Успішна реєстрація.");
                        } else if (code == 422) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                                alertFail("");
                            }
                        } else {
                            Toast.makeText(RegistrationFragment.this.getContext(), "Error " + code, Toast.LENGTH_SHORT).show();
                            alertFail("");
                        }
                    }
                });
            }

        }).start();

    }

    private void alertSuccess(String s) {

        btnRegister.setEnabled(true);
        edName.setEnabled(true);
        edEmail.setEnabled(true);
        edPassword.setEnabled(true);
        edConfirmation.setEnabled(true);
        new AlertDialog.Builder(this.getContext())
                .setTitle("Успіх")
                .setIcon(R.drawable.ic_check)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requireActivity().onBackPressed();
                    }
                }).show();


    }

    private void alertFail(String s) {
        btnRegister.setEnabled(true);
        edName.setEnabled(true);
        edEmail.setEnabled(true);
        edPassword.setEnabled(true);
        edConfirmation.setEnabled(true);
        if (!s.isEmpty())
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Не вдалося")
                    .setIcon(R.drawable.ic_warning)
                    .setMessage(s)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

    }

}