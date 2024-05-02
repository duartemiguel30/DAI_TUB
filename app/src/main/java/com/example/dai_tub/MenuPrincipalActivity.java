package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprincipal);

        // Configuração do clique no texto do menu para abrir/fechar o menu
        TextView menuText = findViewById(R.id.menuText);
        menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout menuOverlay = findViewById(R.id.menuOverlay);
                if (menuOverlay.getVisibility() == View.VISIBLE) {
                    menuOverlay.setVisibility(View.GONE);
                } else {
                    menuOverlay.setVisibility(View.VISIBLE);
                }
            }
        });

        // Configuração do botão para abrir a atividade MeusBilhetesActivity
        Button myTicketsButton = findViewById(R.id.menuItemMyTickets);
        myTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipalActivity.this, MeusBilhetesActivity.class));
            }
        });

        // Configuração do botão para abrir a atividade PerfilActivity
        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipalActivity.this, PerfilActivity.class));
            }
        });

        // Configuração do botão para abrir a atividade ComprarViagensActivity
        Button buyTicketButton = findViewById(R.id.buyTicketButton);
        buyTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipalActivity.this, ComprarViagensActivity.class));
            }
        });

        // Configuração do botão para abrir a atividade CarregarSaldoActivity
        Button loadBalanceButton = findViewById(R.id.loadBalanceButton);
        loadBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipalActivity.this, CarregarSaldoActivity.class));
            }
        });

        // Configuração do botão de pesquisa
        // Configuração do botão de pesquisa
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtendo a consulta de pesquisa da barra de pesquisa
                EditText searchEditText = findViewById(R.id.searchEditText);
                String searchQuery = searchEditText.getText().toString().trim();

                // Abrindo a RotasActivity com a consulta de pesquisa
                Intent intent = new Intent(MenuPrincipalActivity.this, RotasActivity.class);
                intent.putExtra("searchQuery", searchQuery);
                startActivity(intent);
            }
        });

    }    }