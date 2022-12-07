package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ElegirTaquillero extends AppCompatActivity {

    private int idCliente;
    private Button button1, button2;
    private Spinner spinner;
    private ArrayList<String> listTaquilleros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_taquillero);

        button1 = (Button) findViewById(R.id.buttonAccept);
        button2 = (Button) findViewById(R.id.buttonBack);
        spinner = (Spinner) findViewById(R.id.spinner);

        listTaquilleros = new ArrayList<>();

        idCliente = getIntent().getIntExtra("idCliente",0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Código del botón
                Intent i = new Intent(ElegirTaquillero.this, MenuPrincipalCliente.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ElegirTaquillero.this, MenuPrincipalCliente.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        //TODO: Código del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String id = listTaquilleros.get(i);
                sendTaquillero();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        loadTaquillero();
    }

    private void sendTaquillero(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/Ubicua/SelectTaquillero";
        ElegirTaquilleroServerConnectionThread thread = new ElegirTaquilleroServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadTaquillero(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/Ubicua/LoadTaquillero";
        ElegirTaquilleroServerConnectionThread thread = new ElegirTaquilleroServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
