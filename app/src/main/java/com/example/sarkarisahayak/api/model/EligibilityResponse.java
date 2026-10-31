package com.example.sarkarisahayak.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EligibilityResponse implements Serializable {

    @SerializedName("summary")
    private EligibilitySummary summary;

    @SerializedName("results")
    private List<SchemeResult> results;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private String detail;

    public EligibilityResponse() {
    }

    public EligibilityResponse(EligibilitySummary summary, List<SchemeResult> results) {
        this.summary = summary;
        this.results = results;
    }

    public EligibilitySummary getSummary() {
        return summary;
    }

    public void setSummary(EligibilitySummary summary) {
        this.summary = summary;
    }

    public List<SchemeResult> getResults() {
        return results;
    }

    public void setResults(List<SchemeResult> results) {
        this.results = results;
    }

    public String getMessage() {
        if (message != null && !message.isEmpty()) return message;
        if (detail != null && !detail.isEmpty()) return detail;
        return "";
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
