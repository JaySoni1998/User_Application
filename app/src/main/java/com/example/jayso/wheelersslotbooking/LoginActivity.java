package com.example.jayso.wheelersslotbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.jayso.wheelersslotbooking.Constants.KEY_Owner_Type;
import static com.example.jayso.wheelersslotbooking.Constants.KEY_V_Company;
import static com.example.jayso.wheelersslotbooking.Constants.KEY_V_Model;
import static com.example.jayso.wheelersslotbooking.Constants.KEY_V_Type;
import static com.example.jayso.wheelersslotbooking.Constants.KEY_Vehicle_No;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private EditText passEditText;
    private Button btnLogin;
    private static final String KEY_USER_ID = "U_ID";
    private static final String KEY_USER_EMAIL = "Email_ID";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_USER_F_NAME = "U_FirstName";
    private static final String KEY_USER_L_NAME = "U_LastName";
    private static final String KEY_GENDER = "U_Gender";
    private static final String KEY_CONTACT = "Cont_No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hideSoftKeyboard();

        emailEditText = findViewById(R.id.et_login_email);
        passEditText = findViewById(R.id.et_login_pass);
        btnLogin = findViewById(R.id.btn_login_Page);


        findViewById(R.id.btn_login_Page).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final String email = emailEditText.getText().toString();
                final String pass = passEditText.getText().toString();


                if (email.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Login_Page), "Please Insert Email ID", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (!isValidEmail(email)) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Login_Page), "Please Insert ' @ ' and ' . '", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (pass.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Login_Page), "Please Insert Password", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (!isValidPassword(pass)) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Login_Page), "Please Insert Minimum 6 Characters", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (SharedPrefManager.getInstance(LoginActivity.this).isLoggedIn()) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MapActivity.class));
                    return;
                } else {
                    userLogin();

                }
            }

        });


        TextView btnpass = findViewById(R.id.btn_forgot_pass);
        btnpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(it);
            }
        });

        Button btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void userLogin() {

        final String email = emailEditText.getText().toString();
        final String pass = passEditText.getText().toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                JSONObject userData = obj.getJSONObject("user");
                                Toast.makeText(LoginActivity.this, "User Login Success", Toast.LENGTH_SHORT).show();
                                AppPref.setValue("IS_LOGIN", "true",getApplicationContext());

                                SharedPreferences userLogin = getApplicationContext().getSharedPreferences("mysharedpref12", getApplicationContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = userLogin.edit();

                                editor.putInt(KEY_USER_ID, userData.getInt(("U_ID")));
                                editor.putString(KEY_USER_EMAIL, userData.getString("Email_ID"));
                                editor.putString(KEY_USER_F_NAME, userData.getString("U_FirstName"));
                                editor.putString(KEY_USER_L_NAME, userData.getString("U_LastName"));
                                editor.putString(KEY_PASSWORD, userData.getString("Password"));
                                editor.putString(KEY_GENDER, userData.getString("U_Gender"));
                                editor.putString(KEY_CONTACT, userData.getString("Cont_No"));

                                try{
                                JSONObject vehicleDetails = obj.getJSONObject("VehicleDetails");
                                editor.putString(KEY_Vehicle_No, vehicleDetails.getString("V_No"));
                                editor.putString(KEY_V_Company, vehicleDetails.getString("V_Company"));
                                editor.putString(KEY_V_Model, vehicleDetails.getString("V_Model"));
                                editor.putString(KEY_V_Type, vehicleDetails.getString("V_Type"));
                                editor.putString(KEY_Owner_Type, vehicleDetails.getString("Owner_Type"));

                                editor.apply();
                                editor.commit();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                                finish();

                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error", response);
                            Toast.makeText(LoginActivity.this, "json error", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email_ID", email);
                params.put("Password", pass);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            userLogin();
        }
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent it = new Intent(LoginActivity.this, LoginSignupActivity.class);
        startActivity(it);
    }

    public void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}

