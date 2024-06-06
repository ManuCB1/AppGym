package com.example.appgym.model;

import java.io.Serializable;
import java.util.List;

public class Rutina implements Serializable {

    private int id;
    private String nombre;
    private List<Ejercicio> ejercicios;
    private String date;

    public Rutina(int id, String nombre, List<Ejercicio> ejercicios, String date) {
        this.id = id;
        this.nombre = nombre;
        this.ejercicios = ejercicios;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}