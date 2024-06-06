package com.example.appgym.control.fragments;

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

import com.example.appgym.R;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.DayRecycler;
import com.example.appgym.adapter.RecyclerRoutineAdapter;
import com.example.appgym.model.Days;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.repository.RutinaRepositoryImpl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class RoutineFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<DayRecycler> dataRecycler;
    private List<Rutina> rutinas;
    private RecyclerRoutineAdapter adapter;
    private RutinaRepositoryImpl rutinaRepository;
    private int title = R.string.title_routine;
    private int menu = R.menu.menu_routine;

    public RoutineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_routine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView  = view.findViewById(R.id.recycler);

        setMenu(getString(title), menu);
        loadRutinas();
    }

    private void loadRutinas() {
        try {
            rutinaRepository = new RutinaRepositoryImpl(requireContext());
            rutinas = new ArrayList<>();
            rutinaRepository.getAll(new TaskCompleted<List<Rutina>>() {
                @Override
                public void onTaskCompleted(List<Rutina> s) {
                    rutinas.addAll(s);
                    loadRecycler();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void loadRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        dataRecycler = new ArrayList<>();
        List<String> days = Days.getAll();
        days.remove(Days.Seleccionar.getDay());
        List<Rutina> rutinasTemp;

        for (String day: days){
            rutinasTemp = new ArrayList<>();
            for (Rutina rutina: rutinas){
                if ( day.equals(rutina.getDate())){
                    rutinasTemp.add(rutina);
                }
            }
            dataRecycler.add(new DayRecycler(day, rutinasTemp));
        }

        adapter = new RecyclerRoutineAdapter(dataRecycler);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.itemInfo){
            Navigation.findNavController(requireView()).navigate(R.id.action_routineFragment_to_infoRoutineFragment);
        }
        if (item.getItemId()== R.id.itemNew){
            Navigation.findNavController(requireView()).navigate(R.id.action_routineFragment_to_newRoutineFragment);
        }
        return false;
    }

}