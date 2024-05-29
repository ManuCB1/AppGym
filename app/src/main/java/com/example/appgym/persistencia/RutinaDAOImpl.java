package com.example.appgym.persistencia;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appgym.model.Ejercicio;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.RutinaDTO;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RutinaDAOImpl implements RutinaDAO {
    private Context context;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    public RutinaDAOImpl(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
    }
    @Override
    public void getAll(TaskCompleted<List<Rutina>> listener){
        progressDialog.show();
        String url = Constantes.url_getRutinas+"?id_usuario=" + 1;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Rutina> rutinasParse = parseJsonToRoutine(response);
                        listener.onTaskCompleted(rutinasParse);
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void create(RutinaDTO rutina){
        progressDialog.show();
        String url = Constantes.url_createRutina;
        try {
            JSONObject rutinaJson = new JSONObject();
            rutinaJson.put("nombre", rutina.getNombre());
            rutinaJson.put("id_usuario", 1);
            rutinaJson.put("dia", rutina.getDay());
            JSONArray ejerciciosArray = new JSONArray();
            for (Ejercicio ejercicio: rutina.getEjercicios()){
                JSONObject ejerciciosJson = new JSONObject();
                ejerciciosJson.put("nombre", ejercicio.getNombre());
                ejerciciosJson.put("series", ejercicio.getSeries());
                ejerciciosJson.put("repeticiones", ejercicio.getRepeticiones());
                ejerciciosArray.put(ejerciciosJson);
            }
            rutinaJson.put("ejercicios", ejerciciosArray);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    rutinaJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean sucess = response.getBoolean("success");
                                String message = response.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getByDay(String day, TaskCompleted<List<Rutina>> listener) {
        progressDialog.show();
        String url = Constantes.url_getRutinas_today+"?dia=" + day + "&id_usuario=" + 1;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Rutina> rutinasParse = parseJsonToRoutine(response);
                        listener.onTaskCompleted(rutinasParse);
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void putDay(String day, int id) {
        progressDialog.show();
        String url = Constantes.url_putDayRoutine;
        try {
            JSONObject params = new JSONObject();
            params.put("nuevo_dia", day);
            params.put("id_rutina", id);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean sucess = response.getBoolean("success");
                                String message = response.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
                            Log.i("error", error.getMessage());
                            progressDialog.dismiss();
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        progressDialog.show();
        String url = Constantes.url_deleteRoutine + "?id_rutina=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean sucess = response.getBoolean("success");
                            String message = response.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
                        Log.i("error", error.getMessage());
                        progressDialog.dismiss();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }


    private List<Rutina> parseJsonToRoutine(JSONObject response) {
        List<Rutina> rutinasAll = new ArrayList<>();
        if (response != null) {
            try {
                String success = response.getString("success");
                String message = response.getString("message");

                if (success.equals("true")) {
                    JSONArray rutinas = response.getJSONArray("rutinas");
                    for (int i = 0; i < rutinas.length(); i++) {
                        List<Ejercicio> ejerciciosRutina = new ArrayList<>();
                        JSONObject rutinaActual = rutinas.getJSONObject(i);
                        int id = rutinaActual.getInt("id");
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
                        rutinasAll.add(new Rutina(id, nombre_rutina, ejerciciosRutina, dia));
                    }
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return rutinasAll;
    }
}
