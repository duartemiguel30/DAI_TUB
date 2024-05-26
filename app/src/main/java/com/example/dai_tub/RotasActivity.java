package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RotasActivity extends AppCompatActivity implements RotaAdapter.OnItemClickListener {

    private static final String TAG = "RotasActivity";

    private RecyclerView recyclerView;
    private RotaAdapter adapter;
    private DatabaseReference rotasRef;
    private DatabaseReference bilhetesRef;
    private List<Rota> listaRotas;
    private List<Rota> listaRotasFiltradas;
    private Rota rotaSelecionada;
    private Button btnConfirmarPagamento;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotas);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaRotas = new ArrayList<>();
        listaRotasFiltradas = new ArrayList<>();
        adapter = new RotaAdapter(this, listaRotasFiltradas, this); // Passando o contexto e a atividade como listener
        recyclerView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();

        rotasRef = FirebaseDatabase.getInstance().getReference().child("rotas");
        bilhetesRef = FirebaseDatabase.getInstance().getReference().child("bilhetes");

        carregarTodasRotas();

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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
    }

    private void carregarTodasRotas() {
        listaRotas.clear();

        rotasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Rota> rotas = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Rota rota = dataSnapshot.getValue(Rota.class);
                    if (rota != null) {
                        rotas.add(rota);
                    }
                }

                listaRotas.addAll(rotas);
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
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                String nomeUsuario = currentUser.getDisplayName();
                String emailUsuario = currentUser.getEmail();
                if (nomeUsuario == null) {
                    nomeUsuario = emailUsuario;
                }
                String userId = currentUser.getUid();
                salvarBilhete(rotaSelecionada, nomeUsuario, userId);
            } else {
                Toast.makeText(this, "Você precisa estar logado para confirmar o pagamento", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            }
        } else {
            Toast.makeText(this, "Selecione uma rota antes de confirmar o pagamento", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarBilhete(Rota rota, String nomeUsuario, String userId) {
        String bilheteId = bilhetesRef.push().getKey();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataAtual = dateFormat.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 7);
        String dataValidade = dateFormat.format(calendar.getTime());

        Bilhete bilhete = new Bilhete(bilheteId, userId, nomeUsuario, dataAtual, dataValidade, rota.getPontoPartida(), rota.getPontoChegada(), rota);

        bilhetesRef.child(bilheteId).setValue(bilhete).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RotasActivity.this, "Bilhete salvo com sucesso", Toast.LENGTH_SHORT).show();
                rotaSelecionada = null;

                Intent intent = new Intent(RotasActivity.this, ConfirmarPagamentoActivity.class);
                intent.putExtra("bilheteId", bilheteId);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RotasActivity.this, "Erro ao salvar bilhete: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
