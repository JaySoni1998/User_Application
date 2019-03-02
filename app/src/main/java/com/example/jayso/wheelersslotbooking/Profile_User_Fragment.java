package com.example.jayso.wheelersslotbooking;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Profile_User_Fragment extends Fragment{
    private static final String KEY_USER_ID = "U_ID";
    private static final String KEY_USER_EMAIL = "Email_ID";
    private static final String KEY_USER_F_NAME = "U_FirstName";
    private static final String KEY_GENDER = "U_Gender";
    private static final String KEY_CONTACT = "Cont_No";
    private TextView u_id, u_name;
    private TextView gender, email, contact;
    Dialog myDialog;

    public Profile_User_Fragment() {
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
        return inflater.inflate(R.layout.fragment_profile_user, container, false);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        u_id = view.findViewById(R.id.tv_u_id);
        SharedPreferences userLogin = getActivity().getApplicationContext().getSharedPreferences("mysharedpref12",getActivity().getApplicationContext().MODE_PRIVATE);
        int id = (int) userLogin.getInt(KEY_USER_ID,0);
        u_id.setId(id);
        u_id.setText(""+id);

        u_name = view.findViewById(R.id.tv_u_name);
        String fname = userLogin.getString(KEY_USER_F_NAME,null);
        u_name.setText(fname);

        gender = view.findViewById(R.id.tv_u_gender);
        String Gender = userLogin.getString(KEY_GENDER,null);
        gender.setText(Gender);

        email = view.findViewById(R.id.tv_u_email);
        String Email = userLogin.getString(KEY_USER_EMAIL,null);
        email.setText(Email);

        contact = view.findViewById(R.id.tv_u_contact);
        String Contact = userLogin.getString(KEY_CONTACT,null);
        contact.setText(Contact);
    }

}
