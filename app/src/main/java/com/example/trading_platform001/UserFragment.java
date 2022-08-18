package com.example.trading_platform001;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.trading_platform001.models.Http;
import com.example.trading_platform001.models.MenuUserListAdapter;

import org.json.JSONException;
import org.json.JSONObject;


public class UserFragment extends Fragment {
    Button btnAuth,btnLogout;
    ListView listMenu;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        View v = inflater.inflate(R.layout.fragment_user,
                null);

        btnAuth =  v.findViewById(R.id.btnAuth);
        btnLogout =  v.findViewById(R.id.btnLogout);
        getUser();
        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AuthorizationMenuActivity.class));
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        listMenu = (ListView) v.findViewById(R.id.UserMenu);

        final String[] MenuItem = new String[] {
                getResources().getString(R.string.signin),
                getResources().getString(R.string.Account),
                getResources().getString(R.string.My_orders),
                getResources().getString(R.string.My_mesegges),
                getResources().getString(R.string.My_correspondence),
                getResources().getString(R.string.My_purse),
                getResources().getString(R.string.comparison),
                getResources().getString(R.string.Sales),
                getResources().getString(R.string.action),
                getResources().getString(R.string.support),
                getResources().getString(R.string.Work_time),
                getResources().getString(R.string.Information),
        };

        MenuUserListAdapter adapter = new MenuUserListAdapter(getContext(), R.layout.listmenu, MenuItem);
       listMenu.setAdapter(adapter);

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {

                String selectedItem = MenuItem[position];
                switch (selectedItem)
                {
                    case "Вхід": {
                        startActivity(new Intent(getContext(), AuthorizationMenuActivity.class));
                        break;
                    }
                    case "Обліковий запис": {
                        startActivity(new
                                Intent(getContext(),UserInformation.class));
                        break;
                    }
                }
            }
        });

        return v;
    }

    private void getUser() {
        String url = getString(R.string.api_server)+"/user";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(requireActivity(),url);
                http.setToken(true);
                http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String name = response.getString("name");
                                btnAuth.setText(name);
                                Log.d("Token getUser ",http.getStringToken());
                                btnLogout.setEnabled(true);
                                btnAuth.setEnabled(false);

                            }catch (JSONException ex){
                                ex.printStackTrace();
                            }
                        }
                        //else{Toast.makeText(requireActivity(),"Error "+code,Toast.LENGTH_SHORT).show();}
                    }
                });
            }
        }).start();
    }

    private void logout() {
        String url = getString(R.string.api_server)+"/logout";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(requireActivity(),url);
                http.setMethod("POST");
                http.setToken(true);
                http.send();

               requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            Intent intent = new Intent(requireActivity(),MainActivity.class);
                            startActivity(intent);
                            Log.d("Token logout ",http.getStringToken());
                            String strName = getResources().getResourceName(R.string.authorization_button_name_authorization).toString();
                            btnAuth.setText(strName);
                            btnAuth.setEnabled(true);
                            btnLogout.setEnabled(false);
                            requireActivity().finish();
                        }
                        else{
                            Toast.makeText(requireActivity(),"Error "+code,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

}