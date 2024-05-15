package com.example.appgym.persistencia;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.appgym.model.TaskCompleted;
import com.example.appgym.utils.Constantes;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseBBDD extends AsyncTask<String, Object, String> {
    String linea = null;
    private HttpURLConnection clienteHttp = null;
    private Context context;
    private TaskCompleted listener;
    private ProgressDialog progressDialog;

    public BaseBBDD(Context context, TaskCompleted listener) {
        this.context = context;
        this.listener = listener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
//        datos que llegan
        String enlace = strings[0];
//        enlace
        String params = strings[1];
//        objeto que envia
        OutputStream salida = null;
//        codigo de llamada
        try {
            URL url = new URL(Constantes.url_base +enlace+".php");
//            establecer conexion
            clienteHttp = (HttpURLConnection) url.openConnection();
//            metodo POST
            clienteHttp.setDoOutput(true);
//            ponemos el tamaño
            clienteHttp.setFixedLengthStreamingMode(params.getBytes().length);
//            cifrado de los datos
            clienteHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            enviar los datos
            salida = new BufferedOutputStream(clienteHttp.getOutputStream());
            salida.write(params.getBytes());
            salida.flush();
            salida.close();

//            Respuesta PHP
            InputStream input = clienteHttp.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
            BufferedReader reader = new BufferedReader(inputReader);
            linea = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linea;
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        listener.onTaskCompleted(linea);
    }
}
