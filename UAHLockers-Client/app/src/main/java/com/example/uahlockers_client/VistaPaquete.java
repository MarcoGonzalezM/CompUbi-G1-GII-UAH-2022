package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class VistaPaquete extends AppCompatActivity {

    private int idCliente, idProducto;
    private Button button1, button2;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_paquete);

        button1 = (Button) findViewById(R.id.buttonAuth);
        button2 = (Button) findViewById(R.id.buttonBack);
        spinner = (Spinner) findViewById(R.id.spinner);

        idCliente = getIntent().getIntExtra("idCliente",0);
        idProducto = getIntent().getIntExtra("idProducto",0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Código del botón
                int idNotificacion = 0;
                Intent i = new Intent(VistaPaquete.this, Autentificacion.class);
                i.putExtra("idCliente", idCliente);
                i.putExtra("idNotificacion", idNotificacion);
                startActivity(i);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VistaPaquete.this, MisPaquetes.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });
    }
}
