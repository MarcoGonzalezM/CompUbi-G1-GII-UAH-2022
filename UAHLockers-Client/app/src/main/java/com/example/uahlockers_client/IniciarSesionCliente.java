package com.example.uahlockers_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IniciarSesionCliente extends AppCompatActivity {

    private Button button1, button2;
    private EditText textUName, textPwd;
    private TextView textErrMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_cliente);

        button1 = (Button) findViewById(R.id.button_ini_ses);
        button2 = (Button) findViewById(R.id.button_reg);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textUName = (EditText) findViewById(R.id.textUName);
                textPwd = (EditText) findViewById(R.id.TextPwd);
                textErrMess = (TextView) findViewById(R.id.labelErrMess);
                iniciarSes(textUName.getText().toString(),textPwd.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IniciarSesionCliente.this, RegistroUsuario.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void iniciarSes(String uname, String pwd){
        int idCliente=0;
        // TODO: Enviar datos al servidor
        byte [] pwdBytes= pwd.getBytes(StandardCharsets.UTF_8);
        String hashPwd = "";
        for (int i=0;i<pwdBytes.length;i++){
            hashPwd += (char) ~pwdBytes[i];
        }
        sendDatos(uname,hashPwd);

        // TODO: Recibir respuesta de validación del servidor
        int resultado = readResponse();
        switch(resultado){
            case -1:{
                textErrMess.setText("Error: el nombre de usuario no está registrado");
                break;
            }
            case -2:{
                textErrMess.setText("Error: contraseña incorrecta");
                break;
            }
            case -3:{
                textErrMess.setText("Error: no se pudo conectar al servidor");
                break;
            }

            default: {
                Intent i = new Intent(IniciarSesionCliente.this, MenuPrincipalCliente.class);
                startActivity(i);
                i.putExtra("idCliente", idCliente);
                finish();
            }
        }
    }

    private void sendDatos(String uname, String pwd){
        String urlStr = "192.168.0.166:8080";
        try {
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

    private int readResponse(){
        String urlStr = "192.168.0.166:8080";
        String response;
        int resultado = -3;
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
        return resultado;
    }

    //Get the input strean and convert into String
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}