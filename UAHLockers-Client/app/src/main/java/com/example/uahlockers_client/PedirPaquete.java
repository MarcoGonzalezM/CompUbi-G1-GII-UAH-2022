package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class PedirPaquete extends AppCompatActivity {

    private int idCliente, idTaq, resultado;
    private Button button1, button2;
    private Spinner spinner;
    private TextView textErrMess;
    private ArrayList<Integer> listIdsTaquilleros;

    public PedirPaquete() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_paquete);
        
        button1 = (Button) findViewById(R.id.button_paq_prueba);
        button2 = (Button) findViewById(R.id.button_volver);
        spinner = (Spinner) findViewById(R.id.spinner);
        textErrMess = (TextView) findViewById(R.id.labelErrMess);

        listIdsTaquilleros = new ArrayList<>();

        idCliente = getIntent().getIntExtra("idCliente",0);
        System.out.println(idCliente);
        //Código del botón
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirPaq();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PedirPaquete.this, MenuPrincipalCliente.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        //Código del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTaq = listIdsTaquilleros.get(i);
                if (listIdsTaquilleros.size()==0){
                    idTaq=0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        loadTaquilleros();
    }

    public int getIdCliente() { return this.idCliente; }

    public int getIdTaq() { return this.idTaq; }

    public void setResultado(int resultado){ this.resultado = resultado;}

    public void setListTaquilleros(JSONArray jsonTaquilleros){
        try {
            for (int i=0; i < jsonTaquilleros.length(); i++){
                JSONObject jsonObject = jsonTaquilleros.getJSONObject(i);
                listIdsTaquilleros.add(jsonObject.getInt("id_taquillero"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinner.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, listIdsTaquilleros));
    }

    private void pedirPaq(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/pedirPaquetePrueba";
        if (idTaq==0){
            textErrMess.setText("Error: no se ha seleccionado ningún taquillero");
            return;
        }
        PedirPaqueteServerConnectionThread thread = new PedirPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch(resultado){
            case -1:{
                textErrMess.setText("Error: hubo un problema al pedir el paquete");
                break;
            }
            case 1:{
                textErrMess.setText("Solicitud de paquete realizada con éxito");
                break;
            }
            case 0:{
                textErrMess.setText("Error: no se pudo conectar al servidor");
                break;
            }
        }
    }

    private void loadTaquilleros(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/getTaquilleros";
        PedirPaqueteServerConnectionThread thread = new PedirPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
