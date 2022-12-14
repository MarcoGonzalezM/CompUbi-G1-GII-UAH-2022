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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import database.Pedido;

public class VistaPaquete extends AppCompatActivity {

    private int idCliente, idPaquete, idTaq, idNotificacion, resultado;
    private Button button1, button2;
    private Spinner spinner;
    private TextView textErrMess, textProducto, textEstado, textCodigo;
    private final Context context = new Context();
    private Pedido paquete;
    private ArrayList<Integer> listIdsTaquilleros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_paquete);

        button1 = (Button) findViewById(R.id.buttonAuth);
        button2 = (Button) findViewById(R.id.buttonBack);
        spinner = (Spinner) findViewById(R.id.spinner);
        textProducto = (TextView) findViewById(R.id.labelProdName);
        textEstado = (TextView) findViewById(R.id.labelStatus);
        textCodigo = (TextView) findViewById(R.id.labelPIN);
        textErrMess = (TextView) findViewById(R.id.labelErrMess);

        listIdsTaquilleros = new ArrayList<>();

        idCliente = getIntent().getIntExtra("idCliente",0);
        idPaquete = getIntent().getIntExtra("idPaquete",0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (resultado){
                    case 0:{
                        textErrMess.setText("Error: No se puede conectar con el servidor.");
                    }
                    case -1:{
                        textErrMess.setText("Este paquete aún no está en un taquillero.\n" +
                                "No se puede autenticar todavía.");
                        break;
                    }
                    default:{
                        Intent i = new Intent(VistaPaquete.this, Autentificacion.class);
                        i.putExtra("idCliente", idCliente);
                        i.putExtra("idNotificacion", idNotificacion);
                        startActivity(i);
                        finish();
                    }
                }
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTaq = listIdsTaquilleros.get(i);
                if (listIdsTaquilleros.size()==0){
                    idTaq=0;
                }
                //writeTaquillero();
                switch (resultado){
                    case 1:{
                        textErrMess.setText("Guardado.");
                    }
                    default:{

                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        loadPaquete();
        loadTaquilleros();
    }

    public int getIdCliente() { return this.idCliente; }

    public int getIdPaquete() { return this.idPaquete; }

    public void setPaquete(JSONObject paquete){
        this.paquete = new Pedido();
        try {
            this.paquete.setId_pedido(paquete.getInt("id_pedido"));
            this.paquete.setTaquillero(paquete.getInt("taquillero"));
            this.paquete.setId_cliente(paquete.getInt("id_cliente"));
            this.paquete.setEstado_entrega(paquete.getString("estado_entrega"));
            this.idPaquete = this.paquete.getId_pedido();
            System.out.println("Producto: "+this.paquete.getId_pedido());
            textProducto.setText("Producto: "+this.paquete.getId_pedido());
            textEstado.setText("Estado: "+this.paquete.getEstado_entrega());
            textCodigo.setText("PIN: "+this.paquete.getCodigo());
            idTaq = this.paquete.getTaquillero();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIdNotificacion(int idNotificacion){
        this.idNotificacion = idNotificacion;
        //TODO: obtener el Id de notificacion a partir de un Paquete
    }

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

    public void setResultado(int resultado){
        this.resultado = resultado;
    }


    private void loadPaquete(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/getDatosPedido";
        VistaPaqueteServerConnectionThread thread = new VistaPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void loadTaquilleros(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/getTaquilleros";
        VistaPaqueteServerConnectionThread thread = new VistaPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void writeTaquillero(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/writeTaquillero";
        VistaPaqueteServerConnectionThread thread = new VistaPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void getNotif(){
        String urlStr = "http://192.168.0.166:8080";
        urlStr+="/uahlockers/getRecogidaNotificacionPedido";
        VistaPaqueteServerConnectionThread thread = new VistaPaqueteServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
