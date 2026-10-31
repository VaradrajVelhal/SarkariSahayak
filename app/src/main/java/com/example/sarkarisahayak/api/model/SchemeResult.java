package com.example.sarkarisahayak.api.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SchemeResult implements Serializable {

    @SerializedName("scheme_id")
    private String schemeId;

    @SerializedName("scheme_name")
    private String schemeName;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private String category;

    @SerializedName("official_link")
    private String officialLink;

    @SerializedName("is_eligible")
    private boolean isEligible;

    @SerializedName("rules")
    private JsonElement rules;

    @SerializedName("required_documents")
    private JsonElement requiredDocuments;

    public SchemeResult() {
    }

    public String getSchemeId() {
        return schemeId != null ? schemeId : "";
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeName() {
        return schemeName != null ? schemeName : "";
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category != null ? category : "";
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOfficialLink() {
        return officialLink != null ? officialLink : "";
    }

    public void setOfficialLink(String officialLink) {
        this.officialLink = officialLink;
    }

    public boolean isEligible() {
        return isEligible;
    }

    public void setEligible(boolean eligible) {
        isEligible = eligible;
    }

    public JsonElement getRules() {
        return rules;
    }

    public void setRules(JsonElement rules) {
        this.rules = rules;
    }

    public JsonElement getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(JsonElement requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public String getFormattedRequiredDocuments() {
        if (requiredDocuments == null || requiredDocuments.isJsonNull()) {
            return "No specific documents listed.";
        }
        try {
            if (requiredDocuments.isJsonArray()) {
                StringBuilder sb = new StringBuilder();
                JsonArray arr = requiredDocuments.getAsJsonArray();
                for (int i = 0; i < arr.size(); i++) {
                    sb.append("• ").append(arr.get(i).getAsString()).append("\n");
                }
                return sb.toString().trim();
            } else if (requiredDocuments.isJsonPrimitive()) {
                return requiredDocuments.getAsString();
            }
        } catch (Exception e) {
            return requiredDocuments.toString();
        }
        return requiredDocuments.toString();
    }

    public String getFormattedRules() {
        if (rules == null || rules.isJsonNull()) {
            return "No specific eligibility rules returned.";
        }
        try {
            if (rules.isJsonArray()) {
                StringBuilder sb = new StringBuilder();
                JsonArray arr = rules.getAsJsonArray();
                for (int i = 0; i < arr.size(); i++) {
                    JsonElement elem = arr.get(i);
                    if (elem.isJsonObject()) {
                        JsonObject obj = elem.getAsJsonObject();
                        String ruleName = obj.has("rule") ? obj.get("rule").getAsString() : (obj.has("name") ? obj.get("name").getAsString() : obj.toString());
                        String status = obj.has("passed") ? (obj.get("passed").getAsBoolean() ? "Passed" : "Failed") : (obj.has("status") ? obj.get("status").getAsString() : "");
                        sb.append("• ").append(ruleName).append(status.isEmpty() ? "" : " (" + status + ")").append("\n");
                    } else {
                        sb.append("• ").append(elem.getAsString()).append("\n");
                    }
                }
                return sb.toString().trim();
            } else if (rules.isJsonObject()) {
                StringBuilder sb = new StringBuilder();
                JsonObject obj = rules.getAsJsonObject();
                for (String key : obj.keySet()) {
                    sb.append("• ").append(key).append(": ").append(obj.get(key).toString().replace("\"", "")).append("\n");
                }
                return sb.toString().trim();
            } else if (rules.isJsonPrimitive()) {
                return rules.getAsString();
            }
        } catch (Exception e) {
            return rules.toString();
        }
        return rules.toString();
    }
}
