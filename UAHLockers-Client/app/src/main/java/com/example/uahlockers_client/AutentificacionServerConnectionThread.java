package com.example.uahlockers_client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AutentificacionServerConnectionThread extends ServerConnectionThread {
    private Autentificacion activity;
    private String urlStr = "";
    private int commId;

    public AutentificacionServerConnectionThread(Autentificacion p_activity, String p_url) {
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/abrirTaquilla")) {
            commId = 1;
        } else
            commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                autentificar();
            }
            default:{

            }
        }
    }

    private void autentificar(){
        String response = "";
        int resultado = 0;
        try {
            int idNotif = activity.getIdNotificacion();
            urlStr += "?id_recogida=" + idNotif + "&recogido=true";
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
