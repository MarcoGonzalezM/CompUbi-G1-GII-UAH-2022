package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MisNotificaciones extends AppCompatActivity {

    int idCliente, idNotificacion;
    private Button button1, button2;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_notificaciones);

        button1 = (Button) findViewById(R.id.button_ver_detalle);
        button2 = (Button) findViewById(R.id.button_volver);
        spinner = (Spinner) findViewById(R.id.spinner);

        idCliente = getIntent().getIntExtra("idCliente",0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Código del botón
                int idNotificacion = 0;
                Intent i = new Intent(MisNotificaciones.this, Autentificacion.class);
                i.putExtra("idCliente", idCliente);
                i.putExtra("idNotificacion", idNotificacion);
                startActivity(i);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Código del botón
                Intent i = new Intent(MisNotificaciones.this, MenuPrincipalCliente.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
