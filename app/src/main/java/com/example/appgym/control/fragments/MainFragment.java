package com.example.appgym.control.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerInfoRoutineAdapter;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.repository.RutinaRepositoryImpl;
import com.example.appgym.utils.Constantes;
import com.example.appgym.utils.DateConverter;
import com.example.appgym.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainFragment extends BaseFragment {

    private TextView textDate, textDay, textRutina, textHistorial;
    private RutinaRepositoryImpl rutinaRepository;
    private RecyclerView recycler;
    private RecyclerView recyclerHistorial;
    private RecyclerInfoRoutineAdapter adapter;
    private RecyclerInfoRoutineAdapter adapterHistorial;
    private List<Rutina> rutinas;
    private List<Rutina> historiales;
    private static final String argParam1 = Constantes.ARG_RUTINA;
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
        textRutina = view.findViewById(R.id.textRutina);
        textHistorial = view.findViewById(R.id.textHistorial);
        recycler = view.findViewById(R.id.recycler);
        recyclerHistorial = view.findViewById(R.id.recyclerHistorial);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerHistorial.setLayoutManager(new LinearLayoutManager(requireContext()));

        textDate.setOnClickListener(v -> showCalendar(requireContext()));

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
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                Date fechaSeleccionada = calendar.getTime();
                String fechaActual = String.format("%04d-%02d-%02d", year, month+1, day);
                String today = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                setDateTexts(fechaSeleccionada);
                loadRecycler(today, fechaActual);
            }
        }, año, mes, dia);
        dialog.show();
    }

    private void initDate() {
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp);
        setDateTexts(date);

        loadRecycler(DateConverter.getDayOfWeek(), DateConverter.getDateToday());
    }

    private void loadRecycler(String day, String fechaActual) {
        rutinas = new ArrayList<>();
        historiales = new ArrayList<>();
        rutinaRepository = new RutinaRepositoryImpl(requireContext());
        rutinaRepository.getByDay(day, new TaskCompleted<List<Rutina>>() {
            @Override
            public void onTaskCompleted(List<Rutina> s) {
                setDataRutina(s);
            }
        });
        rutinaRepository.getHistorialByDate(fechaActual, new TaskCompleted<List<Rutina>>() {
            @Override
            public void onTaskCompleted(List<Rutina> s) {
                setDataHistorial(s);
            }
        });

    }

    private void menuSelected(int position, MenuItem item, boolean isHistorial) {
        if (item.getItemId() == R.id.titleInfo){
            if ((isHistorial)) {
                sendRoutine(historiales.get(position), R.id.action_mainFragment_to_infoHistorialFragment);
            } else {
                sendRoutine(rutinas.get(position), R.id.action_mainFragment_to_detailFragment);
            }
        }
        if (item.getItemId() == R.id.titleHistorial){
            sendRoutine(rutinas.get(position), R.id.action_mainFragment_to_historialFragment);
        }
        if (item.getItemId() == R.id.titleNewHistorial){
            sendRoutine(rutinas.get(position), R.id.action_mainFragment_to_newHistoryFragment);
        }
        if (item.getItemId() == R.id.titleRemove){
            String titleDialog = "Eliminar";
            String messageDialog = "¿Borrar Historial " + historiales.get(position).getNombre()+ "?";
            Utils.showDialogDelete(requireContext(), titleDialog, messageDialog,
                    (dialogInterface, i) -> {
                        rutinaRepository = new RutinaRepositoryImpl(requireContext());
                        rutinaRepository.deleteHistorial(historiales.get(position).getDate());
                        loadRecycler(DateConverter.getDayOfWeek(), DateConverter.getDateToday());
                    },null);
        }

    }

    private void setDateTexts(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatoDiaSemana = new SimpleDateFormat("EEEE", Locale.getDefault());

        String fechaFormateada = sdf.format(date);
        String diaSemana = formatoDiaSemana.format(date).toUpperCase();

        textDate.setText(DateConverter.getOrderDate(fechaFormateada));
        textDay.setText(diaSemana);
    }

    private void sendRoutine(Rutina rutina, int action) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(argParam1, rutina);
        Navigation.findNavController(requireView()).navigate(action, bundle);
    }

    private void setDataRutina(List<Rutina> s) {
        if ( s != null){
            rutinas.addAll(s);
            if (s.size()>0){
                textRutina.setVisibility(View.VISIBLE);
            }
            else {
                textRutina.setVisibility(View.INVISIBLE);
            }
        }
        adapter = new RecyclerInfoRoutineAdapter(rutinas, Constantes.MENU_ROUTINE,
                (int position, MenuItem item) -> {
                    menuSelected(position, item, false);
                });
        recycler.setAdapter(adapter);
    }

    private void setDataHistorial(List<Rutina> s) {
        if ( s != null){
            historiales.addAll(s);
            if (s.size()>0){
                textHistorial.setVisibility(View.VISIBLE);
            }
            else {
                textHistorial.setVisibility(View.INVISIBLE);
            }
        }
        adapterHistorial = new RecyclerInfoRoutineAdapter(historiales, Constantes.MENU_HISTORIAL,
                (int position, MenuItem item) -> {
                    menuSelected(position, item, true);
                });
        recyclerHistorial.setAdapter(adapterHistorial);
    }

}