package com.example.appgym.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    public static String getOrderDate(String fecha){
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatoSalida = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
        String fechaOrdenada = "";
        try {
            Date fechaDate = formatoEntrada.parse(fecha);
            fechaOrdenada = formatoSalida.format(fechaDate);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaOrdenada;
    }

}
