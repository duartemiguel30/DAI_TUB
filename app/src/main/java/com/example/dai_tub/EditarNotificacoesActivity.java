package com.example.dai_tub;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditarNotificacoesActivity extends AppCompatActivity {

    private Spinner spinnerNotificacoes;
    private EditText editTextTitulo, editTextMensagem;
    private Button confirmarEditarNotificacao;
    private DatabaseReference notificacoesRef;
    private ArrayList<String> notificacoesList;
    private ArrayList<String> notificacoesIds;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_notificacao);

        spinnerNotificacoes = findViewById(R.id.spinner_notificacoes);
        editTextTitulo = findViewById(R.id.edit_text_titulo_notificacao);
        editTextMensagem = findViewById(R.id.edit_text_mensagem_notificacao);
        confirmarEditarNotificacao = findViewById(R.id.confirmar_editar_notificacao);

        notificacoesRef = FirebaseDatabase.getInstance().getReference().child("notificacoes");
        notificacoesList = new ArrayList<>();
        notificacoesIds = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notificacoesList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotificacoes.setAdapter(spinnerAdapter);

        buscarNotificacoes();

        confirmarEditarNotificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarNotificacaoSelecionada();
            }
        });
    }

    private void buscarNotificacoes() {
        notificacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notificacoesList.clear();
                notificacoesIds.clear();
                for (DataSnapshot notificacaoSnapshot : dataSnapshot.getChildren()) {
                    String titulo = notificacaoSnapshot.child("titulo").getValue(String.class);
                    String mensagem = notificacaoSnapshot.child("mensagem").getValue(String.class);
                    notificacoesList.add(titulo + ": " + mensagem);
                    notificacoesIds.add(notificacaoSnapshot.getKey());
                }
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditarNotificacoesActivity.this, "Erro ao carregar notificações.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editarNotificacaoSelecionada() {
        int selectedPosition = spinnerNotificacoes.getSelectedItemPosition();
        if (selectedPosition == -1) {
            Toast.makeText(this, "Por favor, selecione uma notificação para editar.", Toast.LENGTH_SHORT).show();
            return;
        }

        String notificacaoId = notificacoesIds.get(selectedPosition);
        String novoTitulo = editTextTitulo.getText().toString();
        String novaMensagem = editTextMensagem.getText().toString();

        if (novoTitulo.isEmpty() || novaMensagem.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference notificacaoRef = notificacoesRef.child(notificacaoId);
        notificacaoRef.child("titulo").setValue(novoTitulo);
        notificacaoRef.child("mensagem").setValue(novaMensagem).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditarNotificacoesActivity.this, "Notificação editada com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditarNotificacoesActivity.this, "Erro ao editar a notificação. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}