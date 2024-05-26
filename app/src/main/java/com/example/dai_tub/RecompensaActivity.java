package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecompensaActivity extends AppCompatActivity implements RotaAdapter.OnItemClickListener {

    private static final String TAG = "Recompensa";

    private RecyclerView recyclerView;
    private RotaAdapter adapter;
    private DatabaseReference usersRef;
    private DatabaseReference rotasRef;
    private DatabaseReference bilhetesRef;
    private List<Rota> listaRotas;
    private List<Rota> listaRotasFiltradas;
    private Rota rotaSelecionada;
    private Button resgatarRecompensaButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recompensa);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaRotas = new ArrayList<>();
        listaRotasFiltradas = new ArrayList<>();
        adapter = new RotaAdapter(this, listaRotasFiltradas, this);
        recyclerView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        rotasRef = FirebaseDatabase.getInstance().getReference().child("rotas");
        bilhetesRef = FirebaseDatabase.getInstance().getReference().child("bilhetes");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            carregarTodasRotas();
            resgatarRecompensaButton = findViewById(R.id.btnResgatarViagemGratis);
            resgatarRecompensaButton.setEnabled(false);
            resgatarRecompensaButton.setOnClickListener(v -> resgatarRecompensa(currentUser.getUid()));
        } else {
            redirectToLogin();
        }

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
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RecompensaActivity.this, "Erro ao carregar rotas: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
        resgatarRecompensaButton.setEnabled(true);
    }

    private void resgatarRecompensa(String userId) {
        if (rotaSelecionada != null) {
            DatabaseReference userRef = usersRef.child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            int viagensCompradas = user.getViagensCompradas();
                            if (viagensCompradas % 5 == 0) {
                                showToast("Parabéns! Você ganhou uma viagem grátis!");

                                // Salvar o bilhete
                                salvarBilhete(rotaSelecionada, user.getName(), userId);
                            } else {
                                showToast("Você ainda não possui viagens suficientes para resgatar uma recompensa.");
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "Erro ao acessar a base de dados: " + databaseError.getMessage());
                }
            });
        } else {
            showToast("Selecione uma rota antes de confirmar o pagamento");
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
                showToast("Bilhete salvo com sucesso");
                rotaSelecionada = null;

                Intent intent = new Intent(RecompensaActivity.this, ConfirmarPagamentoActivity.class);
                intent.putExtra("bilheteId", bilheteId);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("Erro ao salvar bilhete: " + e.getMessage());
            }
        });
    }}
