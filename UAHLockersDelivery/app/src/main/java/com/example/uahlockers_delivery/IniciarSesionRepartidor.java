package com.example.uahlockers_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IniciarSesionRepartidor extends AppCompatActivity {

    private Button button1, button2;
    private EditText textUName, textPwd;
    private TextView textErrMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_repartidor);

        button1 = (Button) findViewById(R.id.button_ini_ses);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textUName = (EditText) findViewById(R.id.textUName);
                textPwd = (EditText) findViewById(R.id.TextPwd);
                textErrMess = (TextView) findViewById(R.id.labelErrMess);
                iniciarSes(textUName.getText().toString(),textPwd.getText().toString());
            }
        });
    }

    public void iniciarSes(String uname, String pwd){
        int idRepartidor=0;
        // TODO: Enviar datos al servidor

        // TODO: Recibir respuesta de validación del servidor
        int resultado = 0;
        switch(resultado){
            case -1:{
                textErrMess.setText("Error: el nombre de usuario no está registrado");
                break;
            }
            case -2:{
                textErrMess.setText("Error: contraseña incorrecta");
                break;
            }
            default: {
                Intent i = new Intent(IniciarSesionRepartidor.this, MenuPrincipalRepartidor.class);
                startActivity(i);
                i.putExtra("idRepartidor", idRepartidor);
                finish();
            }
        }
    }
}