package com.example.appgym.persistencia.api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WgerApi {
    @GET("exercise/search/?language=2")
    Call<JsonElement> searchExercises(@Query("term") String term);
}
