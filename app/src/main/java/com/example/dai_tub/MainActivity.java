package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int SPLASH_TIMEOUT = 2000; // Tempo de espera em milissegundos (2 segundos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen); // Exibe a tela de entrada

        // Aguarde por SPLASH_TIMEOUT antes de abrir a LoginActivity
        new Handler().postDelayed(() -> {
            // Ir para a tela de escolha (Login ou Registro)
            startActivity(new Intent(MainActivity.this, RegistroActivity.class));
            finish(); // Fecha a tela de entrada após abrir a tela de escolha
        }, SPLASH_TIMEOUT);

        // Insere as rotas no Firebase Realtime Database
        inserirRotas();
    }

    private void inserirRotas() {
        // Inicializa o Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rotasRef = database.getReference("rotas");

        // Verifica se há dados no nó "rotas"
        rotasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Se não houver dados, insere as rotas
                    List<Rota> rotas = criarListaRotas();
                    for (Rota rota : rotas) {
                        // Use o número da rota como chave primária
                        DatabaseReference rotaRef = rotasRef.child(rota.getNumero());
                        // Salva a rota no Firebase
                        rotaRef.setValue(rota);

                        // Verifica se o dataSnapshot contém a lista de horários
                        if (dataSnapshot.child("horarios").getValue() instanceof List) {
                            // Se for uma lista, salva os horários no Firebase
                            DatabaseReference horariosRef = rotaRef.child("horarios");
                            for (Horario horario : rota.getHorarios()) {
                                // Use push() para gerar chaves únicas para os horários
                                DatabaseReference horarioRef = horariosRef.push();
                                // Salva o horário no Firebase
                                horarioRef.setValue(horario);
                            }
                        } else {
                            Log.e(TAG, "Erro: dataSnapshot não contém uma lista de horários");
                        }
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Trate o erro, se necessário
                Log.e(TAG, "Erro ao verificar dados de rotas: " + databaseError.getMessage());
            }
        });
    }



    private List<Rota> criarListaRotas() {
        List<Rota> rotas = new ArrayList<>();

        // Rota 2
        List<Horario> horariosRota2 = new ArrayList<>();
        horariosRota2.add(new Horario(6, 45, 7, 30));
        horariosRota2.add(new Horario(7, 15, 8, 0));
        // Adicione os outros horários...
        rotas.add(new Rota("2", "Descrição da Rota 2", "Ponto de Partida da Rota 2", "Ponto de Chegada da Rota 2", 1.0, 2.0, horariosRota2));

        // Rota 3
        List<Horario> horariosRota3 = new ArrayList<>();
        horariosRota3.add(new Horario(19, 55, 20, 25));
        // Adicione os outros horários...
        rotas.add(new Rota("3", "Descrição da Rota 3", "Ponto de Partida da Rota 3", "Ponto de Chegada da Rota 3", 1.0, 2.0, horariosRota3));

        // Rota 5
        List<Horario> horariosRota5 = new ArrayList<>();
        horariosRota5.add(new Horario(7, 30, 8, 0));
        horariosRota5.add(new Horario(8, 30, 9, 0));
        horariosRota5.add(new Horario(17, 30, 18, 0));
        horariosRota5.add(new Horario(18, 30, 19, 0));
        // Adicione os outros horários...
        rotas.add(new Rota("5", "Descrição da Rota 5", "Ponto de Partida da Rota 5", "Ponto de Chegada da Rota 5", 1.0, 2.0, horariosRota5));

        // Rota 6
        List<Horario> horariosRota6 = new ArrayList<>();
        horariosRota6.add(new Horario(7, 25, 7, 45));
        horariosRota6.add(new Horario(10, 0, 10, 25));
        horariosRota6.add(new Horario(12, 0, 12, 25));
        horariosRota6.add(new Horario(16, 0, 16, 25));
        horariosRota6.add(new Horario(18, 15, 18, 45));
        horariosRota6.add(new Horario(19, 15, 19, 40));
        // Adicione os outros horários...
        rotas.add(new Rota("6", "Descrição da Rota 6", "Ponto de Partida da Rota 6", "Ponto de Chegada da Rota 6", 1.0, 2.0, horariosRota6));

        // Rota 7
        List<Horario> horariosRota7 = new ArrayList<>();
        horariosRota7.add(new Horario(6, 15, 7, 15));
        horariosRota7.add(new Horario(6, 45, 7, 45));
        // Adicione os outros horários...
        rotas.add(new Rota("7", "Descrição da Rota 7", "Ponto de Partida da Rota 7", "Ponto de Chegada da Rota 7", 1.0, 2.0, horariosRota7));

        // Adicione outras rotas...

        return rotas;
    }
}
