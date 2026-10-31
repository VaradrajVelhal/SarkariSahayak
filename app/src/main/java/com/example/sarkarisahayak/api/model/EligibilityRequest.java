package com.example.sarkarisahayak.api.model;

import com.google.gson.annotations.SerializedName;

public class EligibilityRequest {

    @SerializedName("age")
    private int age;

    @SerializedName("income")
    private double income;

    @SerializedName("gender")
    private String gender;

    @SerializedName("state")
    private String state;

    @SerializedName("occupation")
    private String occupation;

    @SerializedName("land_ownership")
    private String landOwnership;

    @SerializedName("disability_status")
    private String disabilityStatus;

    public EligibilityRequest() {
    }

    public EligibilityRequest(int age, double income, String gender, String state, String occupation, String landOwnership, String disabilityStatus) {
        this.age = age;
        this.income = income;
        this.gender = gender;
        this.state = state;
        this.occupation = occupation;
        this.landOwnership = landOwnership;
        this.disabilityStatus = disabilityStatus;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLandOwnership() {
        return landOwnership;
    }

    public void setLandOwnership(String landOwnership) {
        this.landOwnership = landOwnership;
    }

    public String getDisabilityStatus() {
        return disabilityStatus;
    }

    public void setDisabilityStatus(String disabilityStatus) {
        this.disabilityStatus = disabilityStatus;
    }
}
