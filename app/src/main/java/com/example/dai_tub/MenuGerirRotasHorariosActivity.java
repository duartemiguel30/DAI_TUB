package com.example.dai_tub;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuGerirRotasHorariosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_gerir_rotas_horarios);

        // Inicialize os botões e outros componentes de UI, se necessário
        Button adicionarRotasHorariosButton = findViewById(R.id.adicionar_rota_horarios);
        Button removerRotasHorariosButton = findViewById(R.id.removerRotasHorarios);
        Button editarRotasHorariosButton = findViewById(R.id.editarRotasHorarios);
        Button visualizarRotasHorariosButton = findViewById(R.id.visualizarRotasHorarios);

        // Defina os listeners de clique para os botões
        adicionarRotasHorariosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicie a nova atividade AdicionarRotaHorarioActivity
                Intent intent = new Intent(MenuGerirRotasHorariosActivity.this, AdicionarRotaHorarioActivity.class);
                startActivity(intent);
            }
        });

        removerRotasHorariosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuGerirRotasHorariosActivity.this, RemoverRotaHorarioActivity.class);
                startActivity(intent);
            }
        });

        editarRotasHorariosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implemente a lógica para editar rotas e horários
            }
        });

        visualizarRotasHorariosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicie a nova atividade VisualizarRotasHorariosActivity
                Intent intent = new Intent(MenuGerirRotasHorariosActivity.this, VisualizarRotasHorariosActivity.class);
                startActivity(intent);
            }
        });
    }

    // Implemente os métodos abaixo conforme necessário
    public void adicionarRotaHorarios(View view) {
    }

    public void removerRotasHorarios(View view) {
    }

    public void editarRotasHorarios(View view) {
    }

    public void VisualizarRotasHorarios(View view) {
    }
}