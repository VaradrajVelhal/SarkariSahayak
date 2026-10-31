package com.example.sarkarisahayak.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    private static final String PREF_NAME = "SarkariSahayakThemePref";
    private static final String KEY_THEME_MODE = "theme_mode";

    public static final int THEME_SYSTEM = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
    public static final int THEME_LIGHT = AppCompatDelegate.MODE_NIGHT_NO;
    public static final int THEME_DARK = AppCompatDelegate.MODE_NIGHT_YES;

    public static void applyTheme(Context context) {
        int themeMode = getSavedThemeMode(context);
        AppCompatDelegate.setDefaultNightMode(themeMode);
    }

    public static void setThemeMode(Context context, int themeMode) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        pref.edit().putInt(KEY_THEME_MODE, themeMode).apply();
        AppCompatDelegate.setDefaultNightMode(themeMode);
    }

    public static int getSavedThemeMode(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getInt(KEY_THEME_MODE, THEME_SYSTEM);
    }
}
