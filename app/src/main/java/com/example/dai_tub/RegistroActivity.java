package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView textSkip = findViewById(R.id.textSkip);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirecionar para a tela de registro 2
                Intent intent = new Intent(RegistroActivity.this, Registro2Activity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirecionar para a tela de login
                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirecionar diretamente para a tela do menu principal
                Intent intent = new Intent(RegistroActivity.this, MenuPrincipalActivity.class);
                startActivity(intent);
                finish(); // Se desejar finalizar a atividade atual
            }
        });
    }
}
