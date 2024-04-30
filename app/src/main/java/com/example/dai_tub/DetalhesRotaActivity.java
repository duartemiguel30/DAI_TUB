package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import java.util.List;

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

            // Verificar se a lista de horários da rota é nula
            if (rota.getHorarios() != null) {
                // Construir uma string com os horários
                StringBuilder horarios = new StringBuilder();
                horarios.append("Horários:\n");
                List<Horario> listaHorarios = rota.getHorarios();
                for (Horario horario : listaHorarios) {
                    horarios.append("Partida: ")
                            .append(horario.getHoraPartida())
                            .append(":")
                            .append(horario.getMinutoPartida())
                            .append(" - Chegada: ")
                            .append(horario.getHoraChegada())
                            .append(":")
                            .append(horario.getMinutoChegada())
                            .append("\n");
                }

                // Exibir os horários na TextView
                horariosTextView.setText(horarios.toString());
            } else {
                // Se a lista de horários for nula, exibir uma mensagem adequada
                horariosTextView.setText("Não há horários disponíveis para esta rota.");
            }
        }
    }
}
