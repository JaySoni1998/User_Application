package com.example.jayso.wheelersslotbooking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.jayso.wheelersslotbooking.Constants.KEY_Owner_Type;
import static com.example.jayso.wheelersslotbooking.Constants.KEY_V_Company;
import static com.example.jayso.wheelersslotbooking.Constants.KEY_V_Model;
import static com.example.jayso.wheelersslotbooking.Constants.KEY_V_Type;
import static com.example.jayso.wheelersslotbooking.Constants.KEY_Vehicle_No;

public class Profile_Vehicle_Fragment extends Fragment{


    private TextView v_no, v_company,v_model, v_type, v_owner_type;

    public Profile_Vehicle_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_vehicle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v_no = view.findViewById(R.id.tv_v_no);
        SharedPreferences userLogin = getActivity().getApplicationContext().getSharedPreferences("mysharedpref12",getActivity().getApplicationContext().MODE_PRIVATE);
        //int no = (int) userLogin.getInt(KEY_Vehicle_No,0);
        String no = userLogin.getString(KEY_Vehicle_No,null);
        v_no.setText(no);

        v_company = view.findViewById(R.id.tv_v_company);
        String comp = userLogin.getString(KEY_V_Company,null);
        v_company.setText(comp);

        v_model = view.findViewById(R.id.tv_v_model);
        String model = userLogin.getString(KEY_V_Model,null);
        v_model.setText(model);

        v_type = view.findViewById(R.id.tv_v_type);
        String type = userLogin.getString(KEY_V_Type,null);
        v_type.setText(type);

        v_owner_type = view.findViewById(R.id.tv_v_owner_type);
        String oType = userLogin.getString(KEY_Owner_Type,null);
        v_owner_type.setText(oType);
    }

}
