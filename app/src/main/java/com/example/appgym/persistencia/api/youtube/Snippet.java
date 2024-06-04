package com.example.appgym.persistencia.api.youtube;

import com.google.gson.annotations.SerializedName;

public class Snippet {
    @SerializedName("title")
    private String title;

    @SerializedName("thumbnails")
    private Thumbnails thumbnails;

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnails != null && thumbnails.medium != null ? thumbnails.medium.url : null;
    }

    public class Thumbnails {
        @SerializedName("medium")
        private Thumbnail medium;

        public class Thumbnail {
            @SerializedName("url")
            private String url;
        }
    }
}
