package Modules;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import id.ac.umn.mobile.mymaps.LoginActivity;

/**
 * Created by Reynaldi on 7/21/2017.
 */
public class SessionManagement {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context ctx;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "prefLogin";
    private static final String IS_LOGIN = "isLoggedIn";
    public static final String KEY_UNAME = "name";
    public static final String KEY_PASS = "password";

    public SessionManagement(Context context){
        this.ctx = context;
        pref = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    ///LOGIN SESSION///
    public void createLoginSession(String name,String password){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_UNAME, name);
        editor.putString(KEY_PASS, password);

        editor.commit();
    }

    //check status login
    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(ctx, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ctx.startActivity(i);
        }
    }
    //isi datanya
    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_UNAME, pref.getString(KEY_UNAME, null));
        user.put(KEY_PASS, pref.getString(KEY_PASS, null));

        return user;
    }
    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(ctx, LoginActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        ctx.startActivity(i);
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
