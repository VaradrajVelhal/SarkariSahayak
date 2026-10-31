package com.example.sarkarisahayak.api;

import com.example.sarkarisahayak.api.model.EligibilityRequest;
import com.example.sarkarisahayak.api.model.EligibilityResponse;
import com.example.sarkarisahayak.api.model.LoginRequest;
import com.example.sarkarisahayak.api.model.LoginResponse;
import com.example.sarkarisahayak.api.model.PreviousCheckResponse;
import com.example.sarkarisahayak.api.model.RegisterRequest;
import com.example.sarkarisahayak.api.model.RegisterResponse;
import com.example.sarkarisahayak.api.model.SchemeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("register/")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

    @POST("login/")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    @GET("schemes/")
    Call<List<SchemeResponse>> getSchemes();

    @POST("check-eligibility/")
    Call<EligibilityResponse> checkEligibility(@Body EligibilityRequest request);

    @GET("my-checks/")
    Call<List<PreviousCheckResponse>> getMyChecks();

    @GET("schemes/{id}/")
    Call<SchemeResponse> getSchemeDetails(@Path("id") String id);
}
