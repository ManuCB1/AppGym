package com.example.appgym.persistencia.api.youtube;

import com.google.gson.annotations.SerializedName;

public class Id {
    @SerializedName("videoId")
    private String videoId;

    public String getVideoId() {
        return videoId;
    }
}
