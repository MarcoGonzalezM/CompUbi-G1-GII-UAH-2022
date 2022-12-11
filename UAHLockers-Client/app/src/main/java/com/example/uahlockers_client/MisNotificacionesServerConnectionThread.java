package com.example.uahlockers_client;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MisNotificacionesServerConnectionThread extends ServerConnectionThread {
    private MisNotificaciones activity;
    private String urlStr = "";
    private int commId;

    public MisNotificacionesServerConnectionThread(MisNotificaciones p_activity, String p_url) {
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/getNotificaciones")) {
            commId = 1;
        } else commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                loadNotificaciones();
            }
            default:{

            }
        }
    }

    private void loadNotificaciones(){
        String response = "";
        try{
            int idCliente = activity.getIdCliente();
            urlStr = urlStr + "?id_cliente="+idCliente;
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            JSONArray listNotifs = new JSONArray(response);
            activity.setListNotifs(listNotifs);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
