package com.example.trading_platform001.filter_pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.OptionsFilterItemAdapter;
import com.example.trading_platform001.catalog_page.models.Category;
import com.example.trading_platform001.filter_pages.models.GroupElementNested;
import com.example.trading_platform001.filter_pages.models.OptionFilterDataModel;
import com.example.trading_platform001.filter_pages.models.SaveFilterOption;
import com.example.trading_platform001.home_pages.models.HomeValueExProductEntity;
import com.example.trading_platform001.interfaces.NestedOnCheckedCheckBoxFilter;
import com.example.trading_platform001.models.LocalCategory;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.LocalShops;
import com.example.trading_platform001.models.LocalTableProductCategories;
import com.example.trading_platform001.models.ProductCategoriesEntity;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.models.ShopEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class FilterDialogFragment extends DialogFragment implements NestedOnCheckedCheckBoxFilter {
    View view;
    @BindView(R.id.etFrom)
    EditText etFrom;
    @BindView(R.id.seekBar1)
    SeekBar seekBar1;
    @BindView(R.id.etTo)
    EditText etTo;
    @BindView(R.id.seekBar2)
    SeekBar seekBar2;

    @BindView(R.id.btnClose)
    Button btnClose;
    @BindView(R.id.btnAssept)
    Button btnAssept;
    @BindView(R.id.main_recyclervie)
    RecyclerView recyclerView;
    List<OptionFilterDataModel> mList;
    OptionsFilterItemAdapter adapter;
    ArrayList<HomeValueExProductEntity> listProduct;
    Typeface typeface = Typeface.create("open_sans_bold", Typeface.BOLD);
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_dialog_filter, container, false);

        ButterKnife.bind(this, view);

        btnClose.setOnClickListener(v -> dismiss());
        btnAssept.setOnClickListener(v -> getDiapazone());
        showSeekBar();
        addItemOption();
        bundle = getArguments();
        compliteFilter();
        return view;
    }

    private void getDiapazone() {
        searchDiapazone(etFrom.getText().toString(), etTo.getText().toString());
    }


    @SuppressLint("SetTextI18n")
    private void addItemOption() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(FilterDialogFragment.this.getContext()));
        if (mList == null) {
            mList = new ArrayList<>();
        }
        LocalShops.getShops();
        Context context = view.getContext();
        List<GroupElementNested> sellers = new ArrayList<>();
        int count = 0;
        for (ShopEntity item : LocalShops.getShops()) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(item.getName());
            count = (int) LocalProducts.getProducts().stream().filter(o -> o.getShop_id() == item.getId()).count();
            TextView textView = new TextView(context);
            textView.setText("(" + count + ")");
            sellers.add(new GroupElementNested(checkBox, textView));
        }
        mList.add(new OptionFilterDataModel(sellers, "Інші магазини"));
        //list1
        List<GroupElementNested> nestedList1 = new ArrayList<>();





  /*

        List<LinearLayout> list1 = new ArrayList<>();
        list1.add(addLayautStringCharacteristic("Option atribute1",false));
            mList.add(new OptionFilterDataModel(list1, "Option atribute1 "));
        List<LinearLayout> list2 = new ArrayList<>();
        list2.add(addLayautStringCharacteristic("Option atribute2",false));
            mList.add(new OptionFilterDataModel(list2, "Option atribute2 "));
*/

        adapter = new OptionsFilterItemAdapter(mList, SaveFilterOption.getSaveCheck());
        adapter.setOnCheckedCheckBoxFilter(this);
        recyclerView.setAdapter(adapter);

    }

    private LinearLayout addLayautStringCharacteristic(String name, boolean check) {
        int count = 5;
        LinearLayout linearLayout = new LinearLayout(view.getContext());
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llParam.setMargins(0, 5, 0, 5);
        linearLayout.setLayoutParams(llParam);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white, view.getContext().getTheme()));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(10, 5, 5, 5);
        linearLayout.setWeightSum(count);
        LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        /*android:textColor="#8c8c8c"
        android:textSize="16sp"
        android:textStyle="bold"*/

        for (int i = 0; i < count; i++) {
            CheckBox checkBox = new CheckBox(view.getContext(), null, R.style.MyAppTheme);
            checkBox.setTextColor(Color.parseColor("#8c8c8c"));
            checkBox.setTextSize(16);
            checkBox.setTypeface(typeface);
            checkBox.setText(name + i);
            checkBox.setChecked(check);
            checkBox.setLayoutParams(tvParam);
            checkBox.setGravity(Gravity.LEFT);
            linearLayout.addView(checkBox);
        }
        //TextView tvName = new TextView(view.getContext(), null, R.style.MyAppTheme);

        // tvName.setLayoutParams(tvParam);
        // tvName.setText(name);
        //linearLayout.addView(tvName);

        return linearLayout;

    }

    public static <T extends Comparable<T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    /*
     static boolean isBetween(BigDecimal price, BigDecimal start, BigDecimal end){
        return price.compareTo(start) >= 0 && price.compareTo(end) <= 0;
    }
     */

    private void searchDiapazone(String from, String to) {
        CategoryFilterFragment.productAdapter.setSelectSearch(true);
        if (!from.isEmpty() && !to.isEmpty()) {
            BigDecimal a = new BigDecimal(from);
            BigDecimal b = new BigDecimal(to);
            compliteFilter();
            List<HomeValueExProductEntity> list = CategoryFilterFragment.productAdapter.getListProduct().stream().filter(o -> isBetween(o.getPrice(), a, b)).collect(Collectors.toList());
            Log.d("sasa", " " + "  " + list.size());
            CategoryFilterFragment.productAdapter.getListProduct().clear();
            CategoryFilterFragment.productAdapter.getListProduct().addAll(list);
            CategoryFilterFragment.productAdapter.notifyDataSetChanged();
        }
        // updateAdapter();


    }

    private void showSeekBar() {

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                etFrom.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                etTo.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void updateAdapter() {
        //CategoryFilterFragment.productAdapter.getListProduct();
        CategoryFilterFragment.productAdapter.notifyDataSetChanged();
    }

    public void compliteFilter() {

        if (bundle != null) {
            String nameCategory = bundle.getString("NameParenCategory","no category");
            listProduct = new ArrayList<>();
            Optional<Category> category = LocalCategory.getCategory().stream().filter(s -> Objects.equals(s.getName(), nameCategory)).findFirst();
            if (category.isPresent()) {
                int category_id = category.get().getId();

                List<ProductCategoriesEntity> list = LocalTableProductCategories.getProductCategoriesID().stream().filter(k -> k.getCategory_id() == category_id).collect(Collectors.toList());
                for (ProductCategoriesEntity item : list) {
                    Optional<ProductEntity> prod = LocalProducts.getProducts().stream().filter(s -> s.getId() == item.getProduct_id()).findFirst();
                    if (prod.isPresent()) {
                        Optional<ShopEntity> shopEntity = LocalShops.getShops().stream().filter(i -> i.getId() == prod.get().getShop_id()).findFirst();
                        shopEntity.ifPresent(entity -> listProduct.add(new HomeValueExProductEntity(prod.get(), entity.getName())));

                    }
                }
                Log.d("Id ", " " + category_id);
            }
            CategoryFilterFragment.productAdapter.getListProduct().clear();
            CategoryFilterFragment.productAdapter.getListProduct().addAll(listProduct);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CategoryFilterFragment.productAdapter.setSelectSearch(true);
    }

    @Override
    public void onCheckedCheckBoxFilter(CheckBox checkBox, boolean b, TextView tvCountElement) {
        //  Log.d("Tagggss", " " + b + " Text " + checkBox.getText().toString()+"  count: "+tvCountElement.getText().toString());
        //CategoryFilterFragment.productAdapter.setListProduct((ArrayList)CategoryFilterFragment.productAdapter.getListProduct().stream().filter(o->o.getRating()<3).collect(Collectors.toList()));
        // updateAdapter();
        CategoryFilterFragment.productAdapter.setSelectSearch(false);
        String namecheckBox = checkBox.getText().toString();
        if (b) {
            //saveCheck.put(new GroupElementNested(checkBox,tvCountElement), b);
            SaveFilterOption.getSaveCheck().put(namecheckBox, true);
            CategoryFilterFragment.productAdapter.getFilter().filter(namecheckBox);
            //long id = LocalShops.getShops().stream().filter(o -> Objects.equals(o.getName(), namecheckBox)).collect(Collectors.toList()).get(0).getId();
            // CategoryFilterFragment.productAdapter.setListProduct(CategoryFilterFragment.productAdapter.getListProduct().stream().filter(o -> o.getShop_id() == id).collect(Collectors.toList()));
        } else {
            SaveFilterOption.getSaveCheck().remove(namecheckBox);
            CategoryFilterFragment.productAdapter.getFilter().filter("");
            updateAdapter();
        }
        Log.d("Tagggss", " " + b + " Text " + checkBox.getText().toString() + "  count: " + SaveFilterOption.getSaveCheck().size());


    }
}
