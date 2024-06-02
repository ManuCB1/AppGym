package com.example.appgym.model;

public class User {
    private String username;
    private String email;
    private int height;
    private double weight;

    public User(String username, String email, int height, double weight) {
        this.username = username;
        this.email = email;
        this.height = height;
        this.weight = weight;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
