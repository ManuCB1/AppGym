package com.example.appgym.persistencia.api.youtube;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApi {
    @GET("youtube/v3/search")
    Call<YouTubeResponse> getVideos(@Query("part") String part,
                                    @Query("q") String query,
                                    @Query("type") String type,
                                    @Query("key") String apiKey);
}

