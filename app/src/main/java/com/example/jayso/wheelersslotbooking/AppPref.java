package com.example.jayso.wheelersslotbooking;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPref {


	/*For Set and Get String Value in Preference*/ 
	public static void setValue(String key, String value, Context context) {

		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getValue(String key, Context context) {

		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		String value = preferences.getString(key, null);
		return value;
	}
	
	public static String getValue(String key, String defaultValue, Context context) {

		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		String value = preferences.getString(key, defaultValue);
		return value;
	}
		
	/*For Set and Get Boolean Value in Preference*/
	public static void setValue(String key, boolean value, Context context) {

		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}	
	
	public static boolean getBooleanValue(String key, Context context) {

		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		boolean value = preferences.getBoolean(key, false);
		return value;
	}	
	
	public static void setIntValue(String key, int value, Context context) {

		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getIntValue(String key, Context context) {

		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		int value = preferences.getInt(key, 0);
		return value;
	}
	
	//For removing a value from Preference
	public static void clearAll(Context context)
	 {
	  SharedPreferences preferences = context.getSharedPreferences(
	    context.getPackageName(), Context.MODE_PRIVATE);
	  preferences.edit().clear().commit();;
	  
	  
	 }
	public static void removeValue(String key, Context context)
	{
		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();
	}	
}