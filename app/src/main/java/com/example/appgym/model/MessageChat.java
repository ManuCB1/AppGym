package com.example.appgym.model;

public class MessageChat {
    private String message;
    private boolean isUSer;

    public MessageChat(String message, boolean isUSer) {
        this.message = message;
        this.isUSer = isUSer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUSer() {
        return isUSer;
    }

    public void setUSer(boolean USer) {
        isUSer = USer;
    }
}
