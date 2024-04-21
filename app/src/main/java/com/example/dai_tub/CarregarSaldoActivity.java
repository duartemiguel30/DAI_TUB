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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CarregarSaldoActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carregarsaldo);

        // Inicialização do Firebase Authentication
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Obtenha a referência do usuário no Realtime Database
            String userId = currentUser.getUid();
            userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        } else {
            // Se o usuário não estiver autenticado, encerre a atividade
            finish();
        }
    }

    public void onLoadBalanceClick(View view) {
        // Obtenha o valor do saldo do EditText
        EditText saldoEditText = findViewById(R.id.loadBalanceEditText);
        String saldoStr = saldoEditText.getText().toString();

        if (!saldoStr.isEmpty()) {
            // Converta o saldo para inteiro
            int novoSaldo = Integer.parseInt(saldoStr);

            if (novoSaldo > 0) {
                // Salve o novo saldo no Firebase Realtime Database
                salvarSaldo(novoSaldo);
            } else {
                showToast("Insira um valor de saldo válido (maior que zero).");
            }
        } else {
            showToast("Insira um valor de saldo válido.");
        }
    }

    private void salvarSaldo(final int novoSaldo) {
        // Atualize o saldo no Realtime Database
        userDatabaseRef.child("saldo").setValue(novoSaldo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showToast("Saldo atualizado com sucesso!");
                        // Redirecione para a página de perfil
                        Intent intent = new Intent(CarregarSaldoActivity.this, PerfilActivity.class);
                        startActivity(intent);
                        finish(); // Finaliza a atividade atual
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Erro ao atualizar o saldo no Realtime Database.");
                    }
                });
    }

    // Método para exibir uma mensagem de toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
