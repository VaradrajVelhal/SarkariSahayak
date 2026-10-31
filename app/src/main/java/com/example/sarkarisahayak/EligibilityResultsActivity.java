package com.example.sarkarisahayak;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sarkarisahayak.api.model.EligibilityResponse;
import com.example.sarkarisahayak.api.model.EligibilitySummary;
import com.example.sarkarisahayak.api.model.SchemeResult;
import com.example.sarkarisahayak.utils.ThemeManager;
import com.example.sarkarisahayak.utils.UiUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.List;

public class EligibilityResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibility_results);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        UiUtils.setupToolbar(this, toolbar, "Eligibility Results", true);

        TextView tvTotalEvaluated = findViewById(R.id.tvTotalEvaluated);
        TextView tvEligibleCount = findViewById(R.id.tvEligibleCount);
        TextView tvNotEligibleCount = findViewById(R.id.tvNotEligibleCount);
        TextView tvEmptyState = findViewById(R.id.tvEmptyState);
        LinearLayout llSchemesList = findViewById(R.id.llSchemesList);

        EligibilityResponse response = null;
        String responseJson = getIntent().getStringExtra("ELIGIBILITY_RESPONSE_JSON");

        if (responseJson != null && !responseJson.isEmpty()) {
            try {
                response = new Gson().fromJson(responseJson, EligibilityResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (response == null) {
            try {
                response = (EligibilityResponse) getIntent().getSerializableExtra("ELIGIBILITY_RESPONSE");
            } catch (Exception ignored) {
            }
        }

        if (response != null && response.getSummary() != null) {
            EligibilitySummary summary = response.getSummary();
            tvTotalEvaluated.setText("Evaluated: " + summary.getTotalEvaluated());
            tvEligibleCount.setText("Eligible: " + summary.getEligibleCount());
            tvNotEligibleCount.setText("Not Eligible: " + summary.getNotEligibleCount());
        } else {
            tvTotalEvaluated.setText("Evaluated: 0");
            tvEligibleCount.setText("Eligible: 0");
            tvNotEligibleCount.setText("Not Eligible: 0");
        }

        List<SchemeResult> schemeResults = (response != null) ? response.getResults() : null;

        if (schemeResults == null || schemeResults.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            return;
        }

        tvEmptyState.setVisibility(View.GONE);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (final SchemeResult scheme : schemeResults) {
            View cardView = inflater.inflate(R.layout.item_scheme_card, llSchemesList, false);

            TextView tvTitle = cardView.findViewById(R.id.tvSchemeCardTitle);
            TextView tvStatus = cardView.findViewById(R.id.tvSchemeCardStatus);
            TextView tvCategory = cardView.findViewById(R.id.tvSchemeCardCategory);
            TextView tvDesc = cardView.findViewById(R.id.tvSchemeCardDesc);
            Button btnViewDetails = cardView.findViewById(R.id.btnViewDetails);

            tvTitle.setText(scheme.getSchemeName());

            if (tvCategory != null && scheme.getCategory() != null && !scheme.getCategory().isEmpty()) {
                tvCategory.setText("Category: " + scheme.getCategory());
                tvCategory.setVisibility(View.VISIBLE);
            } else if (tvCategory != null) {
                tvCategory.setVisibility(View.GONE);
            }

            tvDesc.setText(scheme.getDescription());

            if (scheme.isEligible()) {
                tvStatus.setText("✔ ELIGIBLE");
                tvStatus.setTextColor(Color.parseColor("#2E7D32"));
            } else {
                tvStatus.setText("✘ NOT ELIGIBLE");
                tvStatus.setTextColor(Color.parseColor("#D32F2F"));
            }

            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EligibilityResultsActivity.this, SchemeDetailsActivity.class);
                    intent.putExtra("SCHEME_RESULT_JSON", new Gson().toJson(scheme));
                    startActivity(intent);
                }
            });

            llSchemesList.addView(cardView);
        }
    }
}
