package com.example.uahlockers_client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ElegirTaquilleroServerConnectionThread extends ServerConnectionThread{
    private ElegirTaquillero activity;
    private String urlStr = "";
    private int commId;

    public ElegirTaquilleroServerConnectionThread(ElegirTaquillero p_activity, String p_url) {
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/SelectTaquillero")) {
            commId = 1;
        } else if (urlStr.contains("/LoadTaquillero")) {
            commId = 2;
        } else commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                sendTaquillero();
            }
            case (2):{
                loadTaquillero();
            }
            default:{

            }
        }
    }

    private void sendTaquillero(){
        try {
            String id = ""; //activity.getId();
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(id.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTaquillero(){
        String response = "";
        int resultado = 0;
        try{
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            resultado = Integer.valueOf(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //activity.setResultado(resultado);
    }
}
