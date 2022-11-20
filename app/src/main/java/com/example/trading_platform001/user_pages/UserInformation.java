package com.example.trading_platform001.user_pages;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.main_pages.MainActivity;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.StorageInformation;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class UserInformation extends AppCompatActivity {

    StorageInformation storage;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.toolbarAccaunt)
    Toolbar toolbar;
    Http http;
    String strEmail;
    View dView;
    EditText edDialogEmail;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        ButterKnife.bind(this);

        if (http == null)
            http = new Http(this);

        storage = new StorageInformation(this);

        String name = storage.GetStorage("Name");
        String email = storage.GetStorage("Email");

        textView2.setText("Ім`я\n" + (name.isEmpty() ? "no name" : name));
        textView3.setText("Електроний пошта\n" + (email.isEmpty() ? "no email" : email));
        toolbar.setTitle("Обліковий запис");

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        textView8.setOnClickListener(v -> redirectMainActivity());
        toolbar.setNavigationOnClickListener(v -> onClick());
        textView7.setOnClickListener(v -> recPassDialogShow());
    }

    private void redirectMainActivity() {
        CartHelper.getCart().clear();
        http.logout();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void onClick() {
        finish();
    }

    private void recPassDialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        if (dView == null)
            dView = inflater.inflate(R.layout.fragment_recover_password_dialog, new LinearLayout(this), false);
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
        Intent intent = new Intent(this, MainActivity.class);
        Toast.makeText(this, "Лист для відновлення паролю відправлений", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
        alertFail("");

    }

    private void alertFail(String s) {

        if (!s.isEmpty())
            new AlertDialog.Builder(this)
                    .setTitle("Не вдалося")
                    .setIcon(R.drawable.ic_warning)
                    .setMessage(s)
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
    }
}