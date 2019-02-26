package com.example.jayso.wheelersslotbooking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user_fn, user_ln, contact_no, emailEditText;
    private EditText passEditText, confirmPassEditText;
    private Button btnRegi;
    private RadioGroup rgGender;


    public String fn = "";
    public String ln = "";
    public String gender = "";
    public String contact = "";
    public String email = "";
    public String pass = "";
    public String cpass = "";

    ProgressDialog progress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        hideSoftKeyboard();

        user_fn = findViewById(R.id.et_user_fn);
        user_ln = findViewById(R.id.et_user_ln);
        rgGender = findViewById(R.id.rg_gender);
        contact_no = findViewById(R.id.et_reg_contact);
        emailEditText = findViewById(R.id.et_reg_email);
        passEditText = findViewById(R.id.et_reg_pass);
        confirmPassEditText = findViewById(R.id.et_reg_con_pass);
        btnRegi = findViewById(R.id.btn_reg_submit);
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                gender = rb.getText().toString();
            }
        });




        btnRegi.setOnClickListener(this);

        findViewById(R.id.btn_reg_submit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                fn = user_fn.getText().toString();
                ln = user_ln.getText().toString();
                contact = contact_no.getText().toString();
                email = emailEditText.getText().toString();
                pass = passEditText.getText().toString();
                cpass = confirmPassEditText.getText().toString();


                if (fn.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Insert First Name", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (ln.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Insert Last Name", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (gender.length() <= 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Select Gender", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (contact_no.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Insert Contact No", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (contact_no.length() < 10) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Insert 10 Digit in Contact No", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (email.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Insert Email ID", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (!isValidEmail(email)) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Insert valid Email ID ' @ ' and ' . ' ", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (pass.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Insert Password", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (!isValidPassword(pass)) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Insert Minimum 6 Characters", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (cpass.length() == 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Please Insert confirm Password", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else if (!pass.equals(cpass)) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.Registration_Layout), "Not Match Password", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                    snackbar.show();
                } else {

                    RegisterUser();
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
        Intent it = new Intent(RegistrationActivity.this, LoginSignupActivity.class);
        startActivity(it);
    }

    public void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onClick(View v) {

        if (v == btnRegi) {

            RegisterUser();

        }

    }

    private void RegisterUser() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.UserRegi,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("Response", response);

                            //Toast.makeText(RegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                            alertDialog.setTitle("Information");
                            alertDialog.setMessage("Registration Successfully!!!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                            finish();

                                        }
                                    });
                            alertDialog.show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error", e.getMessage());


                            Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                params.put("U_FirstName", fn);
                params.put("U_LastName", ln);
                params.put("U_Gender",gender);
                params.put("Email_ID", email);
                params.put("Password", pass);
                params.put("Cont_No", contact);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}

