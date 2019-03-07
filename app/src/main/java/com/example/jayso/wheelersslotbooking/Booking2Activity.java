package com.example.jayso.wheelersslotbooking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.jayso.wheelersslotbooking.Constants.KEY_Vehicle_No;

public class Booking2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Dialog myDialog;
    private Button btn_reg_vehicle, btn_other_vehicle, btn_popup_close;
    private PopupWindow popupWindow;
    private LinearLayout linearLayoutBooking;
    private TextView start_time_picker, end_time_picker;

    public static final String KEY_USER_ID = "U_ID";
//    public static final String KEY_USER_ = "U_ID";
    int user_id = 0;
    String v_no = null;

    public String startingTime = "";
    public String endTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        hideSoftKeyboard();

        try {
            SharedPreferences userLogin = getApplicationContext().getSharedPreferences("mysharedpref12", getApplicationContext().MODE_PRIVATE);
            user_id = userLogin.getInt(KEY_USER_ID, 0);
            v_no = userLogin.getString(KEY_Vehicle_No, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        start_time_picker = findViewById(R.id.start_time_picker);
        end_time_picker = findViewById(R.id.end_time_picker);

        final TextView starttime = findViewById(R.id.start_time_picker);

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calTime = Calendar.getInstance();
                int hour = calTime.get(Calendar.HOUR_OF_DAY);
                int minute = calTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Booking2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMiniute) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MINUTE, selectedMiniute);
                        calendar.set(Calendar.HOUR, selectedHour);
                        String hh = "" + calendar.get(Calendar.HOUR);
                        if (hh.equals("0") || hh.equals("00")) {
                            hh = "12";
                        }
                        String time = setZero(hh) + ":" + setZero("" + calendar.get(Calendar.MINUTE)) + " "
                                + ((calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM");
                        starttime.setText(time);
//                        starttime.setText(selectedHour + ":" + selectedMiniute+timePicker);
                        startingTime = starttime.getText().toString();
//                        Toast.makeText(getApplicationContext(), selectedHour + "  " + selectedMiniute, Toast.LENGTH_SHORT).show();
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        final TextView endtime = findViewById(R.id.end_time_picker);

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calTime = Calendar.getInstance();
                int hour = calTime.get(Calendar.HOUR_OF_DAY);
                int minute = calTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Booking2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMiniute) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MINUTE, selectedMiniute);
                        calendar.set(Calendar.HOUR, selectedHour);
                        String hh = "" + calendar.get(Calendar.HOUR);
                        if (hh.equals("0") || hh.equals("00")) {
                            hh = "12";
                        }
                        String time = setZero(hh) + ":" + setZero("" + calendar.get(Calendar.MINUTE)) + " "
                                + ((calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM");

                        endtime.setText(time);
//                        endtime.setText(selectedHour + ":" + selectedMiniute);
                        endTime = endtime.getText().toString();
//                        Toast.makeText(getApplicationContext(), selectedHour + "  " + selectedMiniute, Toast.LENGTH_SHORT).show();
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        //dialog_popup
        myDialog = new Dialog(this);

        Button btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button btn_cancle = findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(Booking2Activity.this);
                myAlertDialog.setTitle("Are you sure?");
                myAlertDialog.setMessage("Would you like to cancel the booking?");
                myAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent it = new Intent(Booking2Activity.this, MapActivity.class);
                        startActivity(it);
                        finish();
                    }
                });

                myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

                myAlertDialog.show();
            }
        });

        Button btn_done = findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ParkingTime();


            }
        });

    }

    public String setZero(String val) {

        if (val.length() == 1)
            return "0" + val;

        return val;
    }

    public void ShowPopupRegi(View v) {
        TextView popup_close;
        myDialog.setContentView(R.layout.activity_regi_vehicle_popup);
        popup_close = myDialog.findViewById(R.id.btn_popup_close);
        popup_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        popup_close = myDialog.findViewById(R.id.btn_done);
        popup_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void ShowPopupOther(View v) {
        TextView popup_close;
        myDialog.setContentView(R.layout.activity_other_vehicle_popup);
        popup_close = myDialog.findViewById(R.id.btn_popup_close);
        popup_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        popup_close = myDialog.findViewById(R.id.btn_done);
        popup_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
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

    private void ParkingTime() {
//        Log.d("In_Time",startingTime);
//        Log.d("Out_Time",endTime);
        Toast.makeText(this, startingTime, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.parking_charges,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("Response", response);
                            addNotification();
                            Toast.makeText(Booking2Activity.this, "Success", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(Booking2Activity.this, Booking2Activity.class));
                            AlertDialog alertDialog = new AlertDialog.Builder(Booking2Activity.this).create();
                            alertDialog.setTitle("Infomation");
                            alertDialog.setMessage("Booking Successfully!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent it = new Intent(Booking2Activity.this, MapActivity.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    });
                            alertDialog.show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error", e.getMessage());
                            Toast.makeText(Booking2Activity.this, "Error", Toast.LENGTH_SHORT).show();
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
                params.put("In_Time", startingTime);
                params.put("Out_Time", endTime);
                params.put("U_ID", ""+user_id);
                params.put("V_No", v_no);

                Log.d("In_Time", startingTime);
                Log.d("Out_Time", endTime);


                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    //notification
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Wheeler Slot Booking")
                        .setContentText("Your Booking is Successfully Done");

    }
}

