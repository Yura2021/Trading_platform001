package com.example.trading_platform001;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.JsonParser;
import com.example.trading_platform001.models.LocalStorage;
import com.example.trading_platform001.models.StorageInformation;
import com.example.trading_platform001.models.User;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    EditText edEmail, edPassword, edDialogEmail;
    Button btnLogin;
    String strEmail, strPassword;
    LocalStorage localStorage;
    TextView recPass;
    Dialog dialog;
    User user;
    StorageInformation Storage;

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
            recPassDialogShow();
        });
        dialog = new Dialog(requireActivity());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        user = new User();
        Storage = new StorageInformation(getContext());
        return view;

    }

    private void checkLogin() {
        strEmail = edEmail.getText().toString();
        strPassword = edPassword.getText().toString();
        if (strPassword.isEmpty() || strEmail.isEmpty()) {
            alertFail("Необхідно вказати адресу електронної пошти та пароль");
        } else {
            sendLogin();
        }
    }

    private void sendLogin() {
        btnLogin.setEnabled(false);
        edEmail.setEnabled(false);
        edPassword.setEnabled(false);
        JSONObject params = new JSONObject();
        try {
            params.put("email", strEmail);
            params.put("password", strPassword);

        } catch (JSONException ex) {
            ex.printStackTrace();
            alertFail("");
        }
        String data = params.toString();
        String url = getString(R.string.api_server) + "/login";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(requireActivity(), url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String token = response.getString("token");
                                //Update library org.json
                                user = JsonParser.getClassUser(response);
                                Storage.SetStorageUser(user);
                                Storage.SetStorage("Password",strPassword);
                                localStorage.setToken(token);
                                Intent intent = new Intent(requireActivity(), MainActivity.class);
                                Toast.makeText(requireActivity(), "Успіх", Toast.LENGTH_SHORT).show();
                                Thread.sleep(5000);
                                startActivity(intent);
                                alertFail("");
                                requireActivity().finish();


                            } catch (JSONException | InterruptedException ex) {
                                ex.printStackTrace();
                                alertFail("");
                            }

                        } else if (code == 422) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                                alertFail("");
                            }
                        } else if (code == 401) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                                alertFail("");
                            }
                        } else {
                            Toast.makeText(requireActivity(), "Error " + code, Toast.LENGTH_SHORT).show();
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
                    })
                    .show();
    }

    private void recPassDialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_recover_password_dialog, null);
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
                        sendRecPass();
                    }
                });

        builder.show();

    }

    private void sendRecPass() {

        strEmail = edDialogEmail.getText().toString();

        JSONObject params = new JSONObject();
        try {
            params.put("email", strEmail);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        String data = params.toString();
        String url = getString(R.string.api_server) + "/forgot-password";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(requireActivity(), url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String token = response.getString("token");
                                localStorage.setToken(token);
                                Intent intent = new Intent(requireActivity(), MainActivity.class);
                                Toast.makeText(requireActivity(), "Лист для відновлення паролю відправлений", Toast.LENGTH_SHORT).show();
                                Thread.sleep(5000);
                                startActivity(intent);
                                requireActivity().finish();


                            } catch (JSONException | InterruptedException ex) {
                                ex.printStackTrace();
                            }

                        } else if (code == 422) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        } else if (code == 401) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            Toast.makeText(requireActivity(), "Error " + code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }
}