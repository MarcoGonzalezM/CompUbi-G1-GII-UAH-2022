package com.example.uahlockers_delivery;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DepositarPaqueteServerConnectionThread extends ServerConnectionThread {
    private DepositarPaquete activity;
    private String urlStr = "";
    private int commId;

    public DepositarPaqueteServerConnectionThread(DepositarPaquete p_activity, String p_url){
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/entregarPaquete")){
            commId = 1;
        } else if (urlStr.contains("/getTaquillasFromTaquillero")){
            commId = 2;
        } else if (urlStr.contains("/getTaquillero")){
            commId = 3;
        } else commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                depositarPaquete();
                break;
            } case (2):{
                loadTaquillas();
                break;
            } case (3):{
                getTaquillero();
                break;
            }default: {

            }
        }
    }

    private void depositarPaquete(){
        String response = "";
        int resultado = 0;
        try{
            int idTaquilla = activity.getIdTaquilla();
            int idPaquete = activity.getIdPaq();
            int idRepartidor = activity.getRepartidor();
            int idTaquillero = activity.getIdTaquillero();
            urlStr = urlStr +"?id_pedido="+idPaquete + "&id_taquilla="+idTaquilla+"&id_repartidor="+idRepartidor+"&id_taquillero="+idTaquillero;
            System.out.println(urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            resultado = (int) Integer.valueOf(response.substring(0,response.length()-1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        activity.setResultado(resultado);
    }

    private void loadTaquillas(){
        String response = "";
        try{
            int idTaquillero = activity.getIdTaquillero();
            urlStr = urlStr + "?id_taquillero="+idTaquillero;
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            JSONArray listTaquillas = new JSONArray(response);
            activity.setListTaquillas(listTaquillas);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void getTaquillero(){
        String response = "";
        int resultado = 0;
        try{
            int idPaquete = activity.getIdPaq();
            urlStr = urlStr +"?id_pedido="+idPaquete;
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            resultado = (int) Integer.valueOf(response.substring(0,response.length()-1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        activity.setIdTaquillero(resultado);
    }

}


