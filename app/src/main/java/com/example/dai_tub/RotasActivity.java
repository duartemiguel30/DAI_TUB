package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
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
        for (Rota rota : listaRotas) {
            if (rota.getPontoPartida().toLowerCase().contains(texto.toLowerCase()) || rota.getPontoChegada().toLowerCase().contains(texto.toLowerCase())) {
                listaRotasFiltradas.add(rota);
            }
        }
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
