package com.example.sarkarisahayak;

import java.io.Serializable;

public class Scheme implements Serializable {
    private String id;
    private String name;
    private String shortDescription;
    private String detailedDescription;
    private String benefits;
    private String eligibilityCriteria;
    private String requiredDocuments;

    public Scheme(String id, String name, String shortDescription, String detailedDescription, String benefits, String eligibilityCriteria, String requiredDocuments) {
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

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public String getBenefits() {
        return benefits;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public String getRequiredDocuments() {
        return requiredDocuments;
    }
}
