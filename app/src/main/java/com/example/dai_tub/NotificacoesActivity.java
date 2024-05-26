package com.example.dai_tub;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class NotificacoesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CustomNotificacaoAdapter mAdapter;
    private List<CustomNotificacao> mNotificacoesList;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);

        mRecyclerView = findViewById(R.id.recyclerViewNotificacoes);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNotificacoesList = new ArrayList<>();
        mAdapter = new CustomNotificacaoAdapter(mNotificacoesList);
        mRecyclerView.setAdapter(mAdapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("notificacoes");
        carregarNotificacoesFirebase();
    }

    private void carregarNotificacoesFirebase() {
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String titulo = snapshot.child("titulo").getValue(String.class);
                    String texto = snapshot.child("mensagem").getValue(String.class);

                    if (titulo != null && texto != null) {
                        CustomNotificacao notificacao = new CustomNotificacao(titulo, texto);
                        mNotificacoesList.add(notificacao);
                    }
                }

                if (!mNotificacoesList.isEmpty()) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Log.e("NotificacoesActivity", "Erro no banco de dados: " + databaseError.getMessage());
            }
        });
    }
}
