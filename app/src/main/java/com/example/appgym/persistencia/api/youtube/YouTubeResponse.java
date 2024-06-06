package com.example.appgym.persistencia.api.youtube;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YouTubeResponse {
    @SerializedName("items")
    private List<YouTubeItem> items;

    public List<YouTubeItem> getItems() {
        return items;
    }
}

