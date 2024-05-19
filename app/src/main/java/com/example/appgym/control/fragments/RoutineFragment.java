package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.appgym.R;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.DayRecycler;
import com.example.appgym.adapter.RecyclerRoutineAdapter;
import com.example.appgym.model.Days;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.service.RutinaServiceImpl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class RoutineFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<DayRecycler> dataRecycler;
    private RecyclerRoutineAdapter adapter;
    private ImageView newRoutine, infoRoutine;
    private RutinaServiceImpl rutinaService;

    private List<String> days;

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

        newRoutine = view.findViewById(R.id.newRoutine);
        infoRoutine = view.findViewById(R.id.infoRoutine);
        recyclerView  = view.findViewById(R.id.recycler);
        infoRoutine.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_routineFragment_to_infoRoutineFragment);
        });
        newRoutine.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_routineFragment_to_newRoutineFragment);
        });

        try {
            loadRutinas();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadRutinas() throws UnsupportedEncodingException {
        rutinaService = new RutinaServiceImpl(getContext());
        rutinaService.getRutinas(new TaskCompleted<List<Rutina>>() {
            @Override
            public void onTaskCompleted(List<Rutina> s) {
                loadRecycler(s);
            }
        });
    }

    private void loadRecycler(List<Rutina> rutinas) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataRecycler = new ArrayList<>();
        days = Days.getAll();

        for (String day: days){
            List<Rutina> rutinasTemp = new ArrayList<>();
            for (Rutina rutina: rutinas){
                if ( day.equals(rutina.getDay())){
                    rutinasTemp.add(rutina);
                }
            }
            dataRecycler.add(new DayRecycler(day, rutinasTemp));
        }

//        En streams:
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            days.forEach(day -> {
//                List<Rutina> rutinasTemp = new ArrayList<>();
//                rutinas.stream()
//                        .filter(rutina -> rutina.getDay().equals(day))
//                        .forEach(rutina -> rutinasTemp.add(rutina));
//                datos.add(new DayRecycler(day,rutinasTemp));
//            });
//        }

        adapter = new RecyclerRoutineAdapter(dataRecycler);
        recyclerView.setAdapter(adapter);
    }

}