package com.example.trading_platform001;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.trading_platform001.databinding.ActivityMainBinding;
import com.example.trading_platform001.models.DataAdapter;
import com.example.trading_platform001.models.FullImageActivity;
import com.example.trading_platform001.models.ImageTextAdapter;

public class MainActivity extends AppCompatActivity  {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
            }
            return true;
        });
*/
        ///GridView с картинками и пояснительным текстом
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageTextAdapter(this));

        gridview.setOnItemClickListener(gridviewOnItemClickListener);



    }
    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {

            // Sending image id to FullScreenActivity
            Intent i = new Intent(getApplicationContext(),
                    FullImageActivity.class);
            // passing array index
            i.putExtra("id", position);
            startActivity(i);
        }
    };
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_view_fragment,fragment);
        fragmentTransaction.commit();
    }


}