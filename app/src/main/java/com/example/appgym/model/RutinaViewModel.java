package com.example.appgym.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appgym.persistencia.BaseBBDD;
import com.example.appgym.repository.RutinaRepositoryImpl;
import com.example.appgym.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RutinaViewModel extends ViewModel implements TaskCompleted<String>{
    private MutableLiveData<List<Rutina>> rutinas = new MutableLiveData<>();
    private WeakReference<Context> contextRef;
    private RutinaRepositoryImpl rutinaRepository;

    public RutinaViewModel() {
    }

    public RutinaViewModel(Context contextRef) {
        this.contextRef = new WeakReference<>(contextRef);
        this.rutinaRepository = new RutinaRepositoryImpl(this.contextRef.get());
    }

//    public LiveData<List<Rutina>> getRutinas() throws UnsupportedEncodingException {
//        return rutinaRepository.getRutinas();
//    }

    public void actualizarRutinas() throws UnsupportedEncodingException {
        BaseBBDD baseBBDD = new BaseBBDD(contextRef.get(), this);
        String enlace = Constantes.url_getRutinas;
        String id_usuario = "1";
        String datos = "id_usuario=" + URLEncoder.encode(id_usuario, "UTF-8");
        baseBBDD.execute(enlace, datos);
    }

    @Override
    public void onTaskCompleted(String s) {
        if (s!=null) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                List<Rutina> rutinasAll = null;
                if (success.equals("true")) {
                    JSONArray rutinas = jsonObject.getJSONArray("rutinas");
                    rutinasAll = new ArrayList<>();
                    for (int i = 0; i < rutinas.length(); i++) {
                        List<Ejercicio> ejerciciosRutina = new ArrayList<>();
                        JSONObject rutinaActual = rutinas.getJSONObject(i);
//                        int id = rutinas.getInt("id");
                        String nombre_rutina = rutinaActual.getString("nombre_rutina");
                        String dia = rutinaActual.getString("dia");

                        JSONArray ejercicios = rutinaActual.getJSONArray("ejercicios");
                        for (int j = 0; j < ejercicios.length(); j++) {
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
//                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                rutinas.setValue(rutinasAll);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
