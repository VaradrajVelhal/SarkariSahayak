package com.example.sarkarisahayak.api.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("access")
    private String access;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token")
    private String token;

    @SerializedName("refresh")
    private String refresh;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private String detail;

    public LoginResponse() {
    }

    public String getEffectiveAccessToken() {
        if (access != null && !access.isEmpty()) return access;
        if (accessToken != null && !accessToken.isEmpty()) return accessToken;
        if (token != null && !token.isEmpty()) return token;
        return null;
    }

    public String getEffectiveRefreshToken() {
        if (refresh != null && !refresh.isEmpty()) return refresh;
        if (refreshToken != null && !refreshToken.isEmpty()) return refreshToken;
        return null;
    }

    public String getAccess() {
        return access;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getToken() {
        return token;
    }

    public String getRefresh() {
        return refresh;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMessage() {
        if (message != null && !message.isEmpty()) return message;
        if (detail != null && !detail.isEmpty()) return detail;
        return "";
    }
}
