package com.example.dai_tub;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarNotificacoesActivity extends AppCompatActivity {

    private ListView listViewNotificacoes;
    private DatabaseReference notificacoesRef;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notificacoesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_notificacao);

        listViewNotificacoes = findViewById(R.id.list_view_notificacoes);

        notificacoesRef = FirebaseDatabase.getInstance().getReference().child("notificacoes");
        notificacoesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notificacoesList);
        listViewNotificacoes.setAdapter(adapter);

        buscarNotificacoes();
    }

    private void buscarNotificacoes() {
        notificacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notificacoesList.clear();
                for (DataSnapshot notificacaoSnapshot : dataSnapshot.getChildren()) {
                    String titulo = notificacaoSnapshot.child("titulo").getValue(String.class);
                    String mensagem = notificacaoSnapshot.child("mensagem").getValue(String.class);
                    notificacoesList.add(titulo + ": " + mensagem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListarNotificacoesActivity.this, "Erro ao carregar notificações.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}