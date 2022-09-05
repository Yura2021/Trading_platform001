package com.example.trading_platform001;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.JsonParser;
import com.example.trading_platform001.models.Order;
import com.example.trading_platform001.models.OrderUserListAdapter;
import com.example.trading_platform001.models.StorageInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    ListView listView;
    ImageView imageView;
    SearchView SearchOrders;
    List<Order>  orderList = new ArrayList<>();
    OrderUserListAdapter adapter;
    View v;
    StorageInformation storageInformation;
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
        v = inflater.inflate(R.layout.fragment_orders, container, false);
        storageInformation = new StorageInformation(getContext());
        imageView = v.findViewById(R.id.imageView3);
        listView= v.findViewById(R.id.list_orders);
        SearchOrders = v.findViewById(R.id.SearchOrders);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new UserFragment());
            }
        });

        GetOrderUser();

        SearchOrders.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              //  adapter.SearchItem(newText);
                return true;
            }
        });


        adapter= new OrderUserListAdapter(getActivity(),R.layout.list_order_item_template,orderList);
        listView.setAdapter(adapter);
        return v;
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_view_fragment, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
    private void GetOrderUser() {

        JSONObject params = new JSONObject();
        try {
            params.put("email", storageInformation.GetStorage("Email").toString());
            params.put("password", storageInformation.GetStorage("Password").toString());

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        String data = params.toString();
        String url = getString(R.string.api_server) + "/orders";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(requireActivity(), url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                orderList = JsonParser.getClassOrder(response);
                                adapter.updateReceiptsList(orderList);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }

                        } else if (code == 422) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        } else if (code == 401) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            Toast.makeText(requireActivity(), "Error " + code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}