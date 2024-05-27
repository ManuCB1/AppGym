package com.example.appgym.control.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerInfoRoutineAdapter;
import com.example.appgym.model.Days;
import com.example.appgym.model.PopupListener;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.repository.RutinaRepositoryImpl;
import com.example.appgym.utils.Constantes;
import com.example.appgym.utils.DateConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainFragment extends BaseFragment implements PopupListener {

    private TextView textDate, textDay;
    private RutinaRepositoryImpl rutinaRepository;
    private RecyclerView recycler;
    private RecyclerInfoRoutineAdapter adapter;
    private List<Rutina> rutinas;
    private static final String argParam1 = "rutina";
    private int title = R.string.title_home;
    private int menu = 0;

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
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        textDate.setOnClickListener(v -> showCalendar(getContext()));

        setMenu(getString(title), menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        initDate();
    }

    public void showCalendar(Context context){
        final Calendar calendar = Calendar.getInstance();
        int año = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date fechaSeleccionada = calendar.getTime();
                String today = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                setTexts(fechaSeleccionada);
                loadRecycler(today);
            }
        }, año, mes, dia);
        dialog.show();
    }



    private void initDate() {
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        String today = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        setTexts(date);
        loadRecycler(today);
    }

    private void loadRecycler(String day) {
        rutinas = new ArrayList<>();
        rutinaRepository = new RutinaRepositoryImpl(requireContext());
        rutinaRepository.getRutinasByDay(day, new TaskCompleted<List<Rutina>>() {
            @Override
            public void onTaskCompleted(List<Rutina> s) {
                if ( s != null){
                    rutinas.addAll(s);
                }
                adapter = new RecyclerInfoRoutineAdapter(rutinas, Constantes.menuHistorial, MainFragment.this);
                recycler.setAdapter(adapter);
            }
        });

    }


    private void setTexts(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatoDiaSemana = new SimpleDateFormat("EEEE", Locale.getDefault());

        String fechaFormateada = sdf.format(date);
        String diaSemana = formatoDiaSemana.format(date).toUpperCase();

        textDate.setText(DateConverter.getOrderDate(fechaFormateada));
        textDay.setText(diaSemana);
    }

    @Override
    public void selectedItem(int position, MenuItem item) {
        if (item.getItemId() == R.id.titleInfo){
            sendRoutine(position);
        }
        if (item.getItemId() == R.id.titleHistorial){

        }
    }

    private void sendRoutine(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(argParam1, rutinas.get(position));
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_detailFragment, bundle);
    }
}