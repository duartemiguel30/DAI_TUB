package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ComprarViagensActivity extends AppCompatActivity {

    // Variáveis para manter o estado das opções selecionadas
    private RelativeLayout multibancoLayout;
    private RelativeLayout mbwayLayout;
    private RelativeLayout paypalLayout;
    private RelativeLayout saldoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comprarviagens);

        // Inicializar as variáveis com as opções de pagamento
        multibancoLayout = findViewById(R.id.multibancoLayout);
        mbwayLayout = findViewById(R.id.mbwayLayout);
        paypalLayout = findViewById(R.id.paypalLayout);
        saldoLayout = findViewById(R.id.saldoLayout);

        // Configurar o layout e a lógica para a atividade de compra de viagens

        // Exemplo de como lidar com o clique do botão "Prosseguir"
        Button continueButton = findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicie a atividade de rotas quando o botão "Prosseguir" for clicado
                Intent intent = new Intent(ComprarViagensActivity.this, RotasActivity.class);
                startActivity(intent);
            }
        });
    }

    // Métodos para lidar com o clique das opções de pagamento
    public void onMultibancoClick(View view) {
        // Desmarcar todas as opções e sombrear apenas a opção Multibanco
        desmarcarTodasOpcoes();
        multibancoLayout.setBackgroundResource(R.drawable.selected_backgroud);
    }

    public void onMbwayClick(View view) {
        // Desmarcar todas as opções e sombrear apenas a opção MB Way
        desmarcarTodasOpcoes();
        mbwayLayout.setBackgroundResource(R.drawable.selected_backgroud);
    }

    public void onPayPalClick(View view) {
        // Desmarcar todas as opções e sombrear apenas a opção PayPal
        desmarcarTodasOpcoes();
        paypalLayout.setBackgroundResource(R.drawable.selected_backgroud);
    }

    public void onSaldoClick(View view) {
        // Desmarcar todas as opções e sombrear apenas a opção Saldo
        desmarcarTodasOpcoes();
        saldoLayout.setBackgroundResource(R.drawable.selected_backgroud);
    }

    // Método para desmarcar todas as opções e remover a sombra
    private void desmarcarTodasOpcoes() {
        multibancoLayout.setBackgroundResource(0);
        mbwayLayout.setBackgroundResource(0);
        paypalLayout.setBackgroundResource(0);
        saldoLayout.setBackgroundResource(0);
    }
}
