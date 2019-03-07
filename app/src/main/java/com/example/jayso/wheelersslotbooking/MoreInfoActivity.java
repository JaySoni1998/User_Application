package com.example.jayso.wheelersslotbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

public class MoreInfoActivity extends AppCompatActivity {

    TextView txtPlaceName;
    TextView txtPlaceAddress;
    TextView txtAreacode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);


        txtPlaceName = (TextView) findViewById(R.id.txt_place_name);
        txtPlaceAddress = (TextView) findViewById(R.id.txt_place_address);
        txtAreacode = (TextView) findViewById(R.id.txt_areacode);


        txtPlaceName.setText(getIntent().getExtras().getString("NAME",""));
        txtPlaceAddress.setText(getIntent().getExtras().getString("ADDRESS",""));
        txtAreacode.setText(getIntent().getExtras().getString("PIN",""));



    }
}
