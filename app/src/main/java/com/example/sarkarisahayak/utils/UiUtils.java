package com.example.sarkarisahayak.utils;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sarkarisahayak.R;
import com.google.android.material.appbar.MaterialToolbar;

public class UiUtils {

    public static void setupToolbar(final AppCompatActivity activity, MaterialToolbar toolbar, String title, boolean showBack) {
        if (toolbar == null || activity == null) return;

        if (title != null) {
            toolbar.setTitle(title);
        }

        if (showBack) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationContentDescription("Navigate back");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.getOnBackPressedDispatcher().onBackPressed();
                }
            });
        } else {
            toolbar.setNavigationIcon(null);
        }
    }
}
