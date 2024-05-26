package com.example.dai_tub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuGerirNotificacoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_gerir_notificacoes);
    }

    public void adicionarNotificacao(View view) {
        Intent intent = new Intent(MenuGerirNotificacoesActivity.this, AdicionarNotificacoesActivity.class);
        startActivity(intent);
    }

    public void removerNotificacao(View view) {
        Intent intent = new Intent(MenuGerirNotificacoesActivity.this, RemoverNotificacoesActivity.class);
        startActivity(intent);
    }

    public void editarNotificacao(View view) {
        Intent intent = new Intent(MenuGerirNotificacoesActivity.this, EditarNotificacoesActivity.class);
        startActivity(intent);
    }

    public void listarNotificacoes(View view) {
        Intent intent = new Intent(MenuGerirNotificacoesActivity.this, ListarNotificacoesActivity.class);
        startActivity(intent);
    }
}
