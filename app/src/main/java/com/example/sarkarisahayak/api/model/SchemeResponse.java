package com.example.sarkarisahayak.api.model;

import com.google.gson.annotations.SerializedName;

public class SchemeResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("short_description")
    private String shortDescription;

    @SerializedName("detailed_description")
    private String detailedDescription;

    @SerializedName("benefits")
    private String benefits;

    @SerializedName("eligibility_criteria")
    private String eligibilityCriteria;

    @SerializedName("required_documents")
    private String requiredDocuments;

    public SchemeResponse() {
    }

    public SchemeResponse(String id, String name, String shortDescription, String detailedDescription, String benefits, String eligibilityCriteria, String requiredDocuments) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.benefits = benefits;
        this.eligibilityCriteria = eligibilityCriteria;
        this.requiredDocuments = requiredDocuments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public String getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(String requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }
}
