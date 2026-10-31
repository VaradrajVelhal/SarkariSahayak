package com.example.sarkarisahayak.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EligibilitySummary implements Serializable {

    @SerializedName("total_evaluated")
    private int totalEvaluated;

    @SerializedName("eligible_count")
    private int eligibleCount;

    @SerializedName("not_eligible_count")
    private int notEligibleCount;

    public EligibilitySummary() {
    }

    public EligibilitySummary(int totalEvaluated, int eligibleCount, int notEligibleCount) {
        this.totalEvaluated = totalEvaluated;
        this.eligibleCount = eligibleCount;
        this.notEligibleCount = notEligibleCount;
    }

    public int getTotalEvaluated() {
        return totalEvaluated;
    }

    public void setTotalEvaluated(int totalEvaluated) {
        this.totalEvaluated = totalEvaluated;
    }

    public int getEligibleCount() {
        return eligibleCount;
    }

    public void setEligibleCount(int eligibleCount) {
        this.eligibleCount = eligibleCount;
    }

    public int getNotEligibleCount() {
        return notEligibleCount;
    }

    public void setNotEligibleCount(int notEligibleCount) {
        this.notEligibleCount = notEligibleCount;
    }
}
