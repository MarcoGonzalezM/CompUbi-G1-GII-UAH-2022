package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MisPaquetes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_paquetes);

        Button button1 = (Button) findViewById(R.id.button_ver_detalle);
        Button button2 = (Button) findViewById(R.id.button_volver);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Código del botón
                Intent i = new Intent(MisPaquetes.this, VistaPaquete.class);
                startActivity(i);
                finish();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Código del botón
                Intent i = new Intent(MisPaquetes.this, MenuPrincipalCliente.class);
                startActivity(i);
                finish();
            }
        });
    }
}
