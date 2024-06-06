package com.example.appgym.persistencia.api.youtube;

public class YoutubeVideo {
    private String title;
    private String link;
    private String imageUrl; // Nueva propiedad para la URL de la imagen

    public YoutubeVideo(String title, String link, String imageUrl) {
        this.title = title;
        this.link = link;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

