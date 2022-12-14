package com.example.uahlockers_client;

import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VistaPaqueteServerConnectionThread extends ServerConnectionThread{
    private VistaPaquete activity;
    private String urlStr = "";
    private int commId;

    public VistaPaqueteServerConnectionThread(VistaPaquete p_activity, String p_url) {
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/getDatosPedido")) {
            commId = 1;
        } else if (urlStr.contains("/getTaquilleros")) {
            commId = 2;
        } else if (urlStr.contains("/writeTaquillero")) {
            commId = 3;
        } else if (urlStr.contains("/getRecogidaNotificacionPedido")) {
            commId = 4;
        }else
            commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                getPaquete();
                break;
            }
            case (2):{
                loadTaquilleros();
                break;
            }
            case (3):{
                //writeTaquillero();
                break;
            }
            case (4):{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                    while(activity.hasWindowFocus()) {
                        getNotif();
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            default:{

            }
        }
    }

    private void getPaquete(){
        String response = "";
        try {
            int idPaquete = activity.getIdPaquete();
            urlStr = urlStr + "?id_pedido="+idPaquete;
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            JSONObject paquete = new JSONObject(response);
            activity.setPaquete(paquete);
        } catch (IOException| JSONException e) {
            e.printStackTrace();
        }
    }

    private void getNotif(){
        String response = "";
        int resultado = 0;
        try {
            int idPaquete = activity.getIdPaquete();
            urlStr = urlStr + "?id_pedido="+idPaquete;
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            resultado = Integer.valueOf(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        activity.setIdNotificacion(resultado);
    }

    private void loadTaquilleros(){
        String response = "";
        try{
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            JSONArray listTaquilleros = new JSONArray(response);
            activity.setListTaquilleros(listTaquilleros);
        } catch (IOException| JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeTaquillero(){
        String response = "";
        int resultado = 0;
        try{
            int idPaquete = activity.getIdPaquete();
            int idCliente = activity.getIdCliente();
            urlStr = urlStr + "?paquete="+idPaquete+"&id_cliente="+idCliente;
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
}
