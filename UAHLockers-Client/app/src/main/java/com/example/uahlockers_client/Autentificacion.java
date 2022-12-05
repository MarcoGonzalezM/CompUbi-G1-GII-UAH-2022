package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Autentificacion extends AppCompatActivity {

    int idCliente,idNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autentificacion);

        Button buttonY = (Button) findViewById(R.id.buttonY);
        Button buttonN = (Button) findViewById(R.id.buttonN);

        idCliente = getIntent().getIntExtra("idCliente",0);
        idNotificacion = getIntent().getIntExtra("idNotificacion",0);

        buttonY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Código del botón
                Intent i = new Intent(Autentificacion.this, MenuPrincipalCliente.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        buttonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Código del botón
                Intent i = new Intent(Autentificacion.this, MenuPrincipalCliente.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });
    }
}