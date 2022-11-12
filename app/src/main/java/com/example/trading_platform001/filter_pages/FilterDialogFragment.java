package com.example.trading_platform001.filter_pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.example.trading_platform001.models.AttributeValuesEntity;
import com.example.trading_platform001.models.AttributesEntity;
import com.example.trading_platform001.models.LocalAttributes;
import com.example.trading_platform001.models.LocalCategory;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.LocalShops;
import com.example.trading_platform001.models.LocalTableProductCategories;
import com.example.trading_platform001.models.ProductAtribute;
import com.example.trading_platform001.models.ProductCategoriesEntity;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.models.ShopEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    String nameCategory;
    List<HomeValueExProductEntity> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_dialog_filter, container, false);

        ButterKnife.bind(this, view);

        btnClose.setOnClickListener(v -> dismiss());
        btnAssept.setOnClickListener(v -> getDiapazone());


        bundle = getArguments();
        if (bundle != null) {
            nameCategory = bundle.getString("NameParenCategory", "no category");
        }
        showSeekBar();
        compliteFilter();
        addItemOption();
        return view;
    }

    private void getDiapazone() {
        searchDiapazone(etFrom.getText().toString(), etTo.getText().toString());
    }

    long count = 0;

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

        for (ShopEntity item : LocalShops.getShops()) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(item.getName());
            count = listProduct.stream().filter(o -> o.getShop_id() == item.getId()).count();
            TextView textView = new TextView(context);
            textView.setText("(" + count + ")");
            sellers.add(new GroupElementNested(checkBox, textView));
        }
        mList.add(new OptionFilterDataModel(sellers, "Інші магазини"));
        List<GroupElementNested> attValues;
        for (AttributesEntity attItem : LocalAttributes.getAttributes()) {
            attValues = new ArrayList<>();
            for (AttributeValuesEntity attValue : LocalAttributes.getAttributeValues()) {
                if (attItem.getId() == attValue.getAttribute_id()) {
                    CheckBox checkBox = new CheckBox(context);
                    TextView textView = new TextView(context);
                    long b = listProduct.stream().filter(o -> isValueAtribute(o.getProduct_attributes(), attItem.getName(), attValue.getValue())).count();
                    checkBox.setText(attValue.getValue());
                    textView.setText("(" + count + ")");
                    attValues.add(new GroupElementNested(checkBox, textView));
                    count = 0;
                }
            }
            mList.add(new OptionFilterDataModel(attValues, attItem.getName()));
        }
        adapter = new OptionsFilterItemAdapter(mList, SaveFilterOption.getSaveCheck());
        adapter.setOnCheckedCheckBoxFilter(this);
        recyclerView.setAdapter(adapter);

    }

    Type productAtributelistType = new TypeToken<ArrayList<ProductAtribute>>() {
    }.getType();
    List<ProductAtribute> productAtributeList;

    private boolean isValueAtribute(String jsonAtribute, String atributeName, String attributeValue) {

Log.d("isValueAtribute()1 !!",String.valueOf(count));
        if (jsonAtribute != null && !jsonAtribute.isEmpty()) {
            productAtributeList = new Gson().fromJson(jsonAtribute, productAtributelistType);

            for (ProductAtribute item : productAtributeList) {
                if (Objects.equals(item.getKey(), atributeName) && Objects.equals(item.getValue(), attributeValue)) {
                    Log.d(item.getKey(),item.getValue());
                    count++;
                    Log.d("isValueAtribute()2 !!",String.valueOf(count));
                }


            }
            return true;
        } else {
            return false;
        }

    }


    public static <T extends Comparable<T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }


    private void searchDiapazone(String from, String to) {
        if (!from.isEmpty() && !to.isEmpty()) {
            BigDecimal a = new BigDecimal(from);
            BigDecimal b = new BigDecimal(to);
            compliteFilter();
            list = CategoryFilterFragment.productAdapter.getListProduct().stream().filter(o -> isBetween(o.getPrice(), a, b)).collect(Collectors.toList());
            if (CategoryFilterFragment.productAdapter.getListProduct().size() != list.size()) {
                CategoryFilterFragment.productAdapter.getListProduct().clear();
                CategoryFilterFragment.productAdapter.getListProduct().addAll(list);
                CategoryFilterFragment.productAdapter.notifyDataSetChanged();
            }
        }
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

    public void compliteFilter() {

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
        }

        CategoryFilterFragment.productAdapter.setListProduct(listProduct);

        if (!SaveFilterOption.getSaveCheck().isEmpty()) {
            CategoryFilterFragment.productAdapter.setMapItemSearch(SaveFilterOption.getSaveCheck());
            CategoryFilterFragment.productAdapter.getFilter().filter("");


        } else {
            CategoryFilterFragment.productAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (SaveFilterOption.getSaveCheck().isEmpty() && list == null)
            compliteFilter();

    }

    @Override
    public void onCheckedCheckBoxFilter(CheckBox checkBox, boolean b, TextView tvCountElement) {

        String namecheckBox = checkBox.getText().toString();
        Log.d("onCheckedCheckBoxFilter()!!",namecheckBox);
        if (b) {
            SaveFilterOption.getSaveCheck().put(namecheckBox, true);
        } else {
            SaveFilterOption.getSaveCheck().remove(namecheckBox);
        }
        CategoryFilterFragment.productAdapter.setMapItemSearch(SaveFilterOption.getSaveCheck());
        CategoryFilterFragment.productAdapter.getFilter().filter("");


    }
}
