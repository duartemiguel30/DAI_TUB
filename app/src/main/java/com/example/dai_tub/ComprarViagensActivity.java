package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ComprarViagensActivity extends AppCompatActivity {

    private RelativeLayout multibancoLayout;
    private RelativeLayout mbwayLayout;
    private RelativeLayout paypalLayout;
    private RelativeLayout saldoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comprarviagens);

        multibancoLayout = findViewById(R.id.multibancoLayout);
        mbwayLayout = findViewById(R.id.mbwayLayout);
        paypalLayout = findViewById(R.id.paypalLayout);
        saldoLayout = findViewById(R.id.saldoLayout);



        // "prosseguir
        Button continueButton = findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComprarViagensActivity.this, RotasActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onMultibancoClick(View view) {
        desmarcarTodasOpcoes();
        multibancoLayout.setBackgroundResource(R.drawable.selected_backgroud);
    }

    public void onMbwayClick(View view) {
        desmarcarTodasOpcoes();
        mbwayLayout.setBackgroundResource(R.drawable.selected_backgroud);
    }

    public void onPayPalClick(View view) {
        desmarcarTodasOpcoes();
        paypalLayout.setBackgroundResource(R.drawable.selected_backgroud);
    }

    public void onSaldoClick(View view) {
        desmarcarTodasOpcoes();
        saldoLayout.setBackgroundResource(R.drawable.selected_backgroud);
    }

    private void desmarcarTodasOpcoes() {
        multibancoLayout.setBackgroundResource(0);
        mbwayLayout.setBackgroundResource(0);
        paypalLayout.setBackgroundResource(0);
        saldoLayout.setBackgroundResource(0);
    }
}
