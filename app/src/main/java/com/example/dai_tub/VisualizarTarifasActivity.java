package com.example.dai_tub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        carregarTarifasFirebase();
    }

    private void carregarTarifasFirebase() {
        tarifasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tarifasContainer.removeAllViews();
                if (dataSnapshot.exists()) {
                    int count = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (count >= 6) break;
                        String nome = snapshot.child("nome").getValue(String.class);
                        Object valorObject = snapshot.child("valor").getValue();

                        Double valor = null;
                        if (valorObject instanceof Double) {
                            valor = (Double) valorObject;
                        } else if (valorObject instanceof String) {
                            try {
                                valor = Double.parseDouble((String) valorObject);
                            } catch (NumberFormatException e) {
                                Log.e("VisualizarTarifasActivity", "Erro ao converter valor para Double: " + valorObject);
                            }
                        }

                        if (nome != null && valor != null) {
                            View tarifaView = getLayoutInflater().inflate(R.layout.tarifa_item, null);

                            TextView nomeTextView = tarifaView.findViewById(R.id.nomeTarifaTextView);
                            TextView valorTextView = tarifaView.findViewById(R.id.valorTarifaTextView);

                            nomeTextView.setText(nome);
                            valorTextView.setText(String.valueOf(valor));

                            tarifasContainer.addView(tarifaView);
                            count++;
                        } else {
                            Log.e("VisualizarTarifasActivity", "Tarifa com dados incompletos ou valor nulo encontrada.");
                        }
                    }
                } else {
                    Log.d("VisualizarTarifasActivity", "Nenhuma tarifa encontrada na base de dados.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("VisualizarTarifasActivity", "Erro ao carregar as tarifas: " + databaseError.getMessage());
            }
        });
    }
}