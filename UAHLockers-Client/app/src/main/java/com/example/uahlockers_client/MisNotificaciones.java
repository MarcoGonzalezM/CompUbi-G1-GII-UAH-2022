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

public class MisNotificaciones extends AppCompatActivity {

    int idCliente, idNotificacion;
    private Button button1, button2;
    private Spinner spinner;
    private TextView textErrMess;
    private final Context context = new Context();
    private ArrayList<Integer> listIdsNotifs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_notificaciones);

        button1 = (Button) findViewById(R.id.button_ver_detalle);
        button2 = (Button) findViewById(R.id.button_volver);
        spinner = (Spinner) findViewById(R.id.spinner);
            textErrMess = (TextView) findViewById(R.id.labelErrMess);

        listIdsNotifs = new ArrayList<>();

        idCliente = getIntent().getIntExtra("idCliente",0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idNotificacion==0){
                    textErrMess.setText("Error: no se ha seleccionado ninguna notificacion");
                    return;
                }
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
                Intent i = new Intent(MisNotificaciones.this, MenuPrincipalCliente.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idNotificacion = listIdsNotifs.get(i);
                if (listIdsNotifs.size()==0){
                    idNotificacion=0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        loadNotificaciones();
    }
    public int getIdCliente() { return this.idCliente; }

    public void setListNotifs(JSONArray jsonNotifs){
        try {
            for (int i=0; i < jsonNotifs.length(); i++){
                JSONObject jsonObject = jsonNotifs.getJSONObject(i);
                listIdsNotifs.add(jsonObject.getInt("id_recogida"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinner.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, listIdsNotifs));
    }

    private void loadNotificaciones(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/getRecogidaNotificaciones";
        MisNotificacionesServerConnectionThread thread = new MisNotificacionesServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
