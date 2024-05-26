package com.example.dai_tub;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RemoverNotificacoesActivity extends AppCompatActivity {

    private Spinner spinnerNotificacoes;
    private EditText editMensagemNotificacaoRemover;
    private DatabaseReference notificacoesRef;
    private ArrayList<String> notificacoesList;
    private ArrayList<String> notificacoesIds;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remover_notificacao);

        spinnerNotificacoes = findViewById(R.id.spinner_notificacoes);
        editMensagemNotificacaoRemover = findViewById(R.id.edit_text_mensagem_notificacao_remover);

        notificacoesRef = FirebaseDatabase.getInstance().getReference().child("notificacoes");

        notificacoesList = new ArrayList<>();
        notificacoesIds = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notificacoesList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotificacoes.setAdapter(spinnerAdapter);

        spinnerNotificacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mensagem = notificacoesList.get(position);
                editMensagemNotificacaoRemover.setText(mensagem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button confirmar_remover_notificacao = findViewById(R.id.confirmar_remover_notificacao);
        confirmar_remover_notificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerNotificacao();
            }
        });

        buscarNotificacoes();
    }

    private void buscarNotificacoes() {
        notificacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notificacoesList.clear();
                notificacoesIds.clear();
                for (DataSnapshot notificacaoSnapshot : dataSnapshot.getChildren()) {
                    String mensagem = notificacaoSnapshot.child("mensagem").getValue(String.class);
                    notificacoesList.add(mensagem);
                    notificacoesIds.add(notificacaoSnapshot.getKey());
                }
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RemoverNotificacoesActivity.this, "Erro ao carregar notificações.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removerNotificacao() {
        int selectedPosition = spinnerNotificacoes.getSelectedItemPosition();
        if (selectedPosition == AdapterView.INVALID_POSITION) {
            Toast.makeText(this, "Por favor, selecione uma notificação para remover.", Toast.LENGTH_SHORT).show();
            return;
        }

        String notificacaoId = notificacoesIds.get(selectedPosition);

        notificacoesRef.child(notificacaoId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(RemoverNotificacoesActivity.this, "Notificação removida com sucesso!", Toast.LENGTH_SHORT).show();
                editMensagemNotificacaoRemover.setText("");
            } else {
                Toast.makeText(RemoverNotificacoesActivity.this, "Erro ao remover a notificação. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}