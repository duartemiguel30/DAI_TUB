package com.example.dai_tub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        // Referências aos elementos do layout
        TextView greetingText = findViewById(R.id.greetingText);
        TextView fullNameText = findViewById(R.id.fullNameText);
        TextView emailText = findViewById(R.id.emailText);
        TextView nifText = findViewById(R.id.nifText);
        TextView passNumberText = findViewById(R.id.passNumberText);
        TextView balanceText = findViewById(R.id.balanceText);
        TextView backButton = findViewById(R.id.backButton);

        // Obtendo a referência do usuário atual do Firebase
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Referência ao nó do usuário no banco de dados do Firebase
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

            // Adicionando um listener para buscar os dados do usuário
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Obtendo os dados do usuário do snapshot
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            // Definindo os valores nos elementos TextView
                            greetingText.setText(getString(R.string.greeting_text, user.getName()));
                            fullNameText.setText(user.getName());
                            emailText.setText(user.getEmail());
                            nifText.setText(user.getNif());

                            // Verificar se o número de passe está presente e não está vazio
                            if (user.getNumeroPasse() != null && !user.getNumeroPasse().isEmpty()) {
                                // Se o número de passe estiver preenchido, exibe-o
                                passNumberText.setText(user.getNumeroPasse());
                            }
                            // Torna o passNumberText visível independente do número de passe estar presente
                            passNumberText.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.d("PerfilActivity", "No such user exists in database");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("PerfilActivity", "Error retrieving user data: " + databaseError.getMessage());
                }
            });
        } else {
            Log.e("PerfilActivity", "Current user is null");
        }

        // Adicionando um listener de clique ao botão BACK
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chame onBackPressed para voltar à atividade anterior
                onBackPressed();
            }
        });
    }
}
