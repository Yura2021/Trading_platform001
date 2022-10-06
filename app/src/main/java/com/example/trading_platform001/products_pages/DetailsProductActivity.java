package com.example.trading_platform001.products_pages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartEntity;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.Product;
import com.example.trading_platform001.products_pages.product_details_fragment.AllInfoProductFragment;
import com.example.trading_platform001.products_pages.product_details_fragment.CharacteristicFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class DetailsProductActivity extends AppCompatActivity {

    @BindView(R.id.btnAddCart)
    Button addCart;
    @BindView(R.id.imgFav)
    ImageView imgFav;
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.llfaworite)
    LinearLayout llfaworite;
    @BindView(R.id.tbDetails)
    Toolbar tbDetails;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    boolean favorite;
    int productPosition;
    Bundle resultData;
    HomeValueExProductEntity resultProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        ButterKnife.bind(this);
        getResultActivity();
        AllInfoProductFragment arg = new AllInfoProductFragment();
        if (resultData != null) {
            replaceFragment(arg, resultData);
        }

        lottieAnimationView.setAnimation("add_to_wish_list.json");
        addCart.setOnClickListener(v -> addCartItem());
        llfaworite.setOnClickListener(v -> aadFavorite());
        tbDetails.setTitle("Детальна інформація");
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_item_menu_details_product_allinfo)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_item_menu_details_product_characteristic)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_item_menu_details_product_feedback)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_item_menu_details_product_question)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_item_menu_details_product_accessories)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new AllInfoProductFragment(), resultData);
                        break;
                    case 1:
                        replaceFragment(new CharacteristicFragment(), resultData);

                        break;
                    case 2:
                        Log.d("tab.getPosition(2)  ", String.valueOf(tab.getPosition()));
                        break;
                    case 3:
                        Log.d("tab.getPosition(3)  ", String.valueOf(tab.getPosition()));
                        break;
                    case 4:
                        Log.d("tab.getPosition(4)  ", String.valueOf(tab.getPosition()));
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tbDetails.inflateMenu(R.menu.button_nav_menu_details_product);

        tbDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        tbDetails.setNavigationOnClickListener(v -> onBackPressed());
        tbDetails.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {

                case R.id.allInfo:
                    replaceFragment(new AllInfoProductFragment(), resultData);
                    break;
                case android.R.id.home:
                    Log.d("tab.getPosition(4)  " + item.getItemId() + "  ", String.valueOf(android.R.id.home));
                    break;
                case R.id.characteristic:

                    break;
                case R.id.feedback:

                    break;
                case R.id.question:

                    break;
                case R.id.accessories:

                    break;
            }
            return true;
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fcDetailsInfo, fragment);
        fragmentTransaction.commit();
    }

    private void getResultActivity() {

        resultData = getIntent().getExtras();
        if (resultData != null) {
            resultProduct = resultData.getParcelable("ParceHomeValueExProductEntity");
            if (resultProduct.isFavorite()) {
                imgFav.setImageResource(R.drawable.ic_is_favorite);
            } else {
                imgFav.setImageResource(R.drawable.ic_is_not_favorite);
            }
        }

    }


    private void addCartItem() {

        if (resultProduct != null) {
            CartEntity cart = CartHelper.getCart();
            Product product = new Product(resultProduct);
            cart.add(product, 1);
            Toast.makeText(this, "Товар доданий у кошик", Toast.LENGTH_SHORT).show();
        }

    }

    private void aadFavorite() {

        if (favorite) {
            favorite = false;
            lottieAnimationView.setVisibility(View.GONE);
            imgFav.setVisibility(View.VISIBLE);
            imgFav.setImageResource(R.drawable.ic_is_not_favorite);
        } else {
            favorite = true;
            imgFav.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
        }
        LocalProducts.getProducts().get(productPosition).setFavorite(favorite);

    }
}