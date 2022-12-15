package com.example.uahlockers_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalRepartidor extends AppCompatActivity {

    private int idRepartidor;
    private Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_repartidor);

        button1 = (Button) findViewById(R.id.button_depositar_paq);
        button2 = (Button) findViewById(R.id.button_cerrar_ses);


        idRepartidor = getIntent().getIntExtra("idRepartidor",0);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalRepartidor.this, DepositarPaquete.class);
                i.putExtra("idRepartidor", idRepartidor);
                startActivity(i);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalRepartidor.this, IniciarSesionRepartidor.class);
                startActivity(i);
                finish();
            }
        });
    }
}