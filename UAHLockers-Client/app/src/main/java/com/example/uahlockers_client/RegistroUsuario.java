package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;

public class RegistroUsuario extends AppCompatActivity {

    private Button button1;
    private EditText textUName, textPwd, textConfPwd;
    private TextView textErrMess;
    private String uname, pwd, hashPwd;
    private int resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        button1 = (Button) findViewById(R.id.button);
        textUName = (EditText) findViewById(R.id.TextUName);
        textPwd = (EditText) findViewById(R.id.TextPwd);
        textConfPwd = (EditText) findViewById(R.id.TextConfPwd);
        textErrMess = (TextView) findViewById(R.id.labelErrMess);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(textUName.getText().toString(),textPwd.getText().toString(),textConfPwd.getText().toString());
            }
        });
    }

    public void registrar(String uname, String pwd, String confPwd){
        if (pwd.equals(confPwd)){
            this.uname = uname;
            this.pwd = pwd;
            sendRegistration();
            switch(resultado){
                case -1:{
                    textErrMess.setText("Error: el nombre de usuario no está disponible");
                    break;
                }
                case 0:{
                    textErrMess.setText("Error: no se pudo conectar al servidor");
                    break;
                }

                default: {
                    Intent i = new Intent(RegistroUsuario.this, MenuPrincipalCliente.class);
                    startActivity(i);
                    i.putExtra("idCliente", resultado);
                    finish();
                }
            }
        } else {
            textErrMess.setText("Error: las contraseñas no coinciden.");
        }
    }

    public void iniciarSes(String uname, String pwd){
        int idCliente=0;
        byte [] pwdBytes= pwd.getBytes(StandardCharsets.UTF_8);
        String hashPwd = "";
        for (int i=0;i<pwdBytes.length;i++){
            hashPwd += (char) ~pwdBytes[i];
        }

        this.uname = uname;
        //this.hashPwd = hashPwd;
        this.hashPwd = this.pwd;
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
                Intent i = new Intent(RegistroUsuario.this, MenuPrincipalCliente.class);
                startActivity(i);
                i.putExtra("idCliente", idCliente);
                finish();
            }
        }
    }

    public String getUName(){
        return this.uname;
    }

    public String getPwd(){
        return this.pwd;
    }

    public void setResultado(int resultado){
        this.resultado = resultado;
    }

    private void sendRegistration(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/registrarCliente";
        RegistrationServerConnectionThread thread = new RegistrationServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendLogIn(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/iniciarSesionCliente";
        RegistrationServerConnectionThread thread = new RegistrationServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
