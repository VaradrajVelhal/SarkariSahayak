package com.example.sarkarisahayak;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sarkarisahayak.api.ApiConfig;
import com.example.sarkarisahayak.api.ApiService;
import com.example.sarkarisahayak.api.model.LoginRequest;
import com.example.sarkarisahayak.api.model.LoginResponse;
import com.example.sarkarisahayak.utils.SessionManager;
import com.example.sarkarisahayak.utils.ThemeManager;
import com.example.sarkarisahayak.utils.UiUtils;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private ProgressBar pbLoginLoading;
    private Button btnLoginSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        UiUtils.setupToolbar(this, toolbar, "Login", true);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        pbLoginLoading = findViewById(R.id.pbLoginLoading);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);

        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);
        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void attemptLogin() {
        final String usernameOrEmail = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(usernameOrEmail)) {
            etEmail.setError("Username or Email is required");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        pbLoginLoading.setVisibility(View.VISIBLE);
        btnLoginSubmit.setEnabled(false);

        ApiService apiService = ApiConfig.getApiService(this);
        LoginRequest request = new LoginRequest(usernameOrEmail, password);

        apiService.loginUser(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (isFinishing() || isDestroyed()) return;

                pbLoginLoading.setVisibility(View.GONE);
                btnLoginSubmit.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getEffectiveAccessToken();
                    String refreshToken = loginResponse.getEffectiveRefreshToken();

                    if (token != null && !token.isEmpty()) {
                        SessionManager sessionManager = SessionManager.getInstance(LoginActivity.this);
                        sessionManager.saveAuthTokens(token, refreshToken);

                        String username = loginResponse.getUsername() != null ? loginResponse.getUsername() : usernameOrEmail;
                        String email = loginResponse.getEmail() != null ? loginResponse.getEmail() : "";
                        String fullName = loginResponse.getFullName() != null ? loginResponse.getFullName() : "";

                        sessionManager.saveUserData(username, email, fullName);

                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }

                String errorMsg = parseErrorMessage(response);
                Toast.makeText(LoginActivity.this, "Login Failed: " + errorMsg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (isFinishing() || isDestroyed()) return;

                pbLoginLoading.setVisibility(View.GONE);
                btnLoginSubmit.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Network Error: Unable to reach backend server.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String parseErrorMessage(Response<?> response) {
        if (response.errorBody() != null) {
            try {
                String errorJson = response.errorBody().string();
                if (errorJson != null && !errorJson.trim().isEmpty()) {
                    return errorJson.replace("{", "").replace("}", "").replace("[", "").replace("]", "").replace("\"", "");
                }
            } catch (Exception ignored) {
            }
        }
        return "HTTP Error " + response.code();
    }
}
