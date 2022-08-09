package com.example.trading_platform001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ImageButton yourButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         //yourButton = findViewById(R.id.button1);

    }

    public void onClickButton(View v) {
/*
        Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.image_test).mutate();
        mDrawable.setColorFilter(new PorterDuffColorFilter(R.color.green, PorterDuff.Mode.SRC_IN));
        yourButton.setCompoundDrawables(null, mDrawable, null, null);

        Drawable mDrawable = ContextCompat.getDrawable(yourButton.getContext(),R.drawable.image_test);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(Color.GREEN,PorterDuff.Mode.SRC_IN));
        yourButton.setCompoundDrawables(null, mDrawable, null, null);
         */
        //yourButton.setColorFilter(Color.GREEN);
    }

}