package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RotasActivity extends AppCompatActivity implements RotaAdapter.OnItemClickListener {

    private static final String TAG = "RotasActivity";

    private RecyclerView recyclerView;
    private RotaAdapter adapter;
    private DatabaseReference rotasRef;
    private List<Rota> listaRotas;
    private List<Rota> listaRotasFiltradas;

    private Rota rotaSelecionada;
    private Button btnConfirmarPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotas);

        // Inicializa o RecyclerView e o Adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaRotas = new ArrayList<>();
        listaRotasFiltradas = new ArrayList<>();
        adapter = new RotaAdapter(this, listaRotasFiltradas, this);
        recyclerView.setAdapter(adapter);

        // Inicializa o Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        rotasRef = database.getReference("rotas");

        // Obtém o texto da barra de pesquisa da Intent, se houver
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("searchQuery")) {
            String searchQuery = intent.getStringExtra("searchQuery");
            // Se houver um texto de pesquisa, filtra as rotas com base nele
            if (searchQuery != null) {
                filtrarRotas(searchQuery);
            }
        }

        // Configura a barra de pesquisa
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtra as rotas conforme o texto digitado na barra de pesquisa
                filtrarRotas(newText);
                return true;
            }
        });

        btnConfirmarPagamento = findViewById(R.id.btnConfirmarPagamento);
        btnConfirmarPagamento.setEnabled(false);
        btnConfirmarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmarPagamentoClick();
            }
        });

        carregarTodasRotas();
    }

    private void carregarTodasRotas() {
        listaRotas.clear();
        rotasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Rota rota = dataSnapshot.getValue(Rota.class);
                    if (rota != null) {
                        listaRotas.add(rota);
                    }
                }
                // Filtra as rotas com base no texto vazio, exibindo todas inicialmente
                filtrarRotas("");
                Log.d(TAG, "Rotas carregadas com sucesso. Total de rotas: " + listaRotas.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(RotasActivity.this, "Erro ao carregar rotas: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Erro ao carregar rotas: " + error.getMessage());
            }
        });
    }

    private void filtrarRotas(String texto) {
        listaRotasFiltradas.clear();
        if (texto.isEmpty()) {
            // Se o texto estiver vazio, exibe todas as rotas
            listaRotasFiltradas.addAll(listaRotas);
        } else {
            // Se houver texto, filtra as rotas com base nele
            for (Rota rota : listaRotas) {
                // Converte o texto para minúsculas para garantir a comparação sem distinção entre maiúsculas e minúsculas
                String textoLowerCase = texto.toLowerCase();
                // Verifica se o ponto de partida, ponto de chegada ou número da rota contêm o texto fornecido, ignorando maiúsculas e minúsculas
                if (rota.getPontoPartida().toLowerCase().contains(textoLowerCase) ||
                        rota.getPontoChegada().toLowerCase().contains(textoLowerCase) ||
                        rota.getNumero().toLowerCase().contains(textoLowerCase)) {
                    listaRotasFiltradas.add(rota);
                }
            }
        }
        // Atualiza o RecyclerView com as rotas filtradas
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Rota rota, int position) {
        rotaSelecionada = rota;
        adapter.setSelectedPosition(position);
        btnConfirmarPagamento.setEnabled(true);
    }

    private void onConfirmarPagamentoClick() {
        if (rotaSelecionada != null) {
            // Lógica para confirmar o pagamento
        } else {
            Toast.makeText(this, "Selecione uma rota antes de confirmar o pagamento", Toast.LENGTH_SHORT).show();
        }
    }
}
