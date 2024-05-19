package com.example.appgym.model;

import java.util.List;

public class DayRecycler {

    private String title;
    private List<Rutina> rutinas;
    private boolean expandable;

    public DayRecycler(String title, List<Rutina> rutinas) {
        this.title = title;
        this.rutinas = rutinas;
        this.expandable = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Rutina> getRutinas() {
        return rutinas;
    }

    public void setRutinas(List<Rutina> rutinas) {
        this.rutinas = rutinas;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
