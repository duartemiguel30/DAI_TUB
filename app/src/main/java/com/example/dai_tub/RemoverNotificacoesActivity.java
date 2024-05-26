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

        // Inicializa os componentes da UI
        spinnerNotificacoes = findViewById(R.id.spinner_notificacoes);
        editMensagemNotificacaoRemover = findViewById(R.id.edit_text_mensagem_notificacao_remover);

        // Inicializa a referência ao nó 'notificacoes' no Firebase Realtime Database
        notificacoesRef = FirebaseDatabase.getInstance().getReference().child("notificacoes");

        // Inicializa a lista de notificações e seus IDs
        notificacoesList = new ArrayList<>();
        notificacoesIds = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notificacoesList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotificacoes.setAdapter(spinnerAdapter);

        // Define o listener para quando um item do Spinner é selecionado
        spinnerNotificacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Ao selecionar uma notificação, exibe a mensagem dela no EditText
                String mensagem = notificacoesList.get(position);
                editMensagemNotificacaoRemover.setText(mensagem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nada a fazer aqui
            }
        });

        // Botão para confirmar remoção de notificação
        Button confirmar_remover_notificacao = findViewById(R.id.confirmar_remover_notificacao);
        confirmar_remover_notificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerNotificacao();
            }
        });

        // Busca as notificações no Firebase
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
                // Exibe uma mensagem de erro se a consulta for cancelada
                Toast.makeText(RemoverNotificacoesActivity.this, "Erro ao carregar notificações.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removerNotificacao() {
        // Obtém a posição selecionada no Spinner
        int selectedPosition = spinnerNotificacoes.getSelectedItemPosition();
        if (selectedPosition == AdapterView.INVALID_POSITION) {
            // Se nenhuma notificação for selecionada, exibe uma mensagem de erro
            Toast.makeText(this, "Por favor, selecione uma notificação para remover.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtém o ID da notificação selecionada
        String notificacaoId = notificacoesIds.get(selectedPosition);

        // Remove a notificação do Firebase Realtime Database
        notificacoesRef.child(notificacaoId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Exibe uma mensagem de sucesso
                Toast.makeText(RemoverNotificacoesActivity.this, "Notificação removida com sucesso!", Toast.LENGTH_SHORT).show();
                // Limpa o EditText após remover a notificação
                editMensagemNotificacaoRemover.setText("");
            } else {
                // Exibe uma mensagem de erro
                Toast.makeText(RemoverNotificacoesActivity.this, "Erro ao remover a notificação. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}