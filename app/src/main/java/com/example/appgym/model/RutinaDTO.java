package com.example.appgym.model;

import java.io.Serializable;
import java.util.List;

public class RutinaDTO implements Serializable {

    private String nombre;
    private List<Ejercicio> ejercicios;
    private String day;

    public RutinaDTO(String nombre, List<Ejercicio> ejercicios, String day) {
        this.nombre = nombre;
        this.ejercicios = ejercicios;
        this.day = day;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}