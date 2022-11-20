package com.example.trading_platform001.products_pages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.trading_platform001.R;
import com.example.trading_platform001.carts_pages.models.CartEntity;
import com.example.trading_platform001.carts_pages.models.CartHelper;
import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.Product;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.products_pages.product_details_fragment.AllInfoProductFragment;
import com.example.trading_platform001.products_pages.product_details_fragment.CharacteristicFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;
import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class DetailsProductActivity extends AppCompatActivity {

    @BindView(R.id.btnAddCart)
    Button addCart;
    @BindView(R.id.tbDetails)
    Toolbar tbDetails;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
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

        addCart.setOnClickListener(v -> addCartItem());
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_item_menu_details_product_allinfo)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_item_menu_details_product_characteristic)));
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

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tbDetails.setTitle("Детальна інформація");
        tbDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        tbDetails.setNavigationOnClickListener(v -> onBackPressed());

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
        }

    }
    public void alertAddCartToast() {

        LayoutInflater inflater = getLayoutInflater();
        View view_Warn = inflater.inflate(R.layout.warninng_toast_layout, (ViewGroup) this.findViewById(R.id.relativeLayout1), false);
        TextView textView = view_Warn.findViewById(R.id.tvText);
        ImageView imageView = view_Warn.findViewById(R.id.ivImage);
        imageView.setImageResource(R.drawable.ic_baseline_shopping_cart_green_24);
        textView.setText("Товар доданий у кошик");
        Toast toast = new Toast(this);
        toast.setView(view_Warn);
        toast.show();


    }

    private void addCartItem() {

        if (resultProduct != null) {
            CartEntity cart = CartHelper.getCart();

            if (!resultProduct.isAddCard()) {
                Optional<ProductEntity> prod = LocalProducts.getProducts().stream().filter(s -> Objects.equals(s.getId(), resultProduct.getId())).findFirst();
                prod.ifPresent(product -> {
                    product.setAddCard(true);
                    resultProduct.setAddCard(true);
                });
            }

            Product product = new Product(resultProduct);
            cart.add(product, 1);
            alertAddCartToast();
        } else {
            Toast.makeText(this, "Увага помилка!!!", Toast.LENGTH_SHORT).show();
        }

    }

}