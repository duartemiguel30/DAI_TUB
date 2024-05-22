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

public class RemoverTarifasActivity extends AppCompatActivity {

    private EditText editNomeTarifaRemover, editValorTarifaRemover;
    private DatabaseReference tarifasRef;

    private static final String TAG = "RemoverTarifasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remover_tarifas);

        // Inicializa os EditTexts
        editNomeTarifaRemover = findViewById(R.id.edit_text_nome_tarifa_remover);
        editValorTarifaRemover = findViewById(R.id.edit_text_valor_tarifa_remover);

        // Inicializa a referência ao nó 'tarifas' no Firebase Realtime Database
        tarifasRef = FirebaseDatabase.getInstance().getReference().child("tarifas");

        // Botão para confirmar remoção de tarifa
        Button confirmar_remover_tarifa = findViewById(R.id.confirmar_remover_tarifa);
        confirmar_remover_tarifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerTarifa();
            }
        });
    }

    private void removerTarifa() {
        String nomeTarifaRemover = editNomeTarifaRemover.getText().toString();
        String valorTarifaRemover = editValorTarifaRemover.getText().toString();

        // Verifica se os campos estão preenchidos
        if (nomeTarifaRemover.isEmpty() || valorTarifaRemover.isEmpty()) {
            // Se algum campo estiver vazio, exibe uma mensagem de erro
            Toast.makeText(this, "Por favor, preencha o nome e o valor da tarifa.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Procura o nó da tarifa com o nome e valor fornecidos
        tarifasRef.orderByChild("nome").equalTo(nomeTarifaRemover).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean tarifaEncontrada = false;
                if (dataSnapshot.exists()) {
                    // Percorre os filhos correspondentes
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String valor = snapshot.child("valor").getValue(String.class);
                        if (valor != null && valor.equals(valorTarifaRemover)) {
                            // Remove o nó encontrado
                            snapshot.getRef().removeValue().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Exibe uma mensagem de sucesso
                                    Toast.makeText(RemoverTarifasActivity.this, "Tarifa removida com sucesso!", Toast.LENGTH_SHORT).show();
                                    // Limpa os campos após remover a tarifa
                                    editNomeTarifaRemover.setText("");
                                    editValorTarifaRemover.setText("");
                                } else {
                                    // Exibe uma mensagem de erro
                                    Toast.makeText(RemoverTarifasActivity.this, "Erro ao remover a tarifa. Tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            tarifaEncontrada = true;
                            break; // Para de procurar após encontrar a tarifa
                        }
                    }
                }
                if (!tarifaEncontrada) {
                    // Exibe uma mensagem de erro se a tarifa não for encontrada
                    Toast.makeText(RemoverTarifasActivity.this, "Tarifa não encontrada.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Exibe uma mensagem de erro se a consulta for cancelada
                Toast.makeText(RemoverTarifasActivity.this, "Erro ao procurar tarifa. Tente novamente.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Erro ao procurar tarifa", databaseError.toException());
            }
        });
    }
}