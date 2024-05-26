
package com.example.dai_tub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditarTarifasActivity extends AppCompatActivity {

    private EditText editNomeTarifaAtual, editValorTarifaAtual, editNovoNomeTarifa, editNovoValorTarifa;
    private DatabaseReference tarifasRef;

    private static final String TAG = "EditarTarifasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_tarifas);

        editNomeTarifaAtual = findViewById(R.id.edit_text_nome_tarifa_atual);
        editValorTarifaAtual = findViewById(R.id.edit_text_valor_tarifa_atual);
        editNovoNomeTarifa = findViewById(R.id.edit_text_novo_nome_tarifa);
        editNovoValorTarifa = findViewById(R.id.edit_text_novo_valor_tarifa);

        tarifasRef = FirebaseDatabase.getInstance().getReference().child("tarifas");

        Button confirmar_editar_tarifa = findViewById(R.id.confirmar_editar_tarifa);
        confirmar_editar_tarifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarTarifa();
            }
        });
    }

    private void editarTarifa() {
        String nomeAtual = editNomeTarifaAtual.getText().toString();
        String valorAtual = editValorTarifaAtual.getText().toString();
        String novoNome = editNovoNomeTarifa.getText().toString();
        String novoValor = editNovoValorTarifa.getText().toString();

        if (nomeAtual.isEmpty() || valorAtual.isEmpty() || novoNome.isEmpty() || novoValor.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        tarifasRef.orderByChild("nome").equalTo(nomeAtual).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean tarifaEncontrada = false;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String valorDb = snapshot.child("valor").getValue(String.class);
                        if (valorAtual.equals(valorDb)) {
                            tarifaEncontrada = true;
                            snapshot.getRef().child("nome").setValue(novoNome);
                            snapshot.getRef().child("valor").setValue(novoValor).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EditarTarifasActivity.this, "Tarifa editada com sucesso!", Toast.LENGTH_SHORT).show();
                                    editNomeTarifaAtual.setText("");
                                    editValorTarifaAtual.setText("");
                                    editNovoNomeTarifa.setText("");
                                    editNovoValorTarifa.setText("");
                                } else {
                                    Toast.makeText(EditarTarifasActivity.this, "Erro ao editar a tarifa. Tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        }
                    }
                }
                if (!tarifaEncontrada) {
                    Toast.makeText(EditarTarifasActivity.this, "Tarifa não encontrada.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditarTarifasActivity.this, "Erro ao procurar tarifa. Tente novamente.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Erro ao procurar tarifa", databaseError.toException());
            }
        });
    }
}