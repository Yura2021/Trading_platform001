package com.example.trading_platform001.authorizations_pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.trading_platform001.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
@SuppressLint("NonConstantResourceId")
public class AuthorizationMenuActivity extends AppCompatActivity {

    @BindView(R.id.buttonMenuAuthorization)
    BottomNavigationView btnNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_menu);
        replaceFragment(new LoginFragment());
        ButterKnife.bind(this);

        btnNavView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.authorization:
                    replaceFragment(new LoginFragment());
                    break;
                case R.id.registration:
                    replaceFragment(new RegistrationFragment());
                    break;
            }
            return true;
        });


    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_view_fragment1, fragment);
        fragmentTransaction.commit();
    }
}
