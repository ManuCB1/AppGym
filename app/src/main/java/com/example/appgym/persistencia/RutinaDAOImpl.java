package com.example.appgym.persistencia;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.appgym.utils.SessionManager;
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
    private SessionManager sessionManager;
    private String username;

    public RutinaDAOImpl(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
        sessionManager = new SessionManager(context);
        username = sessionManager.getUsername();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
    }
    @Override
    public void getAll(TaskCompleted<List<Rutina>> listener){
        progressDialog.show();
        String url = Constantes.URL_GET_RUTINAS +"?username=" + sessionManager.getUsername();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onTaskCompleted(parseJsonToRoutine(response));
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
        String url = Constantes.URL_CREATE_RUTINA;
        try {
            JSONObject rutinaJson = new JSONObject();
            rutinaJson.put("nombre", rutina.getNombre());
            rutinaJson.put("username",  username);
            rutinaJson.put("dia", rutina.getDay());
            JSONArray ejerciciosArray = new JSONArray();
            for (Ejercicio ejercicio: rutina.getEjercicios()){
                JSONObject ejerciciosJson = new JSONObject();
                ejerciciosJson.put("nombre", ejercicio.getNombre());
                ejerciciosJson.put("series", ejercicio.getSeries());
                ejerciciosJson.put("repeticiones", ejercicio.getRepeticiones());
                ejerciciosJson.put("imagen", ejercicio.getImagen());
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
                            parseJsonToRoutine(response);
                            progressDialog.dismiss();
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
    public void createHistorial(Rutina rutina) {
        progressDialog.show();
        String url = Constantes.URL_CREATE_HISTORIAL;
        try {
            JSONObject rutinaJson = new JSONObject();
            rutinaJson.put("id_rutina", rutina.getId());
            rutinaJson.put("username",  username);
            rutinaJson.put("nombre_rutina",  rutina.getNombre());
            JSONArray ejerciciosArray = new JSONArray();
            for (Ejercicio ejercicio: rutina.getEjercicios()){
                JSONObject ejerciciosJson = new JSONObject();
                ejerciciosJson.put("nombre_ejercicio", ejercicio.getNombre());
                ejerciciosJson.put("num_series", ejercicio.getSeries());
                ejerciciosJson.put("repeticiones", ejercicio.getRepeticiones());
                ejerciciosJson.put("peso", ejercicio.getPeso());
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
                            parseJsonToHistorial(response);
                            progressDialog.dismiss();
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
        String url = Constantes.URL_GET_RUTINAS_TODAY +"?dia=" + day +
                                                    "&username=" + sessionManager.getUsername();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onTaskCompleted(parseJsonToRoutine(response));
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
    public void getHistorialByDate(String fechaActual, TaskCompleted<List<Rutina>> listener) {
        progressDialog.show();
        String url = Constantes.URL_GET_HISTORIAL_TODAY +"?fecha=" + fechaActual +
                                                    "&username=" + sessionManager.getUsername();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onTaskCompleted(parseJsonToHistorial(response));
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
    public void getHistorialByRutina(int id, TaskCompleted<List<Rutina>> listener) {
        progressDialog.show();
        String url = Constantes.URL_GET_HISTORIAL_BY_RUTINA +"?id_rutina=" + id +
                "&username=" + sessionManager.getUsername();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onTaskCompleted(parseJsonToHistorial(response));
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
        String url = Constantes.URL_PUT_DAY_ROUTINE;
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
                            parseJsonToRoutine(response);
                            progressDialog.dismiss();
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
    public void delete(int id) {
        progressDialog.show();
        String url = Constantes.URL_DELETE_ROUTINE + "?id_rutina=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonToRoutine(response);
                        progressDialog.dismiss();
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
    }

    @Override
    public void deleteHistorial(String fecha) {
        progressDialog.show();
        String url = Constantes.URL_DELETE_HISTORIAL + "?fecha=" + fecha + "&username=" + sessionManager.getUsername();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonToHistorial(response);
                        progressDialog.dismiss();
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
    }


    private List<Rutina> parseJsonToRoutine(JSONObject response) {
        List<Rutina> rutinasAll = new ArrayList<>();
        if (response != null) {
            try {
                String success = response.getString("success");
                String message = response.getString("message");

                if (response.has("rutinas") && success.equals("true")) {
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
                            String nombreEjercicio = ejercicioActual.getString("nombre_ejercicio");
                            int series = ejercicioActual.getInt("series");
                            String repeticiones = ejercicioActual.getString("repeticiones");
                            String imagen = ejercicioActual.getString("imagen");
                            if (!ejercicioActual.has("imagen") || imagen == null){
                                imagen = "";
                            }
                            ejerciciosRutina.add(new Ejercicio(nombreEjercicio, series, repeticiones, imagen));
                        }
                        rutinasAll.add(new Rutina(id, nombre_rutina, ejerciciosRutina, dia));
                    }
                }
                else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rutinasAll;
    }
    private List<Rutina> parseJsonToHistorial(JSONObject response) {
        List<Rutina> rutinasAll = new ArrayList<>();
        if (response != null) {
            try {
                String success = response.getString("success");
                String message = response.getString("message");

                if (response.has("historial_rutinas") && success.equals("true")) {
                    JSONArray rutinasHistorial = response.getJSONArray("historial_rutinas");
                    if (rutinasHistorial.length() > 0){
                        for (int i = 0; i < rutinasHistorial.length(); i++) {
                            List<Ejercicio> ejerciciosRutina = new ArrayList<>();
                            JSONObject rutinaActual = rutinasHistorial.getJSONObject(i);
                            String fecha = rutinaActual.getString("fecha");
                            int id = rutinaActual.getInt("id_historial");
                            String nombre_rutina = rutinaActual.getString("nombre_rutina");
                            JSONArray ejercicios = rutinaActual.getJSONArray("ejercicios");
                            for (int j = 0; j < ejercicios.length(); j++) {
                                JSONObject ejercicioActual = ejercicios.getJSONObject(j);
                                String nombreEjercicio = ejercicioActual.getString("nombre_ejercicio");
                                int series = ejercicioActual.getInt("series");
                                String repeticiones = ejercicioActual.getString("repeticiones");

                                Ejercicio ejercicio = new Ejercicio(nombreEjercicio, series, repeticiones, "");
                                String peso = ejercicioActual.getString("peso");
                                ejercicio.setPeso(peso);

                                ejerciciosRutina.add(ejercicio);
                            }
                            rutinasAll.add(new Rutina(id, nombre_rutina, ejerciciosRutina, fecha));
                        }
                    }
                }
                else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rutinasAll;
    }
}
