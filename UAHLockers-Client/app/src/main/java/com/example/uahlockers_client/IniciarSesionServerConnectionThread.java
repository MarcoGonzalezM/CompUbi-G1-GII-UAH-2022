package com.example.uahlockers_client;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IniciarSesionServerConnectionThread extends ServerConnectionThread{
    private IniciarSesionCliente activity;
    private String urlStr = "";
    private int commId;

    public IniciarSesionServerConnectionThread(IniciarSesionCliente p_activity, String p_url){
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/iniciarSesion")){
            commId = 1;
        } else commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                readResponse();
            } default: {

            }
        }
    }

    private void sendLogIn(){
        try {
            String uname = activity.getUName();
            String pwd = activity.getPwd();
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            /*OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(uname.getBytes(StandardCharsets.UTF_8));
            out.write(pwd.getBytes(StandardCharsets.UTF_8));*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readResponse(){
        String response = "";
        int resultado = 0;
        try{
            String uname = activity.getUName();
            String pwd = activity.getPwd();
            urlStr = urlStr + "?nombre="+uname+"&password="+pwd;
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);

            resultado = (int) Integer.valueOf(response.substring(0,response.length()-1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        activity.setResultado(resultado);
    }
}
