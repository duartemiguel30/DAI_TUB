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
    private static final int SPLASH_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, RegistroActivity.class));
            finish();
        }, SPLASH_TIMEOUT);

        inserirRotas();
    }

    private void inserirRotas() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rotasRef = database.getReference("rotas");

        rotasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    List<Rota> rotas = criarListaRotas();
                    for (Rota rota : rotas) {
                        DatabaseReference rotaRef = rotasRef.child(rota.getNumero());
                        rotaRef.setValue(rota);

                        if (dataSnapshot.child("horarios").getValue() instanceof List) {
                            DatabaseReference horariosRef = rotaRef.child("horarios");
                            for (Horario horario : rota.getHorarios()) {
                                DatabaseReference horarioRef = horariosRef.push();
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

        // Rota 8
        List<Horario> horariosRota8 = new ArrayList<>();
        horariosRota8.add(new Horario(8, 15, 8, 30));
        horariosRota8.add(new Horario(9, 15, 9, 30));
        horariosRota8.add(new Horario(12, 30, 12, 45));
        horariosRota8.add(new Horario(14, 15, 14, 30));
        horariosRota8.add(new Horario(17, 15, 17, 30));
// Adicione os outros horários...
        rotas.add(new Rota("8", "Descrição da Rota 8", "Ponto de Partida: Rua 25 de Abril", "Ponto de Chegada: Sete Fontes", 1.0, 2.0, horariosRota8));

// Rota 9
        List<Horario> horariosRota9 = new ArrayList<>();
        horariosRota9.add(new Horario(7, 25, 8, 25));
        horariosRota9.add(new Horario(8, 25, 9, 25));
        horariosRota9.add(new Horario(9, 25, 10, 25));
        horariosRota9.add(new Horario(10, 25, 11, 25));
        horariosRota9.add(new Horario(11, 25, 12, 25));
        horariosRota9.add(new Horario(12, 25, 13, 25));
        horariosRota9.add(new Horario(13, 25, 14, 25));
        horariosRota9.add(new Horario(14, 25, 15, 25));
        horariosRota9.add(new Horario(15, 25, 16, 25));
        horariosRota9.add(new Horario(16, 25, 17, 25));
        horariosRota9.add(new Horario(17, 25, 18, 25));
        horariosRota9.add(new Horario(18, 25, 19, 25));
        horariosRota9.add(new Horario(19, 25, 20, 25));
// Adicione os outros horários...
        rotas.add(new Rota("9", "Descrição da Rota 9", "Ponto de Partida: Ruães", "Ponto de Chegada: Nogueira(Barral)", 1.0, 2.0, horariosRota9));

// Rota 12
        List<Horario> horariosRota12 = new ArrayList<>();
        horariosRota12.add(new Horario(7, 30, 7, 55));
        horariosRota12.add(new Horario(8, 45, 9, 10));
        horariosRota12.add(new Horario(12, 5, 12, 25));
        horariosRota12.add(new Horario(18, 25, 18, 50));
// Adicione os outros horários...
        rotas.add(new Rota("12", "Descrição da Rota 12", "Ponto de Partida: Avenida Liberdade", "Ponto de Chegada: Lageosa/Pedralva via Gualtar", 1.0, 2.0, horariosRota12));

// Rota 13
        List<Horario> horariosRota13 = new ArrayList<>();
        horariosRota13.add(new Horario(6, 0, 6, 40));
        horariosRota13.add(new Horario(6, 40, 7, 20));
        horariosRota13.add(new Horario(7, 15, 7, 55));
        horariosRota13.add(new Horario(8, 40, 9, 20));
        horariosRota13.add(new Horario(10, 0, 10, 40));
        horariosRota13.add(new Horario(11, 20, 12, 0));
        horariosRota13.add(new Horario(12, 40, 13, 20));
        horariosRota13.add(new Horario(14, 0, 14, 40));
        horariosRota13.add(new Horario(15, 20, 16, 0));
        horariosRota13.add(new Horario(16, 40, 17, 20));
        horariosRota13.add(new Horario(17, 20, 18, 0));
        horariosRota13.add(new Horario(18, 0, 18, 40));
        horariosRota13.add(new Horario(18, 40, 19, 20));
        horariosRota13.add(new Horario(20, 0, 20, 40));
// Adicione os outros horários...
        rotas.add(new Rota("13", "Descrição da Rota 13", "Ponto de Partida: Avenida General Norton de Matos", "Ponto de Chegada: Lageosa/Pedralva", 1.0, 2.0, horariosRota13));

// Rota 14
        List<Horario> horariosRota14 = new ArrayList<>();
        horariosRota14.add(new Horario(7, 5, 7, 40));
// Adicione os outros horários...
        rotas.add(new Rota("14", "Descrição da Rota 14", "Ponto de Partida: Praça Conde de Agrolongo", "Ponto de Chegada: Priscos", 1.0, 2.0, horariosRota14));

// Rota 18
        List<Horario> horariosRota18 = new ArrayList<>();
        horariosRota18.add(new Horario(7, 0, 7, 15));
        horariosRota18.add(new Horario(7, 35, 7, 50));
        horariosRota18.add(new Horario(8, 10, 8, 25));
        horariosRota18.add(new Horario(8, 50, 9, 05));
        horariosRota18.add(new Horario(9, 30, 9, 45));
        horariosRota18.add(new Horario(10, 10, 10, 25));
        horariosRota18.add(new Horario(10, 50, 11, 05));
        horariosRota18.add(new Horario(11, 30, 11, 45));
        horariosRota18.add(new Horario(12, 05, 12, 20));
        horariosRota18.add(new Horario(12, 30, 12, 45));
        horariosRota18.add(new Horario(13, 10, 13, 25));
        horariosRota18.add(new Horario(15, 10, 15, 25));
        horariosRota18.add(new Horario(15, 50, 16, 05));
        horariosRota18.add(new Horario(16, 30, 16, 45));
        horariosRota18.add(new Horario(17, 05, 17, 20));
        horariosRota18.add(new Horario(17, 45, 18, 0));
        horariosRota18.add(new Horario(18, 0, 18, 15));
        horariosRota18.add(new Horario(18, 25, 18, 45));
        horariosRota18.add(new Horario(19, 10, 19, 25));
        horariosRota18.add(new Horario(19, 50, 20, 05));
        horariosRota18.add(new Horario(20, 25, 20, 40));
// Adicione os outros horários...
        rotas.add(new Rota("18", "Descrição da Rota 18", "Ponto de Partida: Rua do Raio", "Ponto de Chegada: Pinheiro do Bicho via Esporões", 1.0, 2.0, horariosRota18));

// Rota 24
        List<Horario> horariosRota24 = new ArrayList<>();
        horariosRota24.add(new Horario(6, 50, 7, 40));
        horariosRota24.add(new Horario(7, 25, 8, 20));
        horariosRota24.add(new Horario(8, 20, 9, 20));
        horariosRota24.add(new Horario(17, 20, 18, 20));
        horariosRota24.add(new Horario(19, 40, 20, 40));
// Adicione os outros horários...
        rotas.add(new Rota("24", "Descrição da Rota 24", "Ponto de Partida: Sequeira", "Ponto de Chegada: Gualtar", 1.0, 2.0, horariosRota24));

// Rota 900
        List<Horario> horariosRota900 = new ArrayList<>();
        horariosRota900.add(new Horario(21, 40, 21, 55));
// Adicione os outros horários...
        rotas.add(new Rota("900", "Descrição da Rota 900", "Ponto de Partida: Avenida Central", "Ponto de Chegada: Hospital", 1.0, 2.0, horariosRota900));

// Rota 907
        List<Horario> horariosRota907 = new ArrayList<>();
        horariosRota907.add(new Horario(21, 15, 21, 45));
        horariosRota907.add(new Horario(22, 15, 22, 45));
        horariosRota907.add(new Horario(23, 15, 23, 45));
// Adicione os outros horários...
        rotas.add(new Rota("907", "Descrição da Rota 907", "Ponto de Partida: Misericórdia", "Ponto de Chegada:S.Mamede D'Este", 1.0, 2.0, horariosRota907));

// Rota 911
        List<Horario> horariosRota911 = new ArrayList<>();
        horariosRota911.add(new Horario(22, 30, 22, 55));
        rotas.add(new Rota("911", "Descrição da Rota 911", "Ponto de Partida: Avenida Central", "Ponto de Chegada: Padim da Graça", 1.0, 2.0, horariosRota911));



        return rotas;
    }
}