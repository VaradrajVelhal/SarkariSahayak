package com.example.sarkarisahayak.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "SarkariSahayakSession";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private static SessionManager instance;
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    private SessionManager(Context context) {
        pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveAuthTokens(String accessToken, String refreshToken) {
        if (accessToken != null && !accessToken.trim().isEmpty()) {
            editor.putString(KEY_ACCESS_TOKEN, accessToken);
        }
        if (refreshToken != null && !refreshToken.trim().isEmpty()) {
            editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        }
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public void saveUserData(String username, String email, String fullName) {
        if (username != null && !username.trim().isEmpty()) {
            editor.putString(KEY_USERNAME, username.trim());
        }
        if (email != null && !email.trim().isEmpty()) {
            editor.putString(KEY_EMAIL, email.trim());
        }
        if (fullName != null && !fullName.trim().isEmpty()) {
            editor.putString(KEY_FULL_NAME, fullName.trim());
        }
        editor.apply();
    }

    public String getAccessToken() {
        return pref.getString(KEY_ACCESS_TOKEN, null);
    }

    public String getRefreshToken() {
        return pref.getString(KEY_REFRESH_TOKEN, null);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public String getFullName() {
        return pref.getString(KEY_FULL_NAME, "");
    }

    public boolean isLoggedIn() {
        String token = getAccessToken();
        return pref.getBoolean(KEY_IS_LOGGED_IN, false) && token != null && !token.trim().isEmpty();
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
