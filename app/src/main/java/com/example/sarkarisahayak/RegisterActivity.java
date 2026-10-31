package com.example.sarkarisahayak;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sarkarisahayak.api.ApiConfig;
import com.example.sarkarisahayak.api.ApiService;
import com.example.sarkarisahayak.api.model.RegisterRequest;
import com.example.sarkarisahayak.api.model.RegisterResponse;
import com.example.sarkarisahayak.utils.ThemeManager;
import com.example.sarkarisahayak.utils.UiUtils;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etName;
    private EditText etEmailReg;
    private EditText etPasswordReg;
    private EditText etConfirmPasswordReg;
    private ProgressBar pbRegisterLoading;
    private Button btnRegisterSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        UiUtils.setupToolbar(this, toolbar, "Create Account", true);

        etUsername = findViewById(R.id.etUsername);
        etName = findViewById(R.id.etName);
        etEmailReg = findViewById(R.id.etEmailReg);
        etPasswordReg = findViewById(R.id.etPasswordReg);
        etConfirmPasswordReg = findViewById(R.id.etConfirmPasswordReg);
        pbRegisterLoading = findViewById(R.id.pbRegisterLoading);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);

        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndRegister();
            }
        });

        Button btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validateAndRegister() {
        String username = etUsername.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String email = etEmailReg.getText().toString().trim();
        String password = etPasswordReg.getText().toString().trim();
        String confirmPassword = etConfirmPasswordReg.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Username is required");
            etUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            etName.setError("Full name is required");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmailReg.setError("Email address is required");
            etEmailReg.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailReg.setError("Please enter a valid email address");
            etEmailReg.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPasswordReg.setError("Password is required");
            etPasswordReg.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPasswordReg.setError("Password must be at least 6 characters");
            etPasswordReg.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPasswordReg.setError("Please confirm your password");
            etConfirmPasswordReg.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPasswordReg.setError("Passwords do not match");
            etConfirmPasswordReg.requestFocus();
            return;
        }

        pbRegisterLoading.setVisibility(View.VISIBLE);
        btnRegisterSubmit.setEnabled(false);

        ApiService apiService = ApiConfig.getApiService(this);
        RegisterRequest request = new RegisterRequest(username, name, email, password, confirmPassword);

        apiService.registerUser(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (isFinishing() || isDestroyed()) return;

                pbRegisterLoading.setVisibility(View.GONE);
                btnRegisterSubmit.setEnabled(true);

                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration Successful! Please log in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMsg = parseErrorMessage(response);
                    Toast.makeText(RegisterActivity.this, "Registration Failed: " + errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                if (isFinishing() || isDestroyed()) return;

                pbRegisterLoading.setVisibility(View.GONE);
                btnRegisterSubmit.setEnabled(true);
                Toast.makeText(RegisterActivity.this, "Network Error: Could not connect to backend server.", Toast.LENGTH_LONG).show();
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
