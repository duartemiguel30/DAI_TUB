package com.example.dai_tub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdicionarNotificacoesActivity extends AppCompatActivity {

    private EditText editTitulo, editMensagem;
    private DatabaseReference notificacoesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_notificacao);


        editTitulo = findViewById(R.id.edit_text_titulo_notificacao);
        editMensagem = findViewById(R.id.edit_text_mensagem_notificacao);


        notificacoesRef = FirebaseDatabase.getInstance().getReference().child("notificacoes");


        Button confirmar_nova_notificacao = findViewById(R.id.confirmar_nova_notificacao);
        confirmar_nova_notificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarNovaNotificacao();
            }
        });
    }

    private void adicionarNovaNotificacao() {
        String titulo = editTitulo.getText().toString();
        String mensagem = editMensagem.getText().toString();


        if (titulo.isEmpty() || mensagem.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference novaNotificacaoRef = notificacoesRef.push();

        novaNotificacaoRef.child("titulo").setValue(titulo);
        novaNotificacaoRef.child("mensagem").setValue(mensagem).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AdicionarNotificacoesActivity.this, "Notificação adicionada com sucesso!", Toast.LENGTH_SHORT).show();

                editTitulo.setText("");
                editMensagem.setText("");
            } else {
                Toast.makeText(AdicionarNotificacoesActivity.this, "Erro ao adicionar a notificação. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}