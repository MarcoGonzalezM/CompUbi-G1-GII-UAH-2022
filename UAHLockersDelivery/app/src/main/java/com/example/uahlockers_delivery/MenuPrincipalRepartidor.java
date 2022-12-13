package com.example.uahlockers_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalRepartidor extends AppCompatActivity {

    int idRepartidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_repartidor);

        Button button1 = (Button) findViewById(R.id.button_depositar_paq);

        idRepartidor = getIntent().getIntExtra("idRepartidor",0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalRepartidor.this, DepositarPaquete.class);
                startActivity(i);
                i.putExtra("idRepartidor", idRepartidor);
                finish();
            }
        });
    }

}