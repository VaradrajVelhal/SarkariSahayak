package com.example.sarkarisahayak;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sarkarisahayak.api.ApiConfig;
import com.example.sarkarisahayak.api.ApiService;
import com.example.sarkarisahayak.api.model.PreviousCheckResponse;
import com.example.sarkarisahayak.api.model.SchemeResult;
import com.example.sarkarisahayak.utils.SessionManager;
import com.example.sarkarisahayak.utils.ThemeManager;
import com.example.sarkarisahayak.utils.UiUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousChecksActivity extends AppCompatActivity {

    private LinearLayout llHistoryList;
    private TextView tvEmptyHistory;
    private ProgressBar pbHistoryLoading;
    private LinearLayout llHistoryError;
    private TextView tvHistoryErrorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_checks);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        UiUtils.setupToolbar(this, toolbar, "Previous Checks", true);

        llHistoryList = findViewById(R.id.llHistoryList);
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);
        pbHistoryLoading = findViewById(R.id.pbHistoryLoading);
        llHistoryError = findViewById(R.id.llHistoryError);
        tvHistoryErrorMsg = findViewById(R.id.tvHistoryErrorMsg);
        Button btnHistoryRetry = findViewById(R.id.btnHistoryRetry);

        if (btnHistoryRetry != null) {
            btnHistoryRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetchHistory();
                }
            });
        }

        fetchHistory();
    }

    private void fetchHistory() {
        SessionManager sessionManager = SessionManager.getInstance(this);
        if (!sessionManager.isLoggedIn()) {
            pbHistoryLoading.setVisibility(View.GONE);
            llHistoryError.setVisibility(View.GONE);
            tvEmptyHistory.setText("Please log in to view your eligibility check history.");
            tvEmptyHistory.setVisibility(View.VISIBLE);
            llHistoryList.removeAllViews();
            return;
        }

        pbHistoryLoading.setVisibility(View.VISIBLE);
        llHistoryError.setVisibility(View.GONE);
        tvEmptyHistory.setVisibility(View.GONE);
        llHistoryList.removeAllViews();

        ApiService apiService = ApiConfig.getApiService(this);

        apiService.getMyChecks().enqueue(new Callback<List<PreviousCheckResponse>>() {
            @Override
            public void onResponse(Call<List<PreviousCheckResponse>> call, Response<List<PreviousCheckResponse>> response) {
                if (isFinishing() || isDestroyed()) return;
                pbHistoryLoading.setVisibility(View.GONE);

                if (response.code() == 401) {
                    Toast.makeText(PreviousChecksActivity.this, "Session expired. Please log in again.", Toast.LENGTH_LONG).show();
                    SessionManager.getInstance(PreviousChecksActivity.this).clearSession();
                    Intent intent = new Intent(PreviousChecksActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {
                    List<PreviousCheckResponse> historyList = response.body();
                    if (historyList.isEmpty()) {
                        tvEmptyHistory.setText("No eligibility check history found.\nStart an assessment to save your results here.");
                        tvEmptyHistory.setVisibility(View.VISIBLE);
                    } else {
                        renderHistoryList(historyList);
                    }
                } else {
                    String errorMsg = parseErrorMessage(response);
                    showErrorState("Unable to load check history (" + errorMsg + ").");
                }
            }

            @Override
            public void onFailure(Call<List<PreviousCheckResponse>> call, Throwable t) {
                if (isFinishing() || isDestroyed()) return;
                pbHistoryLoading.setVisibility(View.GONE);
                showErrorState("Network Error: Could not connect to backend server.");
            }
        });
    }

    private void showErrorState(String message) {
        tvHistoryErrorMsg.setText(message);
        llHistoryError.setVisibility(View.VISIBLE);
        tvEmptyHistory.setVisibility(View.GONE);
        llHistoryList.removeAllViews();
    }

    private void renderHistoryList(List<PreviousCheckResponse> list) {
        if (list == null || list.isEmpty()) {
            tvEmptyHistory.setVisibility(View.VISIBLE);
            llHistoryList.removeAllViews();
            return;
        }

        tvEmptyHistory.setVisibility(View.GONE);
        llHistoryError.setVisibility(View.GONE);
        llHistoryList.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (final PreviousCheckResponse item : list) {
            View cardView = inflater.inflate(R.layout.item_previous_check, llHistoryList, false);

            TextView tvSchemeName = cardView.findViewById(R.id.tvHistorySchemeName);
            TextView tvStatus = cardView.findViewById(R.id.tvHistoryStatus);
            TextView tvCategory = cardView.findViewById(R.id.tvHistoryCategory);
            TextView tvDate = cardView.findViewById(R.id.tvHistoryDate);
            TextView tvDesc = cardView.findViewById(R.id.tvHistoryDescription);
            Button btnViewDetails = cardView.findViewById(R.id.btnViewHistoryDetails);

            tvSchemeName.setText(item.getEffectiveSchemeName());

            if (item.isEligible()) {
                tvStatus.setText("✔ ELIGIBLE");
                tvStatus.setTextColor(Color.parseColor("#2E7D32"));
            } else {
                tvStatus.setText("✘ NOT ELIGIBLE");
                tvStatus.setTextColor(Color.parseColor("#D32F2F"));
            }

            String category = item.getEffectiveCategory();
            if (category != null && !category.trim().isEmpty()) {
                tvCategory.setText("Category: " + category);
                tvCategory.setVisibility(View.VISIBLE);
            } else {
                tvCategory.setVisibility(View.GONE);
            }

            tvDate.setText("Checked on: " + item.getFormattedDate());

            String description = item.getEffectiveDescription();
            if (description != null && !description.trim().isEmpty()) {
                tvDesc.setText(description);
                tvDesc.setVisibility(View.VISIBLE);
            } else {
                tvDesc.setVisibility(View.GONE);
            }

            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SchemeResult schemeResult = item.getScheme();
                    if (schemeResult == null) {
                        schemeResult = new SchemeResult();
                        schemeResult.setSchemeId(item.getSchemeId());
                        schemeResult.setSchemeName(item.getEffectiveSchemeName());
                        schemeResult.setCategory(item.getEffectiveCategory());
                        schemeResult.setDescription(item.getEffectiveDescription());
                        schemeResult.setOfficialLink(item.getEffectiveOfficialLink());
                        schemeResult.setEligible(item.isEligible());
                    }

                    Intent intent = new Intent(PreviousChecksActivity.this, SchemeDetailsActivity.class);
                    intent.putExtra("SCHEME_RESULT_JSON", new Gson().toJson(schemeResult));
                    startActivity(intent);
                }
            });

            llHistoryList.addView(cardView);
        }
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
