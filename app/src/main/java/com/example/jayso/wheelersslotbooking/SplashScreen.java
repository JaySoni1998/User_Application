package com.example.jayso.wheelersslotbooking;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                        .getBoolean("isFirstRun", true);

                if (isFirstRun) {
                    startActivity(new Intent(SplashScreen.this, SliderActivity.class));
                    finish();
                }else {
                    Intent i =new Intent(SplashScreen.this,LoginSignupActivity.class);
                    String val = "false";
                    try {
                        val =  AppPref.getValue("IS_LOGIN", "false", SplashScreen.this);
                        if (val.equalsIgnoreCase("true")) {
                            i = new Intent(context, MapActivity.class);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(i);
                    finish();
                }
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putBoolean("isFirstRun", false).commit();
            }
        }, SPLASH_TIME_OUT);
    }
}
