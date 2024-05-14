package com.example.appgym.model;

public enum DayWeek {
    Lunes("Lunes"),
    Martes("Martes"),
    Miercoles("Miércoles"),
    Jueves("Jueves"),
    Viernes("Viernes"),
    Sabado("Sábado"),
    Domingo("Domingo");

    private String nombre;

    DayWeek(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
