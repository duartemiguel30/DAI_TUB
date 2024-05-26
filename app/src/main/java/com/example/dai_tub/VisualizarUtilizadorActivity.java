package com.example.dai_tub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VisualizarUtilizadorActivity extends AppCompatActivity {

    DatabaseReference usersRef;
    LinearLayout usersContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_utilizador);

        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        usersContainer = findViewById(R.id.usersContainer);

        // Carregar os utentes da base de dados Firebase
        carregarUtentesFirebase();
    }

    private void carregarUtentesFirebase() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersContainer.removeAllViews();  // Limpa os utentes antigos antes de carregar os novos
                if (dataSnapshot.exists()) {
                    int count = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (count >= 6) break;
                        String nome = snapshot.child("nome").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        Long nif = snapshot.child("nif").getValue(Long.class);
                        Long numeroPasse = snapshot.child("numeroPasse").getValue(Long.class);
                        Double saldo = snapshot.child("saldo").getValue(Double.class);
                        String userId = snapshot.child("userId").getValue(String.class);
                        Long viagensCompradas = snapshot.child("viagensCompradas").getValue(Long.class);

                        if (nome != null && email != null) {
                            View utenteView = getLayoutInflater().inflate(R.layout.utilizador_item, null);

                            TextView nomeTextView = utenteView.findViewById(R.id.nomeUtenteTextView);
                            TextView emailTextView = utenteView.findViewById(R.id.emailUtenteTextView);
                            TextView nifTextView = utenteView.findViewById(R.id.nifUtenteTextView);
                            TextView numeroPasseTextView = utenteView.findViewById(R.id.numeroPasseUtenteTextView);
                            TextView saldoTextView = utenteView.findViewById(R.id.saldoUtenteTextView);
                            TextView userIdTextView = utenteView.findViewById(R.id.idUtenteTextView);
                            TextView viagensCompradasTextView = utenteView.findViewById(R.id.viagensCompradasUtenteTextView);

                            // Adiciona rótulos aos dados
                            nomeTextView.setText("Nome: " + nome);
                            emailTextView.setText("Email: " + email);
                            if (nif != null) {
                                nifTextView.setText("NIF: " + nif);
                            }
                            if (numeroPasse != null) {
                                numeroPasseTextView.setText("Número Passe: " + numeroPasse);
                            }
                            if (saldo != null) {
                                saldoTextView.setText("Saldo: " + saldo);
                            }
                            if (userId != null) {
                                userIdTextView.setText("ID do Usuário: " + userId);
                            }
                            if (viagensCompradas != null) {
                                viagensCompradasTextView.setText("Viagens Compradas: " + viagensCompradas);
                            }

                            usersContainer.addView(utenteView);
                            count++;
                        } else {
                            Log.e("VisualizarUtilizadorActivity", "Utente com dados incompletos ou nulos encontrado.");
                        }
                    }
                } else {
                    Log.d("VisualizarUtilizadorActivity", "Nenhum utente encontrado na base de dados.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("VisualizarUtilizadorActivity", "Erro ao carregar os utentes: " + databaseError.getMessage());
            }
        });
    }

}