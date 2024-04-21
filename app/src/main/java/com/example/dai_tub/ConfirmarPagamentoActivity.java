package com.example.dai_tub;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// Importações omitidas por brevidade

public class ConfirmarPagamentoActivity extends AppCompatActivity {

    private ImageView qrCodeImageView;

    // Referência ao banco de dados Firebase
    private DatabaseReference bilhetesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pagamento);

        qrCodeImageView = findViewById(R.id.qrCodeImageView);

        // Inicializar a referência ao banco de dados Firebase
        bilhetesRef = FirebaseDatabase.getInstance().getReference().child("bilhetes");

        // Obter o ID do bilhete dos extras da Intent
        String bilheteId = getIntent().getStringExtra("bilheteId");

        // Recuperar os detalhes do bilhete associados a esse ID do banco de dados Firebase
        if (bilheteId != null) {
            bilhetesRef.child(bilheteId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Bilhete bilhete = snapshot.getValue(Bilhete.class);
                    if (bilhete != null) {
                        // Obtenha o nome do usuário associado ao bilhete
                        String nomeUsuario = bilhete.getNomeUsuario();

                        // Chame o método gerarCodigoQR com os detalhes do bilhete e o nome do usuário
                        gerarCodigoQR(bilhete, nomeUsuario);
                    } else {
                        Toast.makeText(ConfirmarPagamentoActivity.this, "Não foi possível encontrar os detalhes do bilhete", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ConfirmarPagamentoActivity.this, "Erro ao recuperar os detalhes do bilhete: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Não foi possível obter o ID do bilhete", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para gerar o código QR com base nos detalhes do bilhete
    private void gerarCodigoQR(Bilhete bilhete, String nomeUsuario) {
        try {
            // Montar os dados do bilhete para exibir no código QR
            String dadosBilhete = "Nome do Usuário: " + nomeUsuario + "\n" +
                    "Data de Compra: " + bilhete.getDataCompra() + "\n" +
                    "Validade: " + bilhete.getValidade() + "\n" +
                    "Ponto de Partida: " + bilhete.getPontoPartida() + "\n" +
                    "Ponto de Chegada: " + bilhete.getPontoChegada();

            // Configurar o tamanho do código QR
            int width = 1000;
            int height = 1000;

            // Criar um BarcodeEncoder e gerar o código QR
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(dadosBilhete, BarcodeFormat.QR_CODE, width, height);

            // Exibir o código QR na ImageView
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao gerar o código QR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
