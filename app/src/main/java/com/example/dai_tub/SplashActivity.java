package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen); // Exibe a tela de entrada

        // Redireciona para a MainActivity após um breve intervalo
        new android.os.Handler().postDelayed(
                () -> {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                },
                2000 // Tempo de espera em milissegundos (2 segundos)
        );
    }
}
