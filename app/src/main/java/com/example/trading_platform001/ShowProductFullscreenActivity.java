package com.example.trading_platform001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShowProductFullscreenActivity extends AppCompatActivity {
    ImageView imgProduct;
    TextView tvNameProduct,tvPriceProduct,tvIdProduct;
    RatingBar rbRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_fullscreen);

        Intent i = getIntent();
        imgProduct =  findViewById(R.id.imgProduct);
        tvNameProduct =  findViewById(R.id.tvNameProduct);
        tvPriceProduct =  findViewById(R.id.tvPriceProduct);
        rbRating =  findViewById(R.id.rbRating);
        tvIdProduct =  findViewById(R.id.tvIdProduct);
        int id = i.getIntExtra("id",-1);
        int id_imgProduct = i.getIntExtra("imgProduct", -1);
        if(id!=-1){
          String NameProduct =  i.getStringExtra("tvNameProduct");
          String PriceProduct =  i.getStringExtra("tvPriceProduct");
          float rating = i.getFloatExtra("rbRating",0f);
          String IdProduct =  i.getStringExtra("tvIdProduct");
            if(id_imgProduct!=-1)
          imgProduct.setImageResource(id_imgProduct);
          tvNameProduct.setText(NameProduct);
          tvPriceProduct.setText(PriceProduct);
          rbRating.setRating(rating);
          tvIdProduct.setText(IdProduct);


        }

    }
}