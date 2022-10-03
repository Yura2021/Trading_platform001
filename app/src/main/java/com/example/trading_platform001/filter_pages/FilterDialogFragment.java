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
import com.example.trading_platform001.filter_pages.models.GroupElementNested;
import com.example.trading_platform001.filter_pages.models.OptionFilterDataModel;
import com.example.trading_platform001.filter_pages.models.SaveFilterOption;
import com.example.trading_platform001.interfaces.NestedOnCheckedCheckBoxFilter;
import com.example.trading_platform001.models.LocalProducts;
import com.example.trading_platform001.models.LocalShops;
import com.example.trading_platform001.models.ProductEntity;
import com.example.trading_platform001.models.ShopEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class FilterDialogFragment extends DialogFragment implements NestedOnCheckedCheckBoxFilter {
    View view;
    @BindView(R.id.eTseekBar1)
    EditText editText1;
    @BindView(R.id.seekBar1)
    SeekBar seekBar1;
    @BindView(R.id.eTseekBar2)
    EditText editText2;
    @BindView(R.id.seekBar2)
    SeekBar seekBar2;

    @BindView(R.id.btnClose)
    Button btnClose;
    @BindView(R.id.main_recyclervie)
    RecyclerView recyclerView;
    List<OptionFilterDataModel> mList;
    OptionsFilterItemAdapter adapter;
    Typeface typeface = Typeface.create("open_sans_bold", Typeface.BOLD);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_dialog_filter, container, false);

        ButterKnife.bind(this, view);

        btnClose.setOnClickListener(v -> dismiss());
        showSeekBar();
        addItemOption();
        return view;
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

    private void showSeekBar() {

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editText1.setText(String.valueOf(progress));
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

                editText2.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void sortProduct(int id) {
        switch (id) {
            case R.id.rbCheapToExpensive:
                //Toast.makeText(view.getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                //LocalProducts.getProducts().sort(Comparator.comparing(ProductEntity::getPrice));
                CategoryFilterFragment.productAdapter.getListProduct().sort(Comparator.comparing(ProductEntity::getPrice));
                updateAdapter();
                break;
            case R.id.rbExpensiveToCheap:
                //LocalProducts.getProducts().sort(((o1, o2) -> o2.getPrice().compareTo(o1.getPrice())));
                CategoryFilterFragment.productAdapter.getListProduct().sort(((o1, o2) -> o2.getPrice().compareTo(o1.getPrice())));
                updateAdapter();
                //Toast.makeText(view.getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbSortNewProduct:
                //LocalProducts.getProducts().sort(Comparator.comparing(ProductEntity::getCreated_at));
                CategoryFilterFragment.productAdapter.getListProduct().sort(Comparator.comparing(ProductEntity::getCreated_at));
                updateAdapter();
                //Toast.makeText(view.getContext(), rb.getText(), Toast.LENGTH_SHORT).show();

                break;
            case R.id.rbSortRating:
                //LocalProducts.getProducts().sort(Comparator.comparing(ProductEntity::getRating));
                CategoryFilterFragment.productAdapter.getListProduct().sort(Comparator.comparing(ProductEntity::getRating));
                updateAdapter();
                //Toast.makeText(view.getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    void updateAdapter() {
        CategoryFilterFragment.productAdapter.getListProduct();
        CategoryFilterFragment.productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedCheckBoxFilter(CheckBox checkBox, boolean b, TextView tvCountElement) {
        //  Log.d("Tagggss", " " + b + " Text " + checkBox.getText().toString()+"  count: "+tvCountElement.getText().toString());
        CategoryFilterFragment.productAdapter.getListProduct().sort(Comparator.comparing(ProductEntity::getPrice));
        updateAdapter();
        String namecheckBox = checkBox.getText().toString();
        if (b) {
            //saveCheck.put(new GroupElementNested(checkBox,tvCountElement), b);
            SaveFilterOption.getSaveCheck().put(namecheckBox, true);

            long id = LocalShops.getShops().stream().filter(o -> o.getName() == namecheckBox).collect(Collectors.toList()).get(0).getId();
            // CategoryFilterFragment.productAdapter.setListProduct(CategoryFilterFragment.productAdapter.getListProduct().stream().filter(o -> o.getShop_id() == id).collect(Collectors.toList()));
        } else {
            SaveFilterOption.getSaveCheck().remove(namecheckBox);

            updateAdapter();
        }
        Log.d("Tagggss", " " + b + " Text " + checkBox.getText().toString() + "  count: " + SaveFilterOption.getSaveCheck().size());


    }
}
