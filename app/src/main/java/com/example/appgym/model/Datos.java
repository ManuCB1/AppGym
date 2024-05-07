package com.example.appgym.model;

import java.util.List;

public class Datos {

    private String title;
    private List<String> datos;
    private boolean expandable;

    public Datos(String title, List<String> datos) {
        this.title = title;
        this.datos = datos;
        this.expandable = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDatos() {
        return datos;
    }

    public void setDatos(List<String> datos) {
        this.datos = datos;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
