package com.example.uahlockers_client;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PedirPaqueteServerConnectionThread extends ServerConnectionThread{
    private PedirPaquete activity;
    private String urlStr = "";
    private int commId;

    public PedirPaqueteServerConnectionThread(PedirPaquete p_activity, String p_url) {
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/pedirPaquetePrueba")) {
            commId = 1;
        } else if (urlStr.contains("/getTaquillero")) {
            commId = 2;
        } else commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                pedirPaq();
            }
            case (2):{
                loadTaquillero();
            }
            default:{

            }
        }
    }

    private void pedirPaq(){
        String response = "";
        int resultado = 0;
        try {
            int idTaq = activity.getIdTaq();
            int idCliente = activity.getIdCliente();
            urlStr = urlStr + "?taquillero="+idTaq+"&id_cliente="+idCliente;
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            resultado = Integer.valueOf(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        activity.setResultado(resultado);
    }

    private void loadTaquillero(){
        String response = "";
        try{
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            JSONArray listTaquilleros = new JSONArray(response);
            activity.setListTaquilleros(listTaquilleros);
        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }
    }
}
