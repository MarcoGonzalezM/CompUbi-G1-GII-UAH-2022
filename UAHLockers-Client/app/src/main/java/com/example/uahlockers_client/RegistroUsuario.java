package com.example.uahlockers_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroUsuario extends AppCompatActivity {

    private EditText textUName, textPwd, textConfPwd;
    private TextView textErrMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        Button button1 = (Button) findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textUName = (EditText) findViewById(R.id.TextUName);
                textPwd = (EditText) findViewById(R.id.TextPwd);
                textConfPwd = (EditText) findViewById(R.id.TextConfPwd);
                textErrMess = (TextView) findViewById(R.id.labelErrMess);
                registrar(textUName.getText().toString(),textPwd.getText().toString(),textConfPwd.getText().toString());
                iniciarSes(textUName.getText().toString(),textPwd.getText().toString());
            }
        });
    }

    public void registrar(String uname, String pwd, String confPwd){
        if (pwd.equals(confPwd)){
            // TODO: Enviar datos al servidor

            // TODO: Recibir respuesta de validación del servidor
            int resultado = 0;
            if (resultado==-1){
                textErrMess.setText("Error: nombre de usuario no disponible.");
            }
            finish();
        } else {
            textErrMess.setText("Error: las contraseñas no coinciden.");
        }
    }

    public void iniciarSes(String uname, String pwd){
        int idCliente=0;
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
                Intent i = new Intent(RegistroUsuario.this, MenuPrincipalCliente.class);
                startActivity(i);
                i.putExtra("idCliente", idCliente);
                finish();
            }
        }
    }
}
