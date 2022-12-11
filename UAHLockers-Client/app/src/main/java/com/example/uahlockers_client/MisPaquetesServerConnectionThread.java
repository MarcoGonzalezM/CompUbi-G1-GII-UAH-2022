package com.example.uahlockers_client;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MisPaquetesServerConnectionThread extends ServerConnectionThread{
    private MisPaquetes activity;
    private String urlStr = "";
    private int commId;

    public MisPaquetesServerConnectionThread(MisPaquetes p_activity, String p_url) {
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/getPaquetes")) {
            commId = 1;
        } else commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                loadPaquetes();
            }
            default:{

            }
        }
    }

    private void loadPaquetes(){
        String response = "";
        try{
            int idCliente = activity.getIdCliente();
            urlStr = urlStr + "?id_cliente="+idCliente;
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            JSONArray listPaquetes = new JSONArray(response);
            activity.setListPaquetes(listPaquetes);
        } catch (IOException| JSONException e) {
            e.printStackTrace();
        }
    }

}
