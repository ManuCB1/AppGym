package com.example.appgym.persistencia.api.youtube;

import com.google.gson.annotations.SerializedName;

public class YouTubeItem {
    @SerializedName("snippet")
    private Snippet snippet;

    @SerializedName("id")
    private Id id;

    public Snippet getSnippet() {
        return snippet;
    }

    public Id getId() {
        return id;
    }
}
