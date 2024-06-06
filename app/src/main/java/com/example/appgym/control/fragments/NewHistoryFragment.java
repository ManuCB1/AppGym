package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerDetailAdapter;
import com.example.appgym.model.Ejercicio;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.TypeAdapter;
import com.example.appgym.repository.RutinaRepositoryImpl;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class NewHistoryFragment extends BaseFragment {

    private static final String argParam1 = "rutina";
    private int min = 1;
    private RutinaRepositoryImpl rutinaRepository;
    private RecyclerDetailAdapter adapter;
    private RecyclerView recycler;
    private TextView textRutina;
    private ImageView btnCreate;
    private Rutina rutina;
    private int title = R.string.title_new_history;
    private int menu = 0;
    public NewHistoryFragment() {
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
        return inflater.inflate(R.layout.fragment_new_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler);
        textRutina = view.findViewById(R.id.textRutina);
        btnCreate = view.findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(view1 -> {
                createHistorial();
        });

        setMenu(getString(title), menu);
        setData();
    }

    private void createHistorial(){
        try {
            List<Ejercicio> ejercicios = adapter.getEjercicios();
            for (Ejercicio ejercicio: ejercicios){
                if (ejercicio.getNombre().isEmpty() ||
                        ejercicio.getSeries() < min ||
                        ejercicio.getRepeticiones().isEmpty() ||
                        ejercicio.getPeso().isEmpty()){
                    Toast.makeText(requireContext(), "Error, campos vacíos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            rutina.setEjercicios(ejercicios);
            rutinaRepository = new RutinaRepositoryImpl(requireContext());
            rutinaRepository.createHistorial(rutina);
            requireActivity().onBackPressed();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error al guardar: " +
                    e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void setData() {
        textRutina.setText(rutina.getNombre());
        List<Ejercicio> ejercicios = rutina.getEjercicios();
        adapter = new RecyclerDetailAdapter(ejercicios, TypeAdapter.Editable, recycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);
    }
}