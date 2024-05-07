package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appgym.R;
import com.example.appgym.model.Datos;
import com.example.appgym.model.recyclerAdapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RoutineFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Datos> datos;
    private MyRecyclerViewAdapter adapter;
    String[] days = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    public RoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadRecycler(view);
    }

    private void loadRecycler(@NonNull View view) {
        recyclerView  = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        datos = new ArrayList<>();
        List<String> ejemplo = Arrays.asList("Ejemplo", "Ejemplo 2");
        for (String day: days){
            datos.add(new Datos(day, ejemplo));
        }

        adapter = new MyRecyclerViewAdapter(datos);
        recyclerView.setAdapter(adapter);
    }
}