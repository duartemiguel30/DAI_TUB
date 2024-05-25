package com.example.dai_tub;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VisualizarTarifasActivity extends AppCompatActivity {

    DatabaseReference tarifasRef;
    LinearLayout tarifasContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_tarifas);

        tarifasRef = FirebaseDatabase.getInstance().getReference().child("tarifas");
        tarifasContainer = findViewById(R.id.tarifasContainer);
    }

    // Método chamado pelo botão no layout XML
    public void visualizarTarifas(View view) {
        tarifasContainer.removeAllViews(); // Limpar quaisquer tarifas existentes antes de carregar novas
        carregarTarifasFirebase();
    }

    private void carregarTarifasFirebase() {
        tarifasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String titulo = snapshot.child("titulo").getValue(String.class);
                        String descricao = snapshot.child("descricao").getValue(String.class);
                        int imagemResource = snapshot.child("imagemResource").getValue(Integer.class);

                        View tarifaView = getLayoutInflater().inflate(R.layout.tarifa_item, null);

                        TextView tituloTextView = tarifaView.findViewById(R.id.nomeTarifaTextView);
                        TextView descricaoTextView = tarifaView.findViewById(R.id.valorTarifaTextView);

                        tituloTextView.setText(titulo);
                        descricaoTextView.setText(descricao);
                        Drawable drawable = getResources().getDrawable(imagemResource);

                        tarifasContainer.addView(tarifaView);
                    }
                } else {
                    Log.d("TarifasActivity", "Nenhuma tarifa encontrada na base de dados.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TarifasActivity", "Erro ao carregar as tarifas: " + databaseError.getMessage());
            }
        });
    }
}