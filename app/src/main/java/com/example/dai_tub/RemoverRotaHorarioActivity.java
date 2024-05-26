package com.example.dai_tub;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RemoverRotaHorarioActivity extends AppCompatActivity {

    private static final String TAG = "RemoverRotaHorario";

    private Spinner spinnerRotasHorarios;
    private DatabaseReference rotasHorariosRef;
    private List<Rota> rotasList;
    private List<String> rotasHorariosList;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remover_rota_horario);

        spinnerRotasHorarios = findViewById(R.id.spinner_rotas_horarios);
        Button confirmarRemoverButton = findViewById(R.id.confirmar_remover_rota_horario);

        rotasHorariosRef = FirebaseDatabase.getInstance().getReference().child("rotas");
        rotasList = new ArrayList<>();
        rotasHorariosList = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rotasHorariosList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRotasHorarios.setAdapter(spinnerAdapter);

        buscarRotasHorarios();

        confirmarRemoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerRotaHorarioSelecionada();
            }
        });
    }

    private void buscarRotasHorarios() {
        Log.d(TAG, "Iniciando a busca de rotas e horários");
        rotasHorariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Dados recebidos do Firebase");
                rotasList.clear();
                rotasHorariosList.clear();
                for (DataSnapshot rotaSnapshot : dataSnapshot.getChildren()) {
                    Rota rota = rotaSnapshot.getValue(Rota.class);
                    if (rota != null) {
                        rotasList.add(rota);
                        for (Horario horario : rota.getHorarios()) {
                            String rotaHorarioString = rota.getNumero() + ": " + horario.getHoraPartida() + ":" + horario.getMinutoPartida() + " - " + horario.getHoraChegada() + ":" + horario.getMinutoChegada();
                            rotasHorariosList.add(rotaHorarioString);
                            Log.d(TAG, "Adicionado: " + rotaHorarioString);
                        }
                    }
                }
                if (rotasHorariosList.isEmpty()) {
                    Log.d(TAG, "Nenhuma rota e horário encontrados.");
                } else {
                    Log.d(TAG, "Total de rotas e horários: " + rotasHorariosList.size());
                }
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RemoverRotaHorarioActivity.this, "Erro ao carregar rotas e horários.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Erro ao carregar rotas e horários", databaseError.toException());
            }
        });
    }

    private void removerRotaHorarioSelecionada() {
        int selectedPosition = spinnerRotasHorarios.getSelectedItemPosition();
        if (selectedPosition == AdapterView.INVALID_POSITION) {
            Toast.makeText(this, "Por favor, selecione uma rota e horário para remover.", Toast.LENGTH_SHORT).show();
            return;
        }

        String rotaHorarioSelecionada = rotasHorariosList.get(selectedPosition);
        String[] partes = rotaHorarioSelecionada.split(": ");
        if (partes.length < 2) {
            Toast.makeText(this, "Erro ao obter detalhes da rota e horário selecionados.", Toast.LENGTH_SHORT).show();
            return;
        }
        String numeroRota = partes[0];
        String[] horarios = partes[1].split(" - ");
        if (horarios.length < 2) {
            Toast.makeText(this, "Erro ao obter detalhes do horário selecionado.", Toast.LENGTH_SHORT).show();
            return;
        }
        String horarioPartida = horarios[0];
        String horarioChegada = horarios[1];

        rotasHorariosRef.orderByChild("numero").equalTo(numeroRota).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot rotaSnapshot : dataSnapshot.getChildren()) {
                        Rota rota = rotaSnapshot.getValue(Rota.class);
                        if (rota != null) {
                            List<Horario> horariosList = rota.getHorarios();
                            if (horariosList != null) {
                                for (Horario horario : horariosList) {
                                    String horarioPartidaString = horario.getHoraPartida() + ":" + horario.getMinutoPartida();
                                    String horarioChegadaString = horario.getHoraChegada() + ":" + horario.getMinutoChegada();
                                    if (horarioPartidaString.equals(horarioPartida) && horarioChegadaString.equals(horarioChegada)) {
                                        horariosList.remove(horario);
                                        if (horariosList.isEmpty()) {
                                            rotaSnapshot.getRef().removeValue().addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RemoverRotaHorarioActivity.this, "Rota e horário removidos com sucesso!", Toast.LENGTH_SHORT).show();
                                                    rotasHorariosList.remove(selectedPosition);
                                                    spinnerAdapter.notifyDataSetChanged();
                                                } else {
                                                    Toast.makeText(RemoverRotaHorarioActivity.this, "Erro ao remover a rota e horário. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            rotaSnapshot.getRef().setValue(rota).addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RemoverRotaHorarioActivity.this, "Horário removido com sucesso!", Toast.LENGTH_SHORT).show();
                                                    rotasHorariosList.remove(selectedPosition);
                                                    spinnerAdapter.notifyDataSetChanged();
                                                } else {
                                                    Toast.makeText(RemoverRotaHorarioActivity.this, "Erro ao remover o horário. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(RemoverRotaHorarioActivity.this, "Rota e horário não encontrados.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RemoverRotaHorarioActivity.this, "Erro ao procurar rota e horário. Tente novamente.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Erro ao procurar rota e horário", databaseError.toException());
            }
        });
    }
}