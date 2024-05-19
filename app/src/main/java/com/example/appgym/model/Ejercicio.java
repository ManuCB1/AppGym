package com.example.appgym.model;

import java.util.List;

public class Ejercicio {

    private String nombre;
    private int series;
    private String repeticiones;
    private String imagen;

    public Ejercicio(String nombre, int series, String repeticiones) {
        this.nombre = nombre;
        this.series = series;
        this.repeticiones = repeticiones;
    }

    public Ejercicio() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
