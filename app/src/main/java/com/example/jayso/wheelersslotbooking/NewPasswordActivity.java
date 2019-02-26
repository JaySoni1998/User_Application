package com.example.jayso.wheelersslotbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class NewPasswordActivity extends AppCompatActivity {

    private EditText passEditText, confirmPassEditText;
    private CheckBox passShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        passEditText = findViewById(R.id.et_new_pass);
        confirmPassEditText = findViewById(R.id.et_new_con_pass);

       /* passShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean value) {
                if(value){
                    passEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPassEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPassEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });*/


        findViewById(R.id.btn_change_pass_submit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                final String pass = passEditText.getText().toString();
                final String cpass = confirmPassEditText.getText().toString();

                if (!isValidPassword(pass)) {
                    Toast.makeText(NewPasswordActivity.this, "Insert valid password", Toast.LENGTH_SHORT).show();
                    //passEditText.setError("Invalid Password, Please Insert Minimum 6 Characters");
                } else if (!pass.equals(cpass)) {
                    Toast.makeText(NewPasswordActivity.this, "Not Match Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent it = new Intent(NewPasswordActivity.this, LoginActivity.class);
                    startActivity(it);
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

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

}
