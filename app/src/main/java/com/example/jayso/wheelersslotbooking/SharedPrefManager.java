package com.example.jayso.wheelersslotbooking;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.accounts.AccountManager.KEY_PASSWORD;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_ID = "U_ID";
    private static final String KEY_USER_EMAIL = "Email_ID";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_USER_F_NAME = "U_FirstName";
    private static final String KEY_USER_L_NAME = "U_LastName";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_CONTACT = "Contact_No";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean UserLogin(int U_ID, String Email_ID, String Password,String U_FirstName,String U_LastName,String Gender,String Contact_No) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, U_ID);
        editor.putString(KEY_USER_F_NAME, U_FirstName);
        editor.putString(KEY_USER_L_NAME, U_LastName);
        editor.putString(KEY_USER_EMAIL, Email_ID);
        editor.putString(KEY_PASSWORD, Password);
        editor.putString(KEY_GENDER, Gender);
        editor.putString(KEY_CONTACT, Contact_No);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL, null) !=null){
            return  true;
        }
        return  false;
    }
    public  boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return  true;
    }


    public String getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID,null);
    }
    public String getUserFName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_F_NAME,null);
    }
    public String getUserLName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_L_NAME,null);
    }
    public String getUserGender(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_GENDER,null);
    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }
    public String getUserContactNo(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CONTACT,null);
    }

}
