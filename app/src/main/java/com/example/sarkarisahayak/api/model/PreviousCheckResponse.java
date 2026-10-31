package com.example.sarkarisahayak.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PreviousCheckResponse implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("scheme_id")
    private String schemeId;

    @SerializedName("scheme_name")
    private String schemeName;

    @SerializedName("scheme_category")
    private String schemeCategory;

    @SerializedName("scheme_description")
    private String schemeDescription;

    @SerializedName("scheme_official_link")
    private String schemeOfficialLink;

    @SerializedName("scheme")
    private SchemeResult scheme;

    @SerializedName("is_eligible")
    private boolean isEligible;

    @SerializedName("checked_on")
    private String checkedOn;

    public PreviousCheckResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchemeId() {
        if (schemeId != null && !schemeId.isEmpty()) return schemeId;
        if (scheme != null && scheme.getSchemeId() != null) return scheme.getSchemeId();
        return "";
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getEffectiveSchemeName() {
        if (schemeName != null && !schemeName.trim().isEmpty()) return schemeName;
        if (scheme != null && scheme.getSchemeName() != null && !scheme.getSchemeName().trim().isEmpty()) return scheme.getSchemeName();
        return "Government Scheme";
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getEffectiveCategory() {
        if (schemeCategory != null && !schemeCategory.trim().isEmpty()) return schemeCategory;
        if (scheme != null && scheme.getCategory() != null) return scheme.getCategory();
        return "";
    }

    public String getSchemeCategory() {
        return schemeCategory;
    }

    public void setSchemeCategory(String schemeCategory) {
        this.schemeCategory = schemeCategory;
    }

    public String getEffectiveDescription() {
        if (schemeDescription != null && !schemeDescription.trim().isEmpty()) return schemeDescription;
        if (scheme != null && scheme.getDescription() != null) return scheme.getDescription();
        return "";
    }

    public String getSchemeDescription() {
        return schemeDescription;
    }

    public void setSchemeDescription(String schemeDescription) {
        this.schemeDescription = schemeDescription;
    }

    public String getEffectiveOfficialLink() {
        if (schemeOfficialLink != null && !schemeOfficialLink.trim().isEmpty()) return schemeOfficialLink;
        if (scheme != null && scheme.getOfficialLink() != null) return scheme.getOfficialLink();
        return "";
    }

    public String getSchemeOfficialLink() {
        return schemeOfficialLink;
    }

    public void setSchemeOfficialLink(String schemeOfficialLink) {
        this.schemeOfficialLink = schemeOfficialLink;
    }

    public SchemeResult getScheme() {
        return scheme;
    }

    public void setScheme(SchemeResult scheme) {
        this.scheme = scheme;
    }

    public boolean isEligible() {
        return isEligible;
    }

    public void setEligible(boolean eligible) {
        isEligible = eligible;
    }

    public String getCheckedOn() {
        return checkedOn;
    }

    public void setCheckedOn(String checkedOn) {
        this.checkedOn = checkedOn;
    }

    public String getFormattedDate() {
        if (checkedOn == null || checkedOn.trim().isEmpty()) {
            return "Date N/A";
        }
        try {
            String datePart = checkedOn;
            if (checkedOn.contains("T")) {
                datePart = checkedOn.split("T")[0];
            } else if (checkedOn.contains(" ")) {
                datePart = checkedOn.split(" ")[0];
            }
            return datePart;
        } catch (Exception e) {
            return checkedOn;
        }
    }
}
