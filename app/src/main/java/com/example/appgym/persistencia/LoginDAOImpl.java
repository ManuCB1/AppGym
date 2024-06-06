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
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.model.User;
import com.example.appgym.model.UserDTO;
import com.example.appgym.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginDAOImpl implements LoginDAO {
    private Context context;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    public LoginDAOImpl(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void get(String username, String password, TaskCompleted<User> listener) {
        progressDialog.show();
        String url = Constantes.URL_LOGIN;
        try {
            JSONObject userJson = new JSONObject();
            userJson.put("username", username);
            userJson.put("password", password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                userJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User user = parseJsonToUser(response);
                        listener.onTaskCompleted(user);
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
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(UserDTO user, TaskCompleted<Boolean> listener) {
        progressDialog.show();
        String url = Constantes.URL_SIGNUP;
        try {
            JSONObject userJson = new JSONObject();
            userJson.put("username", user.getUsername());
            userJson.put("password", user.getPassword());
            userJson.put("email", user.getEmail());
            userJson.put("height", user.getHeight());
            userJson.put("weight", user.getWeight());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    userJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                listener.onTaskCompleted(response.getBoolean("success"));
                                parseJsonToUser(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                progressDialog.dismiss();
                            }
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private User parseJsonToUser(JSONObject response) {
        User user = null;
        if (response != null) {
            try {
                String success = response.getString("success");
                String message = response.getString("message");
                if (response.has("user") && success.equals("true")) {
                    JSONObject userArray = response.getJSONObject("user");

                    String username = userArray.getString("username");
                    String email = userArray.getString("email");
                    int  height = userArray.getInt("height");
                    double weight = userArray.getDouble("weight");
                    user = new User(username, email, height, weight);
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
