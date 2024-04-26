package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
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
                        // Use um identificador único para cada rota, por exemplo, o próprio índice da lista
                        rotasRef.push().setValue(rota);
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


    // Método para criar e retornar a lista de todas as rotas
    // Método para criar e retornar a lista de todas as rotas
    private List<Rota> criarListaRotas() {
        List<Rota> rotas = new ArrayList<>();

        rotas.add(new Rota("2", "Ponte de Prado-Bom Jesus", "Ponte de Prado", "Bom Jesus", 0.5, 1.0, "6:45", "7:30"));
        rotas.add(new Rota("2", "Ponte de Prado-Bom Jesus", "Ponte de Prado", "Bom Jesus", 0.5, 1.0, "7:15", "8:00"));
        rotas.add(new Rota("3", "Avenida Central-Ruães", "Avenida Central", "Ruães", 1.0, 2.0, "8:00", "9:00"));
        rotas.add(new Rota("5", "Dume-Quinta da Capela", "Dume", "Quinta da Capela", 1.0, 1.0, "9:30", "10:15"));
        rotas.add(new Rota("6", "Avenida General Norton de Matos-Gondizalves/Semelhe", "Avenida General Norton de Matos", "Gondizalhes/Semelhe", 1.0, 1.0, "10:00", "11:00"));
        rotas.add(new Rota("7", "S Mamede d'este-Celeirós", "S Mamede d'este", "Celeirós", 0.5, 1.0, "11:30", "12:00"));
        rotas.add(new Rota("8", "Rua 25 de abril-Sete Fontes", "Rua 25 de abril", "Sete Fontes", 1.0, 1.0, "12:00", "13:00"));
        rotas.add(new Rota("9", "Ruães-Nogueira(Barral)", "Ruães", "Barral", 0.5, 1.0, "13:30", "14:00"));
        rotas.add(new Rota("12", "Avenida Liberdade-Lageosa/Pedralva via Gualtar", "Avenida Liberdade", "Lageosa/Pedralva via Gualtar", 1.0, 2.0, "14:30", "15:30"));
        rotas.add(new Rota("13", "Avenida General Norton de Matos-Lageosa/Pedralva", "Avenida General Norton de Matos", "Lageosa/Pedralva", 1.0, 2.0, "15:00", "16:00"));
        rotas.add(new Rota("14", "Praça Conde de Agrolongo-Priscos", "Praça Conde de Agrolongo", "Priscos", 1.0, 2.0, "16:30", "17:00"));
        rotas.add(new Rota("18", "Rua do Raio-Pinheiro do Bicho via Esporões", "Rua do Raio", "Pinheiro do Bicho via Esporões", 1.0, 2.0, "17:00", "18:00"));
        rotas.add(new Rota("24", "Sequeira-Gualtar", "Sequeira", "Gualtar", 1.0, 2.0, "18:30", "19:00"));
        rotas.add(new Rota("900", "Avenida Central-Hospital", "Avenida Central", "Hospital", 1.0, 1.0, "19:00", "20:00"));
        rotas.add(new Rota("907", "Misericórdia-S Mamede d'este", "Misericórdia", "S.Mamede D'este", 0.5, 1.0, "20:30", "21:00"));
        rotas.add(new Rota("911", "Avenida Central-Padim da Graça", "Avenida Central", "Padim de Graça", 1.0, 2.0, "21:00", "22:00"));

        return rotas;
    }}

