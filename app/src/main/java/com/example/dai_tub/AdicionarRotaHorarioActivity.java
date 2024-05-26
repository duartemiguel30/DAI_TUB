package com.example.dai_tub;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class AdicionarRotaHorarioActivity extends AppCompatActivity {

    private static final String TAG = "AdicionarRotaHorario";

    private EditText editNumeroRota;
    private EditText editDescricao;
    private EditText editPontoPartida;
    private EditText editPontoChegada;
    private EditText editPrecoEstudante;
    private EditText editPrecoNormal;
    private EditText editHorarioPartida;
    private EditText editHorarioChegada;
    private Button buttonAdicionarRota;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_rotas_horarios);

        // Inicialize os componentes de UI
        editNumeroRota = findViewById(R.id.editNumeroRota);
        editDescricao = findViewById(R.id.editDescricao);
        editPontoPartida = findViewById(R.id.editPontoPartida);
        editPontoChegada = findViewById(R.id.editPontoChegada);
        editPrecoEstudante = findViewById(R.id.editPrecoEstudante);
        editPrecoNormal = findViewById(R.id.editPrecoNormal);
        editHorarioPartida = findViewById(R.id.editHorarioPartida);
        editHorarioChegada = findViewById(R.id.editHorarioChegada);
        buttonAdicionarRota = findViewById(R.id.buttonAdicionarRota);

        // Inicialize o Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("rotas");

        // Defina o listener de clique para o botão Adicionar Rota
        buttonAdicionarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarRota();
            }
        });
    }

    private void adicionarRota() {
        String numeroRota = editNumeroRota.getText().toString().trim();
        String descricao = editDescricao.getText().toString().trim();
        String pontoPartida = editPontoPartida.getText().toString().trim();
        String pontoChegada = editPontoChegada.getText().toString().trim();
        double precoEstudante = Double.parseDouble(editPrecoEstudante.getText().toString());
        double precoNormal = Double.parseDouble(editPrecoNormal.getText().toString());

        // Obtenha os horários de partida e chegada inseridos
        String horarioPartidaString = editHorarioPartida.getText().toString().trim();
        String horarioChegadaString = editHorarioChegada.getText().toString().trim();

        // Verifique se os campos não estão vazios
        if (!numeroRota.isEmpty() && !descricao.isEmpty() && !pontoPartida.isEmpty() && !pontoChegada.isEmpty() && !horarioPartidaString.isEmpty() && !horarioChegadaString.isEmpty()) {
            // Parse dos horários de partida e chegada
            String[] partesHorarioPartida = horarioPartidaString.split(":");
            String[] partesHorarioChegada = horarioChegadaString.split(":");

            // Verifique se os horários foram inseridos corretamente
            if (partesHorarioPartida.length == 2 && partesHorarioChegada.length == 2) {
                int horaPartida = Integer.parseInt(partesHorarioPartida[0]);
                int minutoPartida = Integer.parseInt(partesHorarioPartida[1]);
                int horaChegada = Integer.parseInt(partesHorarioChegada[0]);
                int minutoChegada = Integer.parseInt(partesHorarioChegada[1]);

                // Crie uma nova lista de horários
                List<Horario> horariosList = new ArrayList<>();
                horariosList.add(new Horario(horaPartida, minutoPartida, horaChegada, minutoChegada)); // Adiciona o horário de partida e chegada fornecidos pelo administrador

                // Crie uma nova rota
                String id = databaseReference.push().getKey(); // Gere um ID único para a rota
                Rota rota = new Rota(numeroRota, descricao, pontoPartida, pontoChegada, precoEstudante, precoNormal, horariosList);

                // Adicione a rota ao Firebase Database
                databaseReference.child(id).setValue(rota);

                // Limpe os campos após adicionar a rota
                editNumeroRota.setText("");
                editDescricao.setText("");
                editPontoPartida.setText("");
                editPontoChegada.setText("");
                editPrecoEstudante.setText("");
                editPrecoNormal.setText("");
                editHorarioPartida.setText("");
                editHorarioChegada.setText("");

                Log.d(TAG, "Rota adicionada com sucesso: " + rota.toString());
            } else {
                Log.e(TAG, "Formato de horário de partida ou de chegada inválido.");
            }
        } else {
            // Mostre uma mensagem de erro indicando que todos os campos são obrigatórios
            Log.e(TAG, "Todos os campos são obrigatórios.");
        }
    }


}
