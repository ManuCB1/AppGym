package com.example.appgym.control.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.appgym.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainFragment extends Fragment {

    private TextView textDate, textDay;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textDate = view.findViewById(R.id.textFecha);
        textDay = view.findViewById(R.id.textDay);

        initDate();
        textDate.setOnClickListener(view1 -> showCalendar(view1.getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        initDate();
    }

    public void showCalendar(Context context){
        final Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, month);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date fechaSeleccionada = calendario.getTime();
                setTexts(fechaSeleccionada);
//                mostrarTareas(fechaFormateada);
            }
        }, año, mes, dia);
        dialog.show();
    }

    private String getOrderDate(String fecha){
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

    private void initDate() {
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp);
        setTexts(date);
//        mostrarTareas(fechaActual);
    }


    private void setTexts(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatoDiaSemana = new SimpleDateFormat("EEEE", Locale.getDefault());

        String fechaFormateada = sdf.format(date);
        String diaSemana = formatoDiaSemana.format(date).toUpperCase();

        textDate.setText(getOrderDate(fechaFormateada));
        textDay.setText(diaSemana);
    }
}