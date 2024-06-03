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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerDetailAdapter;
import com.example.appgym.adapter.SpinnerAdapter;
import com.example.appgym.model.Days;
import com.example.appgym.model.Rutina;
import com.example.appgym.repository.RutinaRepositoryImpl;

import java.util.List;

public class DetailFragment extends BaseFragment {

    private RutinaRepositoryImpl rutinaRepository;
    private Rutina rutina;
    private RecyclerView recycler;
    private TextView titleRoutine, titleDay;
    private ImageView image_info;
    private Spinner spinner;
    private boolean isSpinnerInitialized = false;
    private int title = R.string.title_detail_routine;
    private int menu = 0;
    private static final String argParam1 = "rutina";

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleRoutine = view.findViewById(R.id.titleRoutine);
//        titleDay = view.findViewById(R.id.titleDay);
        recycler = view.findViewById(R.id.recycler);
        spinner = view.findViewById(R.id.spinner);

        setData();
        setMenu(getString(title), menu);
    }

    private void setData() {
        titleRoutine.setText(rutina.getNombre());
//        titleDay.setText(rutina.getDay());

//        Spinner
        List<String> days = Days.getAll();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(requireContext(), R.layout.spinner_item, days);
        spinner.setAdapter(spinnerAdapter);
        //Seleccionar el día de la rutina
        int positionDefault = days.indexOf(rutina.getDay());
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
        RecyclerDetailAdapter adapter = new RecyclerDetailAdapter(rutina.getEjercicios(), false);
        recycler.setAdapter(adapter);
    }

}