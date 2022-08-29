package com.example.trading_platform001;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.trading_platform001.models.MenuUserListAdapter;
import com.example.trading_platform001.models.OrderUserList;

import java.sql.Time;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    ListView listView;
    ImageView imageView;
    ImageView SearchButton;
    EditText Searctext;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);

        imageView = v.findViewById(R.id.imageView3);
        listView= v.findViewById(R.id.list_orders);
        SearchButton = v.findViewById(R.id.SearchOrders);
        Searctext = v.findViewById(R.id.editTextTextOrders);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new UserFragment());
            }
        });

        final String [] ordersItemName = new String[]{
                "№123 123 123",
                "№123 123 124",
                "№123 123 125",
                "№123 123 126",
                "№123 123 127"
        };
        final Time[] orderTime=new Time[]{
                new Time(10),
                new Time(11),
                new Time(12),
                new Time(13),
                new Time(14)
        };
        final String[] orderStatus= new String[]{
                "Виконано",
                "Скасовано користувачем",
                "Виконано",
                "Виконано",
                "Виконано"
        };
        final int[]orderPrice = new int[]{
                1,
                20,
                312,
                446,
                547
        };

        OrderUserList adapter = new OrderUserList(getContext(),R.layout.list_order_item_template,ordersItemName,orderTime,orderStatus,orderPrice);
       listView.setAdapter(adapter);



        /*Searctext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0 ; i<listView.getCount();i++)
                {
                    if(Searctext.getText().equals("№"+listView.findViewById(i).findViewById(R.id.id_orders).toString()))
                    {
                        final String [] ordersItemName = new String[]{
                                "№123 123 123",
                        };
                        final Time[] orderTime=new Time[]{
                                new Time(10),
                        };
                        final String[] orderStatus= new String[]{
                                "Виконано",
                        };
                        final int[]orderPrice = new int[]{
                                1,
                        };
                        OrderUserList adapter = new OrderUserList(getContext(),R.layout.list_order_item_template,ordersItemName,orderTime,orderStatus,orderPrice);
                        listView.setAdapter(adapter);
                    }
                }

            }
        });*/


        return v;
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_view_fragment, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}