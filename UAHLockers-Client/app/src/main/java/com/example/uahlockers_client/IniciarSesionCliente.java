package com.example.uahlockers_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IniciarSesionCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_cliente);

        Button button1 = (Button) findViewById(R.id.button_ini_ses);
        Button button2 = (Button) findViewById(R.id.button_reg);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textUName = (EditText) findViewById(R.id.textUName);
                EditText textPwd = (EditText) findViewById(R.id.TextPwd);
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
        // TODO: Enviar datos al servidor

        // TODO: Recibir respuesta de validación del servidor

        Intent i = new Intent(IniciarSesionCliente.this, MenuPrincipalCliente.class);
        startActivity(i);
        finish();

    }



}