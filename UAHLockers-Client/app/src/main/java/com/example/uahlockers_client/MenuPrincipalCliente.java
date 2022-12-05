package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class MenuPrincipalCliente extends AppCompatActivity {

    int idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_cliente);

        Button button1 = (Button) findViewById(R.id.button_pedir_paq);
        Button button2 = (Button) findViewById(R.id.button_mis_paq);
        Button button3 = (Button) findViewById(R.id.button_mis_not);
        Button button4 = (Button) findViewById(R.id.button_cerr_ses);

        idCliente = getIntent().getIntExtra("idCliente",0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalCliente.this, PedirPaquete.class);
                startActivity(i);
                i.putExtra("idCliente", idCliente);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalCliente.this, MisPaquetes.class);
                startActivity(i);
                i.putExtra("idCliente", idCliente);
                finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalCliente.this, MisNotificaciones.class);
                startActivity(i);
                i.putExtra("idCliente", idCliente);
                finish();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalCliente.this, IniciarSesionCliente.class);
                startActivity(i);
                i.putExtra("idCliente", idCliente);
                finish();
            }
        });

    }

}