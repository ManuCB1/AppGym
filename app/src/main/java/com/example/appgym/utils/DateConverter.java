package com.example.appgym.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    public static String getOrderDate(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatoSalida = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
        String fechaOrdenada = "";
        try {
            Date fechaDate = formatoEntrada.parse(fecha);
            fechaOrdenada = formatoSalida.format(fechaDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaOrdenada;
    }

    public static String getDateToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }

    public static String getOrderDateTime(String date) {
        String format = date;
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat formatoSalida = new SimpleDateFormat("d MMMM yyyy, HH:mm", new Locale("es", "ES"));

            format = formatoSalida.format(formatoEntrada.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

}