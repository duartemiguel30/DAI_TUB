package com.example.dai_tub;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class MeusBilhetesActivity extends AppCompatActivity {

    private static final String TAG = "MeusBilhetesActivity";

    private RecyclerView recyclerViewBilhetes;
    private BilheteAdapter bilheteAdapter;
    private List<Bilhete> bilhetesList;
    private DatabaseReference bilhetesRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_bilhetes);

        mAuth = FirebaseAuth.getInstance();
        bilhetesRef = FirebaseDatabase.getInstance().getReference().child("bilhetes");
        bilhetesList = new ArrayList<>();

        recyclerViewBilhetes = findViewById(R.id.recyclerViewBilhetes);
        recyclerViewBilhetes.setLayoutManager(new LinearLayoutManager(this));

        bilheteAdapter = new BilheteAdapter(bilhetesList);
        recyclerViewBilhetes.setAdapter(bilheteAdapter);

        carregarBilhetesUsuario();
    }

    private void carregarBilhetesUsuario() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            Log.d(TAG, "Email do utilizador: " + userEmail);
            bilhetesRef.orderByChild("nomeUsuario").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "Número de bilhetes encontrados: " + snapshot.getChildrenCount());
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Bilhete bilhete = dataSnapshot.getValue(Bilhete.class);
                            if (bilhete != null) {
                                bilhetesList.add(bilhete);
                            }
                        }
                        bilheteAdapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "Nenhum bilhete encontrado para este utilizador.");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Erro ao carregar bilhetes: " + error.getMessage());
                }
            });
        } else {
            Log.d(TAG, "Utilizador não está logado");
        }
    }}
