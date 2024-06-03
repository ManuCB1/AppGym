package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerDetailAdapter;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.TypeAdapter;

public class DetailHistorialFragment extends BaseFragment {

    private int title = R.string.title_detail_historial;
    private int menu = 0;
    private static final String argParam1 = "rutina";
    private Rutina historial;
    private TextView titleHistorial;
    private RecyclerView recycler;

    public DetailHistorialFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            historial = (Rutina) getArguments().getSerializable(argParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_historial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleHistorial = view.findViewById(R.id.titleHistorial);
        recycler = view.findViewById(R.id.recycler);

//        Recycler
        setData();
        setMenu(getString(title), menu);
    }

    private void setData() {
        titleHistorial.setText(historial.getNombre());
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        RecyclerDetailAdapter adapter = new RecyclerDetailAdapter(historial.getEjercicios(), TypeAdapter.Historial);
        recycler.setAdapter(adapter);
    }
}