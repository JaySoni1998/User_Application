package com.example.jayso.wheelersslotbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText emailEditText,phoneNo;
    private Spinner countryCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailEditText = findViewById(R.id.et_forgot_ID_No);


        findViewById(R.id.btn_forgotpass).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                final String email = emailEditText.getText().toString();
                if (email.length() == 0) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please insert Email ID", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
//                    emailEditText.setError("Invalid Email, Please insert '@' and '.' ");
                    Toast.makeText(ForgetPasswordActivity.this, "Please insert ' @ ' and ' . '", Toast.LENGTH_SHORT).show();
                } else {
                    Intent it = new Intent(ForgetPasswordActivity.this, ForgotPasswordOTPActivity.class);
                    startActivity(it);
                    finish();
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
}
