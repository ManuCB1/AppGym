package com.example.appgym.model;

import java.util.ArrayList;
import java.util.List;

public enum Days {
    Seleccionar("Sin Asignar"),
    Lunes("lunes"),
    Martes("martes"),
    Miercoles("miercoles"),
    Jueves("jueves"),
    Viernes("viernes"),
    Sabado("sabado"),
    Domingo("domingo");

    private String day;

    Days(String day) {
        this.day = day;
    }

    public static String get(int position) {
        return values()[position].getDay();
    }

    public String getDay() {
        return day;
    }

    public static List<String> getAll() {
        List<String> days = new ArrayList<>();
        for (Days day: Days.values()){
            days.add(day.getDay());
        }
        return days;
    }
}
