package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class MenuPrincipalCliente extends AppCompatActivity {

    private int idCliente;
    private Button button1, button2, button3, button4, button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_cliente);

        button1 = (Button) findViewById(R.id.button_pedir_paq);
        button2 = (Button) findViewById(R.id.button_mis_paq);
        button3 = (Button) findViewById(R.id.button_mis_not);
        button5 = (Button) findViewById(R.id.button_cerr_ses);

        System.out.println((getIntent().getExtras()));
        idCliente = getIntent().getIntExtra("idCliente",0);
        System.out.println(idCliente);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalCliente.this, PedirPaquete.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalCliente.this, MisPaquetes.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalCliente.this, MisNotificaciones.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalCliente.this, IniciarSesionCliente.class);
                i.putExtra("idCliente", idCliente);
                startActivity(i);
                finish();
            }
        });
    }
}