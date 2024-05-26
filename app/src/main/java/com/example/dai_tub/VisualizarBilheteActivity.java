

package com.example.dai_tub;

import android.os.Bundle;

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

public class VisualizarBilheteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBilhetes;
    private BilheteAdapter bilheteAdapter;
    private List<Bilhete> bilhetesList;
    private DatabaseReference bilhetesRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_bilhete);

        recyclerViewBilhetes = findViewById(R.id.recyclerViewBilhetes);
        recyclerViewBilhetes.setHasFixedSize(true);
        recyclerViewBilhetes.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        bilhetesRef = FirebaseDatabase.getInstance().getReference().child("bilhetes");
        bilhetesList = new ArrayList<>();
        bilheteAdapter = new BilheteAdapter(bilhetesList);

        recyclerViewBilhetes.setAdapter(bilheteAdapter);

        carregarBilhetes();
    }

    private void carregarBilhetes() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            bilhetesRef.orderByChild("nomeUsuario").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Bilhete bilhete = dataSnapshot.getValue(Bilhete.class);
                        if (bilhete != null) {
                            bilhetesList.add(bilhete);
                        }
                    }
                    bilheteAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}