package com.example.appgym.persistencia.api;

import com.example.appgym.utils.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = Constantes.URL_BASE_WGER;

    public static WgerApi getWgerApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WgerApi.class);
    }
}