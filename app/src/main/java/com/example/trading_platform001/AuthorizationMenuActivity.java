package com.example.trading_platform001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AuthorizationMenuActivity extends AppCompatActivity {
    BottomNavigationView btnNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_menu);
        replaceFragment(new LoginFragment());

        btnNavView = findViewById(R.id.buttonMenuAuthorization);

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
