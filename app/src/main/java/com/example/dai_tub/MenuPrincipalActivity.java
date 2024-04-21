package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
                // Encontre o layout do menu overlay
                LinearLayout menuOverlay = findViewById(R.id.menuOverlay);

                // Alterne a visibilidade do menu overlay entre visível e invisível
                if (menuOverlay.getVisibility() == View.VISIBLE) {
                    menuOverlay.setVisibility(View.GONE);
                } else {
                    menuOverlay.setVisibility(View.VISIBLE);
                }
            }
        });

        // Configuração do botão "Perfil"
        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir a atividade do perfil quando o botão "Perfil" for clicado
                startActivity(new Intent(MenuPrincipalActivity.this, PerfilActivity.class));
            }
        });

        // Configuração do botão "Comprar Bilhetes"
        Button buyTicketButton = findViewById(R.id.buyTicketButton);
        buyTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação do botão "Comprar Bilhetes"
                // Por exemplo, você pode abrir a atividade de comprar viagens
                startActivity(new Intent(MenuPrincipalActivity.this, ComprarViagensActivity.class));
            }
        });

        // Configuração do botão "Carregar Saldo"
        Button loadBalanceButton = findViewById(R.id.loadBalanceButton);
        loadBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação do botão "Carregar Saldo"
                // Por exemplo, você pode abrir a atividade para carregar saldo
                startActivity(new Intent(MenuPrincipalActivity.this, CarregarSaldoActivity.class));
            }
        });

        // Configuração dos outros botões...
    }
}
