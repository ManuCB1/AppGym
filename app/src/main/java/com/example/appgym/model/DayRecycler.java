package com.example.appgym.model;

import java.util.List;

public class DayRecycler {

    private String title;
    private List<Rutina> ejercicios;
    private boolean expandable;

    public DayRecycler(String title, List<Rutina> ejercicios) {
        this.title = title;
        this.ejercicios = ejercicios;
        this.expandable = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Rutina> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Rutina> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
