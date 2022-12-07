package com.example.uahlockers_client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegistrationServerConnectionThread extends ServerConnectionThread {
    private RegistroUsuario activity;
    private String urlStr = "";
    private int commId;

    public RegistrationServerConnectionThread(RegistroUsuario p_activity, String p_url) {
        activity = p_activity;
        urlStr = p_url;
        if (urlStr.contains("/LogIn")) {
            commId = 1;
        } else if (urlStr.contains("/LogResponse")) {
            commId = 2;
        } else if (urlStr.contains("/Registration")) {
            commId = 3;
        } else if (urlStr.contains("/RegisterResponse")) {
            commId = 4;
        } else commId = -1;
        start();
    }

    public void run(){
        switch(commId){
            case (1):{
                sendLogIn();
            }
            case (2):{
                readResponse();
            }
            case (3):{
                sendRegistration();
            }
            case (4):{
                readRegisterResponse();
            }
            default:{

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
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(uname.getBytes(StandardCharsets.UTF_8));
            out.write(pwd.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readResponse(){
        String response = "";
        int resultado = 0;
        try{
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            JSONObject jsonObject= new JSONObject(response);
            resultado = jsonObject.getInt("resultado");
        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }
        activity.setResultado(resultado);
    }

    private void sendRegistration(){
        try {
            String uname = activity.getUName();
            String pwd = activity.getPwd();
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(uname.getBytes(StandardCharsets.UTF_8));
            out.write(pwd.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRegisterResponse(){
        String response = "";
        int resultado = 0;
        try{
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            JSONObject jsonObject= new JSONObject(response);
            resultado = jsonObject.getInt("resultado");
        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }
        activity.setResultado(resultado);
    }



}
