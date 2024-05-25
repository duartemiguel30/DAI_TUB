package com.example.dai_tub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilActivity extends AppCompatActivity {

    private DatabaseReference userRef;
    private TextView balanceText;
    private TextView viagensCompradasText;
    private TextView viagensCompradasValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        TextView greetingText = findViewById(R.id.greetingText);
        TextView fullNameText = findViewById(R.id.fullNameText);
        TextView emailText = findViewById(R.id.emailText);
        TextView nifText = findViewById(R.id.nifText);
        TextView passNumberText = findViewById(R.id.passNumberText);
        balanceText = findViewById(R.id.balanceText);
        viagensCompradasText = findViewById(R.id.tripsNumberText);
        viagensCompradasValue = findViewById(R.id.tripsNumberText);

        if (greetingText == null || fullNameText == null || emailText == null || nifText == null || passNumberText == null || balanceText == null || viagensCompradasText == null || viagensCompradasValue == null) {
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            // Definindo os valores nos elementos TextView
                            greetingText.setText(getString(R.string.greeting_text, user.getName()));
                            fullNameText.setText(user.getName());
                            emailText.setText(user.getEmail());
                            nifText.setText(user.getNif());

                            if (user.getNumeroPasse() != null && !user.getNumeroPasse().isEmpty()) {
                                passNumberText.setText(user.getNumeroPasse());
                            }
                            passNumberText.setVisibility(View.VISIBLE);

                            displayBalanceFromDatabase();

                            displayViagensCompradasFromDatabase();
                        }
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

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void displayViagensCompradasFromDatabase() {
        userRef.child("viagensCompradas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long viagensCompradas = dataSnapshot.getValue(Long.class);
                    if (viagensCompradas != null) {
                        viagensCompradasText.setVisibility(View.VISIBLE);
                        viagensCompradasValue.setVisibility(View.VISIBLE);
                        viagensCompradasValue.setText(String.valueOf(viagensCompradas));
                    } else {
                        Log.d("PerfilActivity", "Viagens compradas não encontradas no banco de dados");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PerfilActivity", "Erro ao recuperar as viagens compradas: " + databaseError.getMessage());
            }
        });
    }


    private void displayBalanceFromDatabase() {
        userRef.child("saldo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Double saldoAtual = dataSnapshot.getValue(Double.class);
                    if (saldoAtual != null) {
                        balanceText.setText(String.valueOf(saldoAtual));
                    } else {
                        balanceText.setText("0.0"); // Se o saldo for nulo, exibe 0.0
                    }
                } else {
                    Log.d("PerfilActivity", "Saldo do utilizador não encontrado na base de dados");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PerfilActivity", "Erro: " + databaseError.getMessage());
            }
        });
    }
}
