package com.example.sarkarisahayak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sarkarisahayak.utils.SessionManager;
import com.example.sarkarisahayak.utils.ThemeManager;
import com.example.sarkarisahayak.utils.UiUtils;
import com.google.android.material.appbar.MaterialToolbar;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        UiUtils.setupToolbar(this, toolbar, "Profile & Settings", true);

        final SessionManager sessionManager = SessionManager.getInstance(this);

        TextView tvProfileName = findViewById(R.id.tvProfileName);
        TextView tvProfileEmail = findViewById(R.id.tvProfileEmail);

        String username = sessionManager.getUsername();
        String email = sessionManager.getEmail();
        String fullName = sessionManager.getFullName();

        if (fullName != null && !fullName.isEmpty()) {
            tvProfileName.setText(fullName);
        } else if (username != null && !username.isEmpty()) {
            tvProfileName.setText(username);
        }

        if (email != null && !email.isEmpty()) {
            tvProfileEmail.setText(email);
        }

        RadioGroup rgThemeMode = findViewById(R.id.rgThemeMode);
        RadioButton rbThemeSystem = findViewById(R.id.rbThemeSystem);
        RadioButton rbThemeLight = findViewById(R.id.rbThemeLight);
        RadioButton rbThemeDark = findViewById(R.id.rbThemeDark);

        int currentTheme = ThemeManager.getSavedThemeMode(this);
        if (currentTheme == ThemeManager.THEME_LIGHT) {
            rbThemeLight.setChecked(true);
        } else if (currentTheme == ThemeManager.THEME_DARK) {
            rbThemeDark.setChecked(true);
        } else {
            rbThemeSystem.setChecked(true);
        }

        rgThemeMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedMode;
                if (checkedId == R.id.rbThemeLight) {
                    selectedMode = ThemeManager.THEME_LIGHT;
                } else if (checkedId == R.id.rbThemeDark) {
                    selectedMode = ThemeManager.THEME_DARK;
                } else {
                    selectedMode = ThemeManager.THEME_SYSTEM;
                }

                if (selectedMode != ThemeManager.getSavedThemeMode(ProfileActivity.this)) {
                    ThemeManager.setThemeMode(ProfileActivity.this, selectedMode);
                }
            }
        });

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.clearSession();
                Toast.makeText(ProfileActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
