package com.example.dai_tub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CarregarSaldoActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carregarsaldo);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        } else {
            finish();
        }
    }

    public void onMultibancoClick(View view) {
    }

    public void onMbwayClick(View view) {
    }

    public void onPayPalClick(View view) {
    }

    public void onLoadBalanceClick(View view) {
        EditText saldoEditText = findViewById(R.id.loadBalanceEditText);
        String saldoStr = saldoEditText.getText().toString();

        if (!saldoStr.isEmpty()) {
            int novoSaldo = Integer.parseInt(saldoStr);

            if (novoSaldo > 0) {
                salvarSaldo(novoSaldo);
            } else {
                showToast("Insira um valor de saldo válido (maior que zero).");
            }
        } else {
            showToast("Insira um valor de saldo válido.");
        }
    }

    private void salvarSaldo(final int novoSaldo) {
        Query query = userDatabaseRef.child("saldo");
        query.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int saldoAtual = 0;
                if (dataSnapshot.exists()) {
                    saldoAtual = dataSnapshot.getValue(Integer.class);
                }
                int saldoTotal = saldoAtual + novoSaldo;

                userDatabaseRef.child("saldo").setValue(saldoTotal)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                showToast("Saldo atualizado!");
                                // Redirecione para a página de perfil
                                Intent intent = new Intent(CarregarSaldoActivity.this, PerfilActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showToast("Erro ao atualizar o saldo.");
                            }
                        });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
