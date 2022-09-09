package com.example.trading_platform001.authorizations_pages;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trading_platform001.R;
import com.example.trading_platform001.models.Http;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class RegistrationFragment extends Fragment {
    @BindView(R.id.edName)
    EditText edName;
    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.edPassword)
    EditText edPassword;
    @BindView(R.id.edConfirmation)
    EditText edConfirmation;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    String strName, strEmail, strPassword, strConfirmation;
    View view;
    Http http;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this, view);
        btnRegister.setOnClickListener(v -> checkRegister());
        http = new Http(view.getContext());
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
        http.sendRegister(strName,strEmail,strPassword,strConfirmation);
        alertSuccess();

    }

    private void alertSuccess() {

        btnRegister.setEnabled(true);
        edName.setEnabled(true);
        edEmail.setEnabled(true);
        edPassword.setEnabled(true);
        edConfirmation.setEnabled(true);
        new AlertDialog.Builder(this.getContext())
                .setTitle("Успіх")
                .setIcon(R.drawable.ic_check)
                .setMessage("Успішна реєстрація.")
                .setPositiveButton("OK", (dialog, which) -> requireActivity().onBackPressed()).show();


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
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).show();

    }

}