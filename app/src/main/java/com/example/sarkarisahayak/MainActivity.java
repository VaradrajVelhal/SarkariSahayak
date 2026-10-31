package com.example.sarkarisahayak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sarkarisahayak.utils.SessionManager;
import com.example.sarkarisahayak.utils.ThemeManager;
import com.example.sarkarisahayak.utils.UiUtils;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tvWelcomeUser;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnLogoutHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        UiUtils.setupToolbar(this, toolbar, "Sarkari Sahayak", false);

        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogoutHome = findViewById(R.id.btnLogoutHome);

        Button btnCheckEligibility = findViewById(R.id.btnCheckEligibility);
        btnCheckEligibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EligibilityFormActivity.class));
            }
        });

        Button btnPreviousChecks = findViewById(R.id.btnPreviousChecks);
        btnPreviousChecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PreviousChecksActivity.class));
            }
        });

        Button btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        if (btnLogoutHome != null) {
            btnLogoutHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SessionManager.getInstance(MainActivity.this).clearSession();
                    Toast.makeText(MainActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                    updateAuthenticationUI();
                }
            });
        }

        updateAuthenticationUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAuthenticationUI();
    }

    private void updateAuthenticationUI() {
        SessionManager sessionManager = SessionManager.getInstance(this);
        boolean isLoggedIn = sessionManager.isLoggedIn();

        Log.d(TAG, "updateAuthenticationUI: isLoggedIn = " + isLoggedIn);

        if (isLoggedIn) {
            String fullName = sessionManager.getFullName();
            String username = sessionManager.getUsername();
            String email = sessionManager.getEmail();

            String displayName = null;
            if (fullName != null && !fullName.trim().isEmpty()) {
                displayName = fullName.trim();
            } else if (username != null && !username.trim().isEmpty()) {
                displayName = username.trim();
            } else if (email != null && !email.trim().isEmpty()) {
                displayName = email.trim();
            }

            if (displayName != null) {
                tvWelcomeUser.setText("Welcome, " + displayName + "!");
            } else {
                tvWelcomeUser.setText("Welcome to Sarkari Sahayak");
            }

            if (btnLogin != null) {
                btnLogin.setVisibility(View.GONE);
            }
            if (btnRegister != null) {
                btnRegister.setVisibility(View.GONE);
            }
            if (btnLogoutHome != null) {
                btnLogoutHome.setVisibility(View.VISIBLE);
            }
        } else {
            tvWelcomeUser.setText("Welcome to Sarkari Sahayak");
            if (btnLogin != null) {
                btnLogin.setVisibility(View.VISIBLE);
            }
            if (btnRegister != null) {
                btnRegister.setVisibility(View.VISIBLE);
            }
            if (btnLogoutHome != null) {
                btnLogoutHome.setVisibility(View.GONE);
            }
        }
    }
}
