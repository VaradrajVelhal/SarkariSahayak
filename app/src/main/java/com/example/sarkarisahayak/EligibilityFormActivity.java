package com.example.sarkarisahayak;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sarkarisahayak.api.ApiConfig;
import com.example.sarkarisahayak.api.ApiService;
import com.example.sarkarisahayak.api.model.EligibilityRequest;
import com.example.sarkarisahayak.api.model.EligibilityResponse;
import com.example.sarkarisahayak.utils.SessionManager;
import com.example.sarkarisahayak.utils.ThemeManager;
import com.example.sarkarisahayak.utils.UiUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EligibilityFormActivity extends AppCompatActivity {

    private EditText etAge;
    private EditText etAnnualIncome;
    private Spinner spGender;
    private Spinner spState;
    private Spinner spOccupation;
    private Spinner spLandOwnership;
    private Spinner spDisability;
    private ProgressBar pbEligibilityLoading;
    private Button btnSubmitEligibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibility_form);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        UiUtils.setupToolbar(this, toolbar, "Check Eligibility", true);

        etAge = findViewById(R.id.etAge);
        etAnnualIncome = findViewById(R.id.etAnnualIncome);
        spGender = findViewById(R.id.spGender);
        spState = findViewById(R.id.spState);
        spOccupation = findViewById(R.id.spOccupation);
        spLandOwnership = findViewById(R.id.spLandOwnership);
        spDisability = findViewById(R.id.spDisability);
        pbEligibilityLoading = findViewById(R.id.pbEligibilityLoading);
        btnSubmitEligibility = findViewById(R.id.btnSubmitEligibility);

        btnSubmitEligibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmit();
            }
        });
    }

    private void validateAndSubmit() {
        String ageStr = etAge.getText().toString().trim();
        String incomeStr = etAnnualIncome.getText().toString().trim();

        if (TextUtils.isEmpty(ageStr)) {
            etAge.setError("Age is required");
            etAge.requestFocus();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0 || age > 120) {
                etAge.setError("Please enter a valid positive age");
                etAge.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etAge.setError("Please enter a valid number for age");
            etAge.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(incomeStr)) {
            etAnnualIncome.setError("Annual income is required");
            etAnnualIncome.requestFocus();
            return;
        }

        double income;
        try {
            income = Double.parseDouble(incomeStr);
            if (income < 0) {
                etAnnualIncome.setError("Income must be a valid non-negative number");
                etAnnualIncome.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etAnnualIncome.setError("Please enter a valid number for income");
            etAnnualIncome.requestFocus();
            return;
        }

        if (spGender.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spState.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select State", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spOccupation.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Occupation", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spLandOwnership.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Land Ownership status", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spDisability.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Disability status", Toast.LENGTH_SHORT).show();
            return;
        }

        String gender = spGender.getSelectedItem().toString().toLowerCase();
        String state = spState.getSelectedItem().toString();
        String occupation = spOccupation.getSelectedItem().toString().toLowerCase();
        String landOwnership = spLandOwnership.getSelectedItem().toString().toLowerCase();
        String disability = spDisability.getSelectedItem().toString().toLowerCase();

        pbEligibilityLoading.setVisibility(View.VISIBLE);
        btnSubmitEligibility.setEnabled(false);

        ApiService apiService = ApiConfig.getApiService(this);
        EligibilityRequest request = new EligibilityRequest(age, income, gender, state, occupation, landOwnership, disability);

        apiService.checkEligibility(request).enqueue(new Callback<EligibilityResponse>() {
            @Override
            public void onResponse(Call<EligibilityResponse> call, Response<EligibilityResponse> response) {
                if (isFinishing() || isDestroyed()) return;

                pbEligibilityLoading.setVisibility(View.GONE);
                btnSubmitEligibility.setEnabled(true);

                if (response.code() == 401) {
                    Toast.makeText(EligibilityFormActivity.this, "Session expired. Please log in again.", Toast.LENGTH_LONG).show();
                    SessionManager.getInstance(EligibilityFormActivity.this).clearSession();
                    Intent intent = new Intent(EligibilityFormActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {
                    EligibilityResponse eligibilityResponse = response.body();
                    String jsonResponse = new Gson().toJson(eligibilityResponse);

                    Intent intent = new Intent(EligibilityFormActivity.this, EligibilityResultsActivity.class);
                    intent.putExtra("ELIGIBILITY_RESPONSE_JSON", jsonResponse);
                    startActivity(intent);
                } else {
                    String errorMsg = parseErrorMessage(response);
                    Toast.makeText(EligibilityFormActivity.this, "Assessment Failed: " + errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EligibilityResponse> call, Throwable t) {
                if (isFinishing() || isDestroyed()) return;

                pbEligibilityLoading.setVisibility(View.GONE);
                btnSubmitEligibility.setEnabled(true);
                Toast.makeText(EligibilityFormActivity.this, "Network Error: Unable to reach backend server.", Toast.LENGTH_LONG).show();
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
