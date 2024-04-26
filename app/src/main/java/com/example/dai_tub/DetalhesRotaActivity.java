package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class DetalhesRotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_rota_activity);

        // Receber os detalhes da rota da intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("rota")) {
            Rota rota = intent.getParcelableExtra("rota");

            // Exibir os horários da rota na TextView
            TextView horariosTextView = findViewById(R.id.horariosTextView);
            horariosTextView.setText("Horário de partida: " + rota.getHorarioPartida() + "\n" +
                    "Horário de chegada: " + rota.getHorarioChegada());
        }
    }
}
