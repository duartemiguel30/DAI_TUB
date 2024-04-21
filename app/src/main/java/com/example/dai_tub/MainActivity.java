package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        // Cria uma lista com todas as rotas
        List<Rota> rotas = criarListaRotas();

        // Inicializa o Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rotasRef = database.getReference("rotas");

        // Insere cada rota no Firebase Realtime Database
        for (Rota rota : rotas) {
            // Use um identificador único para cada rota, por exemplo, o próprio índice da lista
            rotasRef.push().setValue(rota);
        }
    }

    // Método para criar e retornar a lista de todas as rotas
    private List<Rota> criarListaRotas() {
        List<Rota> rotas = new ArrayList<>();

        rotas.add(new Rota("Ponte de Prado", "Ponte de Prado", "Bom Jesus", "Bom Jesus", 0.5, 1.0));
        rotas.add(new Rota("Avenida Central", "Avenida Central", "Ruães", "Ruães", 1.0, 2.0));
        rotas.add(new Rota("Dume", "Dume", "Quinta da Capela", "Quinta da Capela", 1.0, 1.0));
        rotas.add(new Rota("Avenida General Norton de Matos", "Avenida General Norton de Matos", "Gondizalhes/Semelhe", "Gondizalhes/Semelhe", 1.0, 1.0));
        rotas.add(new Rota("S Mamede d'este", "S Mamede d'este", "Celeirós", "Celeirós", 0.5, 1.0));
        rotas.add(new Rota("Rua 25 de abril", "Rua 25 de abril", "Sete Fontes", "Sete Fontes", 1.0, 1.0));
        rotas.add(new Rota("Ruães Nogueira", "Ruães Nogueira", "Barral", "Barral", 0.5, 1.0));
        rotas.add(new Rota("Avenida Liberdade", "Avenida Liberdade", "Lageosa/Pedralva via Gualtar", "Lageosa/Pedralva via Gualtar", 1.0, 2.0));
        rotas.add(new Rota("Avenida General Norton de Matos", "Avenida General Norton de Matos", "Lageosa/Pedralva", "Lageosa/Pedralva", 1.0, 2.0));
        rotas.add(new Rota("Praça Conde de Agrolongo", "Praça Conde de Agrolongo", "Priscos", "Priscos", 1.0, 2.0));
        rotas.add(new Rota("Rua do Raio", "Rua do Raio", "Pinheiro do Bicho via Esporões", "Pinheiro do Bicho via Esporões", 1.0, 2.0));
        rotas.add(new Rota("Sequeira", "Sequeira", "Gualtar", "Gualtar", 1.0, 2.0));
        rotas.add(new Rota("Avenida Central", "Avenida Central", "Hospital", "Hospital", 1.0, 1.0));
        rotas.add(new Rota("Misericórdia", "Misericórdia", "S.Mamede D'este", "S.Mamede D'este", 0.5, 1.0));
        rotas.add(new Rota("Avenida Central", "Avenida Central", "Padim de Graça", "Padim de Graça", 1.0, 2.0));

        return rotas;
    }
}
