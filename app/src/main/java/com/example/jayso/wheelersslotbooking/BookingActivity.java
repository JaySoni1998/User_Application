package com.example.jayso.wheelersslotbooking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText VehicleNo, VehicleCompany, VehicleModel;
    private Spinner vehicle_type;
    private RadioGroup rgOwnerType;

    public String v_no = "";
    public String v_company = "";
    public String v_model = "";
    public String v_type = "";
    public String owner_type = "";

    String[] vh_type = {"2 Wheelers", "3 Wheelers", "4 Wheelers"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        hideSoftKeyboard();

        VehicleNo = findViewById(R.id.et_vehicle_no);
        VehicleCompany = findViewById(R.id.et_vehicle_company);
        VehicleModel = findViewById(R.id.et_vehicle_model);
        vehicle_type = findViewById(R.id.v_type_spinner);
        rgOwnerType = findViewById(R.id.rgOwnerType);

        final Spinner v_type = findViewById(R.id.v_type_spinner);
        v_type.setOnItemSelectedListener(this);
        ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vh_type);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        v_type.setAdapter(a);
        rgOwnerType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                owner_type = rb.getText().toString();
//                Toast.makeText(BookingActivity.this, owner_type, Toast.LENGTH_SHORT).show();
            }
        });
        Button sub = findViewById(R.id.submit_booking);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v_no = VehicleNo.getText().toString();
                v_company = VehicleCompany.getText().toString();
                v_model = VehicleModel.getText().toString();




                if (v_no.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Vehicle_Booking_Page), "Please Insert Vehicle Number", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (v_company.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Vehicle_Booking_Page), "Please Insert Vehicle Company", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (v_model.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Vehicle_Booking_Page), "Please Insert Vehicle Model", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (owner_type.length() <= 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Vehicle_Booking_Page), "Please Select Owner Type", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else {

                    UserVehicledetails();
                }
            }
        });

        Button btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //
        final EditText vehicleNo = findViewById(R.id.et_vehicle_no);
        vehicleNo.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prevL = vehicleNo.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 2 || length == 5 || length == 8)) {
                    editable.append("-");
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void UserVehicledetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.Vehicle_details,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("Response", response);
                            Toast.makeText(BookingActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                             startActivity(new Intent(BookingActivity.this, Booking2Activity.class));


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error", e.getMessage());
                            Toast.makeText(BookingActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("V_No", v_no);
                params.put("V_Company", v_company);
                params.put("V_Model",v_model);
                params.put("V_Type", v_type);
                params.put("Owner_Type",owner_type);

                Log.d("V_No",v_no);
                Log.d("V_Company",v_company);
                Log.d("V_Model",v_model);
                Log.d("V_Type",v_type);
                Log.d("Owner_Type",owner_type);


                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
}
