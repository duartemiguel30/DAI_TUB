package com.example.dai_tub;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;


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

                        // Gerar o PDF com as informações do bilhete e o código QR
                        gerarPDF(bilhete);
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

    // Método para exibir mensagens Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
            showToast("Erro ao gerar o código QR: " + e.getMessage());
        }
    }

    // Método para gerar o PDF com as informações do bilhete e o código QR
    private void gerarPDF(Bilhete bilhete) {
        Bitmap qrCodeBitmap = ((BitmapDrawable) qrCodeImageView.getDrawable()).getBitmap();
        String fileName = "bilhete.pdf";
        String filePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + fileName;
        generatePDF.generatePDF(this, bilhete, qrCodeBitmap);
    }

}
