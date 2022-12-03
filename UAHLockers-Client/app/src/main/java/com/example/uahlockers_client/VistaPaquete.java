package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class VistaPaquete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_paquete);

        Button button1 = (Button) findViewById(R.id.buttonAuth);
        Button button2 = (Button) findViewById(R.id.buttonBack);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VistaPaquete.this, Autentificacion.class);
                startActivity(i);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VistaPaquete.this, MisPaquetes.class);
                startActivity(i);
                finish();
            }
        });
    }
}
