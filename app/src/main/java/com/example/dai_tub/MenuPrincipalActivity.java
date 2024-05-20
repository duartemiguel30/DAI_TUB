
package com.example.dai_tub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MenuPrincipalActivity extends AppCompatActivity {

    DatabaseReference noticiasRef; // Referência para o nó "noticias" no Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprincipal);

        // Inicializar a referência para o nó "noticias" no Realtime Database
        noticiasRef = FirebaseDatabase.getInstance().getReference().child("noticias");

        // Adicionando as notícias diretamente no banco de dados Firebase
        adicionarNoticiasFirebase();

        // Configuração do clique no texto do menu para abrir/fechar o menu
        TextView menuText = findViewById(R.id.menuText);
        menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout menuOverlay = findViewById(R.id.menuOverlay);
                if (menuOverlay.getVisibility() == View.VISIBLE) {
                    menuOverlay.setVisibility(View.GONE);
                } else {
                    menuOverlay.setVisibility(View.VISIBLE);
                }
            }
        });

        // Configuração do botão de pesquisa
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtendo o texto da barra de pesquisa
                EditText searchEditText = findViewById(R.id.searchEditText);
                String searchQuery = searchEditText.getText().toString().trim();

                // Verifica se o texto da pesquisa não está vazio
                if (!searchQuery.isEmpty()) {
                    // Adicione a log para verificar o texto da pesquisa
                    Log.d("MenuPrincipalActivity", "Texto da pesquisa: " + searchQuery);

                    // Abrindo a RotasActivity com a consulta de pesquisa
                    Intent intent = new Intent(MenuPrincipalActivity.this, RotasActivity.class);
                    intent.putExtra("searchQuery", searchQuery); // Passando o texto da pesquisa como extra
                    startActivity(intent);
                } else {
                    // Se o texto da pesquisa estiver vazio, exiba uma mensagem de erro
                    Toast.makeText(MenuPrincipalActivity.this, "Por favor, insira um texto de pesquisa", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Carregar as notícias da base de dados Firebase
        carregarNoticiasFirebase();
    }

    // Método para adicionar as notícias diretamente no banco de dados Firebase
    private void adicionarNoticiasFirebase() {
        // Notícia 1
        String tituloNoticia1 = "Novo lançamento: Smartphone XYZ";
        String textoNoticia1 = "A empresa XYZ acaba de lançar seu mais recente smartphone, com uma tela de alta resolução, câmera de última geração e desempenho excepcional.";
        int imagemResource1 = R.drawable.ic_profile; // Obtém o recurso da imagem ic_profile

        // Notícia 2
        String tituloNoticia2 = "Evento de lançamento da nova coleção";
        String textoNoticia2 = "Participe do evento de lançamento da nova coleção da marca ABC. Serão apresentadas as últimas tendências da moda e descontos exclusivos para os participantes.";
        int imagemResource2 = R.drawable.ic_paypal; // Obtém o recurso da imagem ic_paypal

        // Salvando as notícias no banco de dados Firebase
        noticiasRef.child("noticia1").child("titulo").setValue(tituloNoticia1);
        noticiasRef.child("noticia1").child("texto").setValue(textoNoticia1);
        noticiasRef.child("noticia1").child("imagemResource").setValue(imagemResource1); // Salva o recurso da imagem

        noticiasRef.child("noticia2").child("titulo").setValue(tituloNoticia2);
        noticiasRef.child("noticia2").child("texto").setValue(textoNoticia2);
        noticiasRef.child("noticia2").child("imagemResource").setValue(imagemResource2); // Salva o recurso da imagem
    }

    // Método para carregar as notícias da base de dados Firebase
    private void carregarNoticiasFirebase() {
        // Adicionar um listener para ler os dados da base de dados
        noticiasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Verificar se existem notícias na base de dados
                if (dataSnapshot.exists()) {
                    // Iterar sobre os filhos do nó "noticias" (noticia1 e noticia2)
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Obter os valores das notícias
                        String titulo = snapshot.child("titulo").getValue(String.class);
                        String texto = snapshot.child("texto").getValue(String.class);
                        int imagemResource = snapshot.child("imagemResource").getValue(Integer.class); // Obtém o recurso da imagem

                        // Exibir as notícias na UI
                        if (snapshot.getKey().equals("noticia1")) {
                            // Atualizar os elementos de UI com os dados da notícia 1
                            TextView tituloNoticia1TextView = findViewById(R.id.tituloNoticia1TextView);
                            tituloNoticia1TextView.setText(titulo);

                            TextView textoNoticia1TextView = findViewById(R.id.textoNoticia1TextView);
                            textoNoticia1TextView.setText(texto);

                            // Carregar a imagem e definir na ImageView
                            ImageView imagemNoticia1ImageView = findViewById(R.id.imagemNoticia1ImageView);
                            Drawable drawable = getResources().getDrawable(imagemResource); // Obtém o recurso da imagem
                            imagemNoticia1ImageView.setImageDrawable(drawable);
                        } else if (snapshot.getKey().equals("noticia2")) {
                            // Atualizar os elementos de UI com os dados da notícia 2
                            TextView tituloNoticia2TextView = findViewById(R.id.tituloNoticia2TextView);
                            tituloNoticia2TextView.setText(titulo);

                            TextView textoNoticia2TextView = findViewById(R.id.textoNoticia2TextView);
                            textoNoticia2TextView.setText(texto);

                            // Carregar a imagem e definir na ImageView
                            ImageView imagemNoticia2ImageView = findViewById(R.id.imagemNoticia2ImageView);
                            Drawable drawable = getResources().getDrawable(imagemResource); // Obtém o recurso da imagem
                            imagemNoticia2ImageView.setImageDrawable(drawable);
                        }
                    }
                } else {
                    Log.d("MenuPrincipalActivity", "Nenhuma notícia encontrada na base de dados.");
                }
            }

            @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                // Lidar com erro de leitura da base de dados, se necessário
                Log.e("MenuPrincipalActivity", "Erro ao carregar as notícias: " + databaseError.getMessage());
            }
        });
    }
}
