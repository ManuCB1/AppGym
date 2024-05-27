package com.example.appgym.repository;

import android.content.Context;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.persistencia.RutinaDAO;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RutinaRepositoryImpl implements RutinaRepositoryBO{
    private Context context;
    private RutinaDAO rutinaDAO;

    public RutinaRepositoryImpl(Context context) {
        this.context = context;
        rutinaDAO = new RutinaDAO(context);
    }

//    @Override
//    public void getRutinas(TaskCompleted<List<Rutina>> listener) throws UnsupportedEncodingException {
//        List<Rutina> rutinas = new ArrayList<>();
//        String enlace = Constantes.url_getRutinas;
//        String id_usuario = "1";
//        String datos = "id_usuario=" + URLEncoder.encode(id_usuario, "UTF-8");
//        this.baseBBDD = new BaseBBDD(context, new TaskCompleted<String>() {
//            @Override
//            public void onTaskCompleted(String s) {
//                if (s!=null) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(s);
//                        String success = jsonObject.getString("success");
//                        String message = jsonObject.getString("message");
//
//                        List<Rutina> rutinasAll = null;
//                        if (success.equals("true")) {
//                            JSONArray rutinas = jsonObject.getJSONArray("rutinas");
//                            rutinasAll = new ArrayList<>();
//                            for (int i = 0; i < rutinas.length(); i++) {
//                                List<Ejercicio> ejerciciosRutina = new ArrayList<>();
//                                JSONObject rutinaActual = rutinas.getJSONObject(i);
////                        int id = rutinas.getInt("id");
//                                String nombre_rutina = rutinaActual.getString("nombre_rutina");
//                                String dia = rutinaActual.getString("dia");
//
//                                JSONArray ejercicios = rutinaActual.getJSONArray("ejercicios");
//                                for (int j = 0; j < ejercicios.length(); j++) {
//                                    JSONObject ejercicioActual = ejercicios.getJSONObject(j);
////                            int id = ejercicioActual.getInt("id_ejercicio");
//                                    String nombreEjercicio = ejercicioActual.getString("nombre_ejercicio");
//                                    int series = ejercicioActual.getInt("series");
//                                    String repeticiones = ejercicioActual.getString("repeticiones");
//
//                                    ejerciciosRutina.add(new Ejercicio(nombreEjercicio, series, repeticiones));
//                                }
//                                rutinasAll.add(new Rutina(nombre_rutina, ejerciciosRutina, dia));
//                            }
//                        }
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                        rutinas.addAll(rutinasAll);
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                };
//                listener.onTaskCompleted(rutinas);
//            }
//        });
//        baseBBDD.execute(enlace, datos);
//
//    }

    @Override
    public void getRutinas(TaskCompleted<List<Rutina>> listener) throws UnsupportedEncodingException {
        rutinaDAO.getAll(listener);
    }

    @Override
    public void createRutina(Rutina rutina) throws UnsupportedEncodingException {
        rutinaDAO.send(rutina);
    }

    @Override
    public Rutina getRutinaByName(String name) {
        return null;
    }

    @Override
    public void getRutinasByDay(String day, TaskCompleted<List<Rutina>> listener) {
        rutinaDAO.getByDay(day, listener);
    }

    @Override
    public void deleteRutina(String name) {
    }
}
