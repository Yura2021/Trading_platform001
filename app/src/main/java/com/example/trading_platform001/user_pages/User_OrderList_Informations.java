package com.example.trading_platform001.user_pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trading_platform001.R;
import com.example.trading_platform001.adapters.OrderUserListAdapter;
import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.StorageInformation;
import com.example.trading_platform001.user_pages.models.OrderList;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class User_OrderList_Informations extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_orders)
    ListView listView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.imageBack)
    ImageView imageView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.SearchOrders)
    SearchView SearchOrders;
    OrderList orderList ;
    OrderUserListAdapter adapter;
    View view;
    Http http;
    StorageInformation storageInformation;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public User_OrderList_Informations() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_user__order_list__informations, container, false);
        ButterKnife.bind(this, view);
        storageInformation = new StorageInformation(getContext());
        http = new Http(getContext());
        imageView.setOnClickListener(v->onClick());
        orderList = new OrderList();
        adapter= new OrderUserListAdapter(getActivity(),R.layout.list_order_item_template, orderList.getOrders());
        http.GetOrdersUser(adapter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(( parent, view, position, id)-> {
                String item = orderList.getOrders().get(position).getOrder().getOrder_number();
                for (int i = 0 ; i<orderList.getOrders().size();i++)
                {
                    if(Objects.equals(item, orderList.getOrders().get(position).getOrder().getOrder_number()))
                    {
                        Intent intent = new Intent(getContext(),UserOrderItemActivity.class);
                        intent.putExtra("orderitem",orderList.GetAllItemOrder().get(position));
                        intent.putExtra("order",orderList.getOrders().get(position).getOrder());
                        startActivity(intent);
                        break;
                    }
                }
        });

        SearchOrders.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              adapter.getFilter().filter(newText);
              return true;
            }
        });

       adapter.getFilter().filter("-");
        return view;
    }
    public void onClick() {
        replaceFragment(new UserFragment());
    }
    public void replaceFragment(Fragment fragment) {
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_view_fragment, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }


}