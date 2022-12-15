package com.example.uahlockers_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;

public class IniciarSesionRepartidor extends AppCompatActivity{
    private Button button1;
    private EditText textUName, textPwd;
    private TextView textErrMess;
    private String uname, hashPwd;
    private int resultado, idRepartidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_repartidor);

        button1 = (Button) findViewById(R.id.button_ini_ses);
        textUName = (EditText) findViewById(R.id.textUName);
        textPwd = (EditText) findViewById(R.id.TextPwd);
        textErrMess = (TextView) findViewById(R.id.labelErrMess);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSes(textUName.getText().toString(),textPwd.getText().toString());
            }
        });
    }

    public void iniciarSes(String uname, String pwd){
        idRepartidor=0;
        byte [] pwdBytes= pwd.getBytes(StandardCharsets.UTF_8);
        String hashPwd = "";
        for (int i=0;i<pwdBytes.length;i++){
            hashPwd += (char) ~pwdBytes[i];
        }
        this.uname = uname;
        // TODO: hash
        // this.hashPwd = hashPwd;
        this.hashPwd = pwd;
        sendLogIn();

        switch(resultado){
            case -1:{
                textErrMess.setText("Error: el nombre de usuario no está registrado");
                break;
            }
            case -2:{
                textErrMess.setText("Error: contraseña incorrecta");
                break;
            }
            case 0:{
                textErrMess.setText("Error: no se pudo conectar al servidor");
                break;
            }
            default: {
                Intent i = new Intent(IniciarSesionRepartidor.this, MenuPrincipalRepartidor.class);
                i.putExtra("idRepartidor", idRepartidor);
                startActivity(i);
                finish();
            }
        }
    }
    public String getUName(){
        return this.uname;
    }

    public String getPwd(){
        return this.hashPwd;
    }

    public void setResultado(int resultado){
        this.resultado = resultado;
    }

    private void sendLogIn(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/iniciarSesionRepartidor";
        IniciarSesionServerConnectionThread thread = new IniciarSesionServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setRepartidor(int repartidor) { this.idRepartidor = repartidor;}

}