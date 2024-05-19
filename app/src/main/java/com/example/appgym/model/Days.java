package com.example.appgym.model;

import java.util.ArrayList;
import java.util.List;

public enum Days {
    Lunes("Lunes"),
    Martes("Martes"),
    Miercoles("Miércoles"),
    Jueves("Jueves"),
    Viernes("Viernes"),
    Sabado("Sábado"),
    Domingo("Domingo");

    private String nombre;

    Days(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static List<String> getAll() {
        List<String> days = new ArrayList<>();
        for (Days day: Days.values()){
            days.add(day.getNombre());
        }
        return days;
    }
}
