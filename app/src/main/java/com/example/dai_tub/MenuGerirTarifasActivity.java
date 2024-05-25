package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuGerirTarifasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gerir_tarifas);

        Button adicionar_tarifas = findViewById(R.id.adicionar_tarifas);
        adicionar_tarifas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar um intent para abrir a atividade da página 1
                Intent intent = new Intent(MenuGerirTarifasActivity.this, AdicionarTarifasActivity.class);
                startActivity(intent);
            }
        });

        Button remover_tarifas = findViewById(R.id.remover_tarifas);
        remover_tarifas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar um intent para abrir a atividade da página 1
                Intent intent = new Intent(MenuGerirTarifasActivity.this, RemoverTarifasActivity.class);
                startActivity(intent);
            }
        });


        Button editar_tarifas = findViewById(R.id.editar_tarifas);
        editar_tarifas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar um intent para abrir a atividade da página 1
                Intent intent = new Intent(MenuGerirTarifasActivity.this, EditarTarifasActivity.class);
                startActivity(intent);
            }
        });
        Button visualizar_tarifas = findViewById(R.id.visualizar_tarifas);
        visualizar_tarifas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar um intent para abrir a atividade de visualização de tarifas
                Intent intent = new Intent(MenuGerirTarifasActivity.this, VisualizarTarifasActivity.class);
                startActivity(intent);
            }
        });

        editar_tarifas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar um intent para abrir a atividade da página 1
                Intent intent = new Intent(MenuGerirTarifasActivity.this, VisualizarTarifasActivity.class);
                startActivity(intent);
            }
        });
    }

}