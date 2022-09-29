package com.example.trading_platform001.filter_pages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.OptionsFilterItemAdapter;
import com.example.trading_platform001.filter_pages.models.OptionFilterDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class FilterDialogFragment extends DialogFragment {
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

    private void addItemOption() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(FilterDialogFragment.this.getContext()));

        mList = new ArrayList<>();

        //list1
        List<String> nestedList1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nestedList1.add("Test " + i + 1);
        }

        List<String> nestedList2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nestedList2.add("Test " + i + 1);
        }

        List<String> nestedList3 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nestedList3.add("Test " + i + 1);
        }

        List<String> nestedList4 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nestedList4.add("Test " + i + 1);
        }

        List<String> nestedList5 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nestedList5.add("Test " + i + 1);
        }

        List<String> nestedList6 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nestedList6.add("Test " + i + 1);
        }


        List<String> nestedList7 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nestedList7.add("Test " + i + 1);
        }

        mList.add(new OptionFilterDataModel(nestedList1, "Option atribute 1"));
        mList.add(new OptionFilterDataModel(nestedList2, "Option atribute 2"));
        mList.add(new OptionFilterDataModel(nestedList3, "Option atribute 3"));
        mList.add(new OptionFilterDataModel(nestedList4, "Option atribute 4"));
        mList.add(new OptionFilterDataModel(nestedList5, "Option atribute 5"));
        mList.add(new OptionFilterDataModel(nestedList6, "Option atribute 6"));
        mList.add(new OptionFilterDataModel(nestedList7, "Option atribute 7"));

        adapter = new OptionsFilterItemAdapter(mList);
        recyclerView.setAdapter(adapter);
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


}
