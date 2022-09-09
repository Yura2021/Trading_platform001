package com.example.trading_platform001.authorizations_pages;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trading_platform001.main_pages.MainActivity;
import com.example.trading_platform001.R;
import com.example.trading_platform001.models.Http;


import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class LoginFragment extends Fragment {
    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.edPassword)
    EditText edPassword;
    @BindView(R.id.recPass)
    TextView recPass;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    String strEmail, strPassword;
    Dialog dialog;
    EditText edDialogEmail;
    View view;
    View dView;
    Http http;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null)
            view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);
            http = new Http(view.getContext());
        recPass.setOnClickListener(v -> recPassDialogShow(container));
        if (dialog == null)
            dialog = new Dialog(requireActivity());
        btnLogin.setOnClickListener(v -> checkLogin());
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
            http.sendLogin(strEmail, strPassword);

        Intent intent = new Intent(requireActivity(), MainActivity.class);
        //Toast.makeText(getContext(), "Успіх", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        alertFail("");
        requireActivity().finish();


    }

    private void alertFail(String s) {
        btnLogin.setEnabled(true);
        edEmail.setEnabled(true);
        edPassword.setEnabled(true);
        if (!s.isEmpty())
            new AlertDialog.Builder(getContext())
                    .setTitle("Не вдалося")
                    .setIcon(R.drawable.ic_warning)
                    .setMessage(s)
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
    }

    private void recPassDialogShow(ViewGroup container) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        if (dView == null)
            dView = inflater.inflate(R.layout.fragment_recover_password_dialog, container,false);
        if (edDialogEmail == null)
            edDialogEmail = dView.findViewById(R.id.edDialogEmail);
        builder.setTitle("Відновлення паролю");
        builder.setView(dView).setNegativeButton(R.string.cancel, (dialog, id) -> {
                })
                .setPositiveButton(R.string.restore, (dialog, id) -> sendRecPass());
        builder.show();

    }

    private void sendRecPass() {
        strEmail = edDialogEmail.getText().toString();
        http.sendRecPass(strEmail);
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        Toast.makeText(requireActivity(), "Лист для відновлення паролю відправлений", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        requireActivity().finish();
        alertFail("");

    }

}