package com.example.dai_tub;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ConfirmarPagamentoActivity extends AppCompatActivity {

    private static final String TAG = "ConfirmarPagamento";

    private ImageView qrCodeImageView;
    private DatabaseReference bilhetesRef;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pagamento);

        qrCodeImageView = findViewById(R.id.qrCodeImageView);
        bilhetesRef = FirebaseDatabase.getInstance().getReference().child("bilhetes");
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        String bilheteId = getIntent().getStringExtra("bilheteId");

        if (bilheteId != null) {
            bilhetesRef.child(bilheteId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Bilhete bilhete = snapshot.getValue(Bilhete.class);
                    if (bilhete != null) {
                        String nomeUsuario = bilhete.getNomeUsuario();
                        gerarCodigoQR(bilhete, nomeUsuario);
                        confirmarPagamento(bilhete.getUserId(), bilhete);
                    } else {
                        showToast("Não foi possível encontrar os detalhes do bilhete");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    showToast("Erro ao recuperar os detalhes do bilhete: " + error.getMessage());
                }
            });
        } else {
            showToast("Não foi possível obter o ID do bilhete");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void gerarCodigoQR(Bilhete bilhete, String nomeUsuario) {
        try {
            String dadosBilhete = "Nome do Usuário: " + nomeUsuario + "\n" +
                    "Data de Compra: " + bilhete.getDataCompra() + "\n" +
                    "Validade: " + bilhete.getValidade() + "\n" +
                    "Ponto de Partida: " + bilhete.getPontoPartida() + "\n" +
                    "Ponto de Chegada: " + bilhete.getPontoChegada();
            int width = 1000;
            int height = 1000;
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(dadosBilhete, BarcodeFormat.QR_CODE, width, height);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            showToast("Erro ao gerar o código QR: " + e.getMessage());
        }
    }

    private void confirmarPagamento(String userId, Bilhete bilhete) {
        Log.d(TAG, "Iniciando confirmação de pagamento para o usuário: " + userId);
        if (userId != null) {
            usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Acessando os dados específicos do usuário
                        String userEmail = snapshot.child("email").getValue(String.class);
                        String userName = snapshot.child("name").getValue(String.class);
                        Double userSaldo = snapshot.child("saldo").getValue(Double.class);
                        Long viagensCompradasValue = snapshot.child("viagensCompradas").getValue(Long.class);
                        long viagensCompradas = (viagensCompradasValue == null) ? 0 : viagensCompradasValue;

                        if (userSaldo != null) {
                            double precoEstudante = bilhete.getRota().getPrecoEstudante();
                            Log.i(TAG, "Saldo atual do usuário: " + userSaldo);
                            Log.d(TAG, "Preço do bilhete para estudante: " + precoEstudante);
                            if (userSaldo >= precoEstudante) {
                                // Calculando o novo saldo
                                double novoSaldo = userSaldo - precoEstudante;
                                Log.d(TAG, "Novo saldo do usuário após o pagamento: " + novoSaldo);
                                // Atualizando o saldo do usuário no Firebase
                                usersRef.child(userId).child("saldo").setValue(novoSaldo)
                                        .addOnSuccessListener(aVoid -> {
                                            showToast("Pagamento confirmado! Saldo atualizado.");
                                            Log.d(TAG, "Atualização do saldo bem-sucedida.");
                                            // Incrementar o número de viagens compradas
                                            usersRef.child(userId).child("viagensCompradas").setValue(viagensCompradas + 1)
                                                    .addOnSuccessListener(aVoid2 -> {
                                                        showToast("Número de viagens compradas atualizado.");
                                                        Log.d(TAG, "Viagens compradas atualizadas para: " + (viagensCompradas + 1));
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        showToast("Erro ao atualizar o número de viagens compradas: " + e.getMessage());
                                                        Log.e(TAG, "Erro ao atualizar o número de viagens compradas: ", e);
                                                    });
                                        })
                                        .addOnFailureListener(e -> {
                                            showToast("Erro ao atualizar o saldo: " + e.getMessage());
                                            Log.e(TAG, "Erro ao atualizar o saldo: ", e);
                                        });
                            } else {
                                showToast("Saldo insuficiente para pagar este bilhete.");
                            }
                        } else {
                            showToast("Saldo do usuário está armazenado de forma inadequada no banco de dados");
                        }
                    } else {
                        showToast("Usuário não encontrado no banco de dados.");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    showToast("Erro ao confirmar pagamento: " + error.getMessage());
                    Log.e(TAG, "Erro ao confirmar pagamento: ", error.toException());
                }
            });
        } else {
            showToast("ID do usuário não encontrado.");
            Log.e(TAG, "ID do usuário não encontrado.");
        }
    }
}
