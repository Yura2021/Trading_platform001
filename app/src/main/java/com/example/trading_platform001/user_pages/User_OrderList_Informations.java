package com.example.trading_platform001.user_pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.OrderUserListAdapter;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.StorageInformation;
import com.example.trading_platform001.user_pages.models.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_OrderList_Informations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_OrderList_Informations extends Fragment {

    @BindView(R.id.list_orders)
    ListView listView;
    @BindView(R.id.imageBack)
    ImageView imageView;
    @BindView(R.id.SearchOrders)
    SearchView SearchOrders;
    List<Order> orderList = new ArrayList<>();
    OrderUserListAdapter adapter;
    View view;
    Http http;
    Intent intent;
    StorageInformation storageInformation;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_OrderList_Informations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_OrderList_Informations.
     */
    // TODO: Rename and change types and number of parameters
    public static User_OrderList_Informations newInstance(String param1, String param2) {
        User_OrderList_Informations fragment = new User_OrderList_Informations();
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
        if (view == null)
            view = inflater.inflate(R.layout.fragment_user__order_list__informations, container, false);
        ButterKnife.bind(this, view);
        storageInformation = new StorageInformation(getContext());
        http = new Http(getContext());
        imageView.setOnClickListener(v->onClick(view));

        adapter= new OrderUserListAdapter(getActivity(),R.layout.list_order_item_template,  orderList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = orderList.get(position).getOrder_number();
                for (int i = 0 ; i<orderList.size();i++)
                {
                    if(item==orderList.get(position).getOrder_number())
                    {
                        Intent intent = new Intent(getContext(),UserOrderItemActivity.class);
                        intent.putExtra("order",orderList.get(position));
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
        http.GetOrdersUser(adapter);
        return view;
    }
    public void onClick(View v) {
        replaceFragment(new UserFragment());
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_view_fragment, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }


}