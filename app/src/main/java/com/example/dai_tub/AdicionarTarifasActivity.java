package com.example.dai_tub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdicionarTarifasActivity extends AppCompatActivity {

    private EditText editValor, editNome;
    private DatabaseReference tarifasRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_tarifas);

        // Inicializa os EditTexts
        editValor = findViewById(R.id.edit_text_valor_tarifa);
        editNome = findViewById(R.id.edit_text_nome_tarifa);

        // Inicializa a referência ao nó 'tarifas' no Firebase Realtime Database
        tarifasRef = FirebaseDatabase.getInstance().getReference().child("tarifas");

        // Botão para confirmar nova tarifa
        Button confirmar_nova_tarifa = findViewById(R.id.confirmar_nova_tarifa);
        confirmar_nova_tarifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarNovaTarifa();
            }
        });
    }

    private void adicionarNovaTarifa() {
        String valor = editValor.getText().toString();
        String nome = editNome.getText().toString();

        // Verifica se os campos estão preenchidos
        if (valor.isEmpty() || nome.isEmpty()) {
            // Se algum campo estiver vazio, exibe uma mensagem de erro
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria um novo nó com um ID único gerado automaticamente
        DatabaseReference novaTarifaRef = tarifasRef.push();

        // Define os valores da nova tarifa
        novaTarifaRef.child("valor").setValue(valor);
        novaTarifaRef.child("nome").setValue(nome).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Exibe uma mensagem de sucesso
                Toast.makeText(AdicionarTarifasActivity.this, "Tarifa adicionada com sucesso!", Toast.LENGTH_SHORT).show();

                // Limpa os campos após adicionar a tarifa
                editValor.setText("");
                editNome.setText("");
            } else {
                // Exibe uma mensagem de erro
                Toast.makeText(AdicionarTarifasActivity.this, "Erro ao adicionar a tarifa. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}