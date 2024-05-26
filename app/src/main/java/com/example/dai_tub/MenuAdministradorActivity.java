package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuAdministradorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paginainicialadministrador);

        Button gerir_faturacao = findViewById(R.id.gerir_tarifas);
        gerir_faturacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdministradorActivity.this, MenuGerirTarifasActivity.class);
                startActivity(intent);
            }
        });

        Button visualizar_utilizador = findViewById(R.id.visualizar_utilizador);
        visualizar_utilizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the VisualizarUtilizadorActivity
                Intent intent = new Intent(MenuAdministradorActivity.this, VisualizarUtilizadorActivity.class);
                startActivity(intent);
            }
        });

        // New button to manage routes and schedules
        Button gerirRotas = findViewById(R.id.gerirRotas);
        gerirRotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdministradorActivity.this, MenuGerirRotasHorariosActivity.class);
                startActivity(intent);
            }
        });

        Button gerir_notificacoes = findViewById(R.id.gerir_notificacoes);
        gerir_notificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar um intent para abrir a atividade da página 1
                Intent intent = new Intent(MenuAdministradorActivity.this, MenuGerirNotificacoesActivity.class);
                startActivity(intent);
            }
        });

    }
}