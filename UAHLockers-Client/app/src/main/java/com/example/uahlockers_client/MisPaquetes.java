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

public class MisPaquetes extends AppCompatActivity {

    private int idCliente, idPaquete;
    private Button button1, button2;
    private Spinner spinner;
    private TextView textErrMess;
    private final Context context = new Context();
    private ArrayList<Integer> listIdsPaquetes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_paquetes);

        button1 = (Button) findViewById(R.id.button_ver_detalle);
        button2 = (Button) findViewById(R.id.button_volver);
        spinner = (Spinner) findViewById(R.id.spinner);
        textErrMess = (TextView) findViewById(R.id.labelErrMess);

        listIdsPaquetes = new ArrayList<>();

        idCliente = getIntent().getIntExtra("idCliente",0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idPaquete==0){
                    textErrMess.setText("Error: no se ha seleccionado ningún paquete");
                    return;
                }
                Intent i = new Intent(MisPaquetes.this, VistaPaquete.class);
                i.putExtra("idCliente", idCliente);
                i.putExtra("idPaquete", idPaquete);
                startActivity(i);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MisPaquetes.this, MenuPrincipalCliente.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        //Código del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idPaquete = listIdsPaquetes.get(i);
                if (listIdsPaquetes.size()==0){
                    idPaquete=0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        loadPaquetes();
    }
    public int getIdCliente() { return this.idCliente; }

    public void setListPaquetes(JSONArray jsonPaquetes){
        try {
            for (int i=0; i < jsonPaquetes.length(); i++){
                JSONObject jsonObject = jsonPaquetes.getJSONObject(i);
                listIdsPaquetes.add(jsonObject.getInt("id_pedido"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinner.setAdapter(new ArrayAdapter<Integer>(this.context, android.R.layout.simple_spinner_item, listIdsPaquetes));
    }

    private void loadPaquetes(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/getPaquetes";
        MisPaquetesServerConnectionThread thread = new MisPaquetesServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
