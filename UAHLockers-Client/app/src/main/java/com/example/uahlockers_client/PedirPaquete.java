package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PedirPaquete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_paquete);

        Button button1 = (Button) findViewById(R.id.button_paq_prueba);
        Button button2 = (Button) findViewById(R.id.button_volver);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Código del botón
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PedirPaquete.this, MenuPrincipalCliente.class);
                startActivity(i);
                finish();
            }
        });
    }
}
