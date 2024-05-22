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
                // Criar um intent para abrir a atividade da página 1
                Intent intent = new Intent(MenuAdministradorActivity.this, MenuGerirTarifasActivity.class);
                startActivity(intent);
            }
        });
    }
}