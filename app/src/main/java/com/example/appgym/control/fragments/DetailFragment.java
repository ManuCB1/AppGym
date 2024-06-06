package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerDetailAdapter;
import com.example.appgym.adapter.SpinnerAdapter;
import com.example.appgym.model.Days;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.TypeAdapter;
import com.example.appgym.repository.RutinaRepositoryImpl;
import com.example.appgym.utils.Constantes;

import java.util.List;

public class DetailFragment extends BaseFragment {

    private RutinaRepositoryImpl rutinaRepository;
    private Rutina rutina;
    private RecyclerView recycler;
    private TextView titleRoutine;
    private Spinner spinner;
    private boolean isSpinnerInitialized = false;
    private int title = R.string.title_detail_routine;
    private int menu = 0;
    private static final String argParam1 = Constantes.ARG_RUTINA;

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rutina = (Rutina) getArguments().getSerializable(argParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleRoutine = view.findViewById(R.id.titleRoutine);
        recycler = view.findViewById(R.id.recycler);
        spinner = view.findViewById(R.id.spinner);

        setMenu(getString(title), menu);
        setData();
    }

    private void setData() {
        titleRoutine.setText(rutina.getNombre());

//        Spinner
        List<String> days = Days.getAll();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(requireContext(), R.layout.spinner_item, days);
        spinner.setAdapter(spinnerAdapter);
//        Seleccionar el día de la rutina
        int positionDefault = days.indexOf(rutina.getDate());
        if (positionDefault != -1){
            spinner.setSelection(positionDefault);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isSpinnerInitialized){
                    rutinaRepository = new RutinaRepositoryImpl(requireContext());
                    String day = (String) adapterView.getItemAtPosition(i);
                    rutinaRepository.putDay(day, rutina.getId());
                }else {
                    isSpinnerInitialized = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        Recycler
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        RecyclerDetailAdapter adapter = new RecyclerDetailAdapter(rutina.getEjercicios(), TypeAdapter.Info);
        recycler.setAdapter(adapter);
    }

}