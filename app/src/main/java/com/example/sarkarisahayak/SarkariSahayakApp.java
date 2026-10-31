package com.example.sarkarisahayak;

import android.app.Application;

import com.example.sarkarisahayak.utils.ThemeManager;

public class SarkariSahayakApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ThemeManager.applyTheme(this);
    }
}
