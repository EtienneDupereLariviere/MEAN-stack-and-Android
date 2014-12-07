package com.example.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ConnectionStatus {

private static final String SHARED_PREF_CONNECTION_STATUS = "sharedPrefConnexionStatus";
    
    public static SharedPreferences getSharedPref(Activity activity)
    {
        return activity.getSharedPreferences(SHARED_PREF_CONNECTION_STATUS, Context.MODE_PRIVATE);
    }
    
    public static boolean IsSignedIn(Activity activity)
    {
        return getSharedPref(activity).getBoolean(Constants.IS_SIGNED_IN, false);
    }
    
    public static String getUserEmailSignedIn(Activity activity)
    {
        return getSharedPref(activity).getString(Constants.SIGNED_IN_USER_EMAIL, null);
    }
    
    public static String getUsernameSignIn(Activity activity)
    {
        return getSharedPref(activity).getString(Constants.SIGNED_IN_USERNAME, null);
    }
    
    public static String getCookie(Activity activity)
    {
        return getSharedPref(activity).getString(Constants.USER_COOKIE, null);
    }
    
    public static String getUserId(Activity activity)
    {
        return getSharedPref(activity).getString(Constants.SIGNED_IN_USER_ID, null);
    }
    
    public static void SetCookie(Activity activity, String cookie)
    {
        SharedPreferences.Editor editor = getSharedPref(activity).edit();
        editor.putString(Constants.USER_COOKIE, cookie);
        editor.commit();    
    }
    
    public static void SignIn(Activity activity, String email, String username, String userId)
    {
        SharedPreferences.Editor editor = getSharedPref(activity).edit();
        editor.putBoolean(Constants.IS_SIGNED_IN, true);
        editor.putString(Constants.SIGNED_IN_USER_EMAIL, email);
        editor.putString(Constants.SIGNED_IN_USERNAME, username);
        editor.putString(Constants.SIGNED_IN_USER_ID, userId);
        editor.commit();    
    }
    
    public static void SignOut(Activity activity)
    {
        SharedPreferences.Editor editor = getSharedPref(activity).edit();
        editor.putBoolean(Constants.IS_SIGNED_IN, false);
        editor.putString(Constants.SIGNED_IN_USER_EMAIL, "");
        editor.putString(Constants.SIGNED_IN_USERNAME, "");
        editor.putString(Constants.USER_COOKIE, "");
        
        editor.commit();       
    }

}
