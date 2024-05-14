package com.example.appgym.control.fragments;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.appgym.R;
import com.example.appgym.model.Ejercicio;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.DayRecycler;
import com.example.appgym.adapter.RecyclerRoutineAdapter;
import com.example.appgym.model.DayWeek;
import com.example.appgym.persistencia.BaseBBDD;
import com.example.appgym.persistencia.TaskCompleted;
import com.example.appgym.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RoutineFragment extends Fragment implements TaskCompleted {

    private RecyclerView recyclerView;
    private List<DayRecycler> datos;
    private RecyclerRoutineAdapter adapter;
    private BaseBBDD baseBBDD;

    List<String> days = new ArrayList<>();
    List<Rutina> rutinasAll = new ArrayList<>();

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

        loadDays();
        try {
            loadRutinas(view);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadRutinas(View view) throws UnsupportedEncodingException {
        String enlace = Constantes.url_getRutinas;
        String id_usuario = "1";
        String datos = "id_usuario=" + URLEncoder.encode(id_usuario, "UTF-8");
        baseBBDD = new BaseBBDD(view.getContext(), this);
        baseBBDD.execute(enlace, datos);
    }

    private void loadDays() {
        for (DayWeek dayWeek: DayWeek.values()){
            days.add(dayWeek.getNombre());
        }
    }

    private void loadRecycler(@NonNull View view) {
        recyclerView  = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        datos = new ArrayList<>();

        for (String day: days){
            List<Rutina> rutinasTemp = new ArrayList<>();
            for (Rutina rutina: rutinasAll){
                if ( day.equals(rutina.getDay())){
                    rutinasTemp.add(rutina);
                }
            }
            datos.add(new DayRecycler(day, rutinasTemp));
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

        adapter = new RecyclerRoutineAdapter(datos);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTaskCompleted(String s) {
        if (s!=null) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");
                if (success.equals("true")){

                    JSONArray rutinas = jsonObject.getJSONArray("rutinas");
                    for (int i = 0; i < rutinas.length(); i++) {
                        List<Ejercicio> ejerciciosRutina = new ArrayList<>();
                        JSONObject rutinaActual = rutinas.getJSONObject(i);
//                        int id = rutinas.getInt("id");
                        String nombre_rutina = rutinaActual.getString("nombre_rutina");
                        String dia = rutinaActual.getString("dia");

                        JSONArray ejercicios = rutinaActual.getJSONArray("ejercicios");
                        for (int j = 0; j < ejercicios.length(); j++){
                            JSONObject ejercicioActual = ejercicios.getJSONObject(j);
//                            int id = ejercicioActual.getInt("id_ejercicio");
                            String nombreEjercicio = ejercicioActual.getString("nombre_ejercicio");
                            int series = ejercicioActual.getInt("series");
                            String repeticiones = ejercicioActual.getString("repeticiones");

                            ejerciciosRutina.add(new Ejercicio(nombreEjercicio, series, repeticiones));
                        }
                        rutinasAll.add(new Rutina(nombre_rutina, ejerciciosRutina, dia));
                    }
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                loadRecycler(getView());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
    }
}