package com.example.uahlockers_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DepositarPaquete extends AppCompatActivity {
    private Button button1, button2, button3;
    private EditText textIdPaq;
    private TextView textErrMess, textTaquillero;
    private Spinner spinner;
    private int idRepartidor, idPaq, idTaquilla, idTaquillero, resultado;
    private final Context context = new Context();
    private ArrayList<Integer> listIdsTaquillas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depositar_paquete);

        button1 = (Button) findViewById(R.id.button_descartar);
        button2 = (Button) findViewById(R.id.button_finalizar);
        button3 = (Button) findViewById(R.id.button_taquillero);
        textIdPaq = (EditText) findViewById(R.id.textIdPaq);
        textTaquillero = (TextView) findViewById(R.id.taquillero);
        textErrMess = (TextView) findViewById(R.id.labelErrMess);
        spinner = (Spinner) findViewById(R.id.spinner);

        idRepartidor = getIntent().getIntExtra("idRepartidor",0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarPaq(Integer.valueOf(textIdPaq.getText().toString()));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DepositarPaquete.this, MenuPrincipalRepartidor.class);
                i.putExtra("idRepartidor", idRepartidor);
                startActivity(i);
                finish();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTaquillero();
                loadTaquillas();
            }
        });
        //Código del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTaquilla = listIdsTaquillas.get(i);
                if (listIdsTaquillas.size()==0){
                    idTaquilla=0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void registrarPaq(int idPaq){
        this.idPaq = idPaq;
        sendPaq();

        switch(resultado){
            case -1:{
                textErrMess.setText("Error: paquete desconocido");
                break;
            }
            case -2:{
                textErrMess.setText("Error: taquilla ocupada");
                break;
            }
            default: {
                Intent i = new Intent(DepositarPaquete.this, MenuPrincipalRepartidor.class);
                i.putExtra("idRepartidor", idRepartidor);
                startActivity(i);
                finish();
            }
        }
    }
    public int getIdPaq(){
        return this.idPaq;
    }

    public int getIdTaquilla(){
        return this.idTaquilla;
    }

    public void setResultado(int resultado) { this.resultado = resultado;}

    private void sendPaq(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/EntregarPaquete";
        DepositarPaqueteServerConnectionThread thread = new DepositarPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setListTaquillas(JSONArray jsonTaquillas){
        try {
            for (int i=0; i < jsonTaquillas.length(); i++){
                JSONObject jsonObject = jsonTaquillas.getJSONObject(i);
                listIdsTaquillas.add(jsonObject.getInt("id_taquilla"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinner.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, listIdsTaquillas));
    }

    private void loadTaquillas(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/GetTaquillasFromTaquillero";
        DepositarPaqueteServerConnectionThread thread = new DepositarPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getTaquillero(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/GetTaquillero";
        DepositarPaqueteServerConnectionThread thread = new DepositarPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch(resultado){
            case -1:{
                textErrMess.setText("Error: paquete desconocido");
                break;
            }
            case -2:{
                textErrMess.setText("Error: taquilla ocupada");
                break;
            }
            default: {
                Intent i = new Intent(DepositarPaquete.this, MenuPrincipalRepartidor.class);
                i.putExtra("idRepartidor", idRepartidor);
                startActivity(i);
                finish();
            }
        }
    }
}
