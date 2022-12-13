package com.example.uahlockers_delivery;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class IniciarSesionServerConnectionThread extends ServerConnectionThread{

    private IniciarSesionRepartidor activity;
    private String urlStr = "";
    private int commId;

    public IniciarSesionServerConnectionThread(IniciarSesionRepartidor p_activity, String p_url){
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/iniciarSesionRepartidor")){
            commId = 1;
        } else commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                sendLogin();
            } default: {

            }
        }
    }

    private void sendLogin(){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        activity.setResultado(resultado);
    }
}
