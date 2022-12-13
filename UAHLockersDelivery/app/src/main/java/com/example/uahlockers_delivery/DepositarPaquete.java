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
    private Button button1, button2;
    private EditText textIdPaq;
    private TextView textErrMess;
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
        textIdPaq = (EditText) findViewById(R.id.textIdPaq);
        textErrMess = (TextView) findViewById(R.id.labelErrMess);
        spinner = (Spinner) findViewById(R.id.spinner);

        idRepartidor = getIntent().getIntExtra("idRepartidor",0);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarPaq(Integer.valueOf(textIdPaq.getText().toString()));
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DepositarPaquete.this, MenuPrincipalRepartidor.class);
                startActivity(i);
                i.putExtra("idRepartidor", idRepartidor);
                finish();
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
        loadTaquillas();
    }

    public void registrarPaq(int idPaq){
        int idRepartidor=0;
        this.idPaq = idPaq;
        sendPaq();

        int resultado = 0;
        switch(resultado){
            case -1:{
                //TODO: Mensajes
                textErrMess.setText("Error: el nombre de usuario no está registrado");
                break;
            }
            case -2:{
                textErrMess.setText("Error: contraseña incorrecta");
                break;
            }
            default: {
                Intent i = new Intent(DepositarPaquete.this, MenuPrincipalRepartidor.class);
                startActivity(i);
                i.putExtra("idRepartidor", idRepartidor);
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
        urlStr+="/uahlockers/depositarPaquete";
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
        spinner.setAdapter(new ArrayAdapter<Integer>(this.context, android.R.layout.simple_spinner_item, listIdsTaquillas));
    }

    private void loadTaquillas(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/getTaquillas";
        DepositarPaqueteServerConnectionThread thread = new DepositarPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
