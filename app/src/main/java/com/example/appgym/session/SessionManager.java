package com.example.appgym.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.appgym.model.User;

public class SessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public boolean createLoginSession(User user) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putInt(KEY_HEIGHT, user.getHeight());
        editor.putFloat(KEY_WEIGHT,  (float) user.getWeight());
        editor.commit();
        return true;
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }

    public boolean isLogged() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public int getHeight() {
        return pref.getInt(KEY_HEIGHT, 0);
    }

    public Float getWeight() {
        return pref.getFloat(KEY_WEIGHT, 0.0f);
    }
}
