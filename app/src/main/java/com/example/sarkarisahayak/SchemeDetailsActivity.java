package com.example.sarkarisahayak;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sarkarisahayak.api.model.SchemeResult;
import com.example.sarkarisahayak.utils.ThemeManager;
import com.example.sarkarisahayak.utils.UiUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

public class SchemeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_details);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        UiUtils.setupToolbar(this, toolbar, "Scheme Details", true);

        TextView tvSchemeName = findViewById(R.id.tvSchemeName);
        TextView tvSchemeCategory = findViewById(R.id.tvSchemeCategory);
        TextView tvEligibilityStatus = findViewById(R.id.tvEligibilityStatus);
        TextView tvSchemeDescription = findViewById(R.id.tvSchemeDescription);
        TextView tvSchemeEligibility = findViewById(R.id.tvSchemeEligibility);
        TextView tvSchemeDocuments = findViewById(R.id.tvSchemeDocuments);
        Button btnOfficialLink = findViewById(R.id.btnOfficialLink);
        Button btnBack = findViewById(R.id.btnBack);

        SchemeResult scheme = null;
        String schemeJson = getIntent().getStringExtra("SCHEME_RESULT_JSON");

        if (schemeJson != null && !schemeJson.isEmpty()) {
            try {
                scheme = new Gson().fromJson(schemeJson, SchemeResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (scheme == null) {
            try {
                scheme = (SchemeResult) getIntent().getSerializableExtra("SCHEME_RESULT");
            } catch (Exception ignored) {
            }
        }

        if (scheme != null) {
            tvSchemeName.setText(scheme.getSchemeName());

            if (scheme.getCategory() != null && !scheme.getCategory().isEmpty()) {
                tvSchemeCategory.setText("Category: " + scheme.getCategory());
                tvSchemeCategory.setVisibility(View.VISIBLE);
            } else {
                tvSchemeCategory.setVisibility(View.GONE);
            }

            if (scheme.isEligible()) {
                tvEligibilityStatus.setText("✔ ELIGIBLE");
                tvEligibilityStatus.setTextColor(Color.parseColor("#2E7D32"));
            } else {
                tvEligibilityStatus.setText("✘ NOT ELIGIBLE");
                tvEligibilityStatus.setTextColor(Color.parseColor("#D32F2F"));
            }

            tvSchemeDescription.setText(scheme.getDescription());
            tvSchemeEligibility.setText(scheme.getFormattedRules());
            tvSchemeDocuments.setText(scheme.getFormattedRequiredDocuments());

            final String link = scheme.getOfficialLink();
            if (link != null && !link.trim().isEmpty() && (link.startsWith("http://") || link.startsWith("https://"))) {
                btnOfficialLink.setVisibility(View.VISIBLE);
                btnOfficialLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                            startActivity(browserIntent);
                        } catch (Exception e) {
                            Toast.makeText(SchemeDetailsActivity.this, "Unable to open link", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                btnOfficialLink.setVisibility(View.GONE);
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}
