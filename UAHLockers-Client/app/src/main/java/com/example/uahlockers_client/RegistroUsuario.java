package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroUsuario extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        Button button1 = (Button) findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textUName = (EditText) findViewById(R.id.textUName);
                EditText textPwd = (EditText) findViewById(R.id.TextPwd);
                EditText textConfPwd = (EditText) findViewById(R.id.TextConfPwd);
                registrar(textUName.getText().toString(),textPwd.getText().toString(),textConfPwd.getText().toString());
                iniciarSes(textUName.getText().toString(),textPwd.getText().toString());
            }
        });
    }

    public void registrar(String uname, String pwd, String confPwd){
        if (pwd.equals(confPwd)){
            // TODO: Enviar datos al servidor

            // TODO: Recibir respuesta de validación del servidor
            finish();
        } else {
            // TODO: Mostrar error
        }
    }

    public void iniciarSes(String uname, String pwd){
        // TODO: Enviar datos al servidor

        // TODO: Recibir respuesta de validación del servidor

        Intent i = new Intent(RegistroUsuario.this, MenuPrincipalCliente.class);
        startActivity(i);
        finish();

    }
}
