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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MenuPrincipalActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    DatabaseReference noticiasRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprincipal);


        mAuth = FirebaseAuth.getInstance();

        noticiasRef = FirebaseDatabase.getInstance().getReference().child("noticias");

        adicionarNoticiasFirebase();

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

        Button comprarBilhetesButton = findViewById(R.id.buyTicketButton);
        comprarBilhetesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, RotasActivity.class);
                startActivity(intent);
            }
        });

        Button noticiasButton = findViewById(R.id.menuItemNews);
        noticiasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, NoticiasActivity.class);
                startActivity(intent);
            }
        });

        Button meusBilhetesButton = findViewById(R.id.menuItemMyTickets);
        meusBilhetesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, MeusBilhetesActivity.class);
                startActivity(intent);
            }
        });

        Button carregarSaldoButton = findViewById(R.id.loadBalanceButton);
        carregarSaldoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, CarregarSaldoActivity.class);
                startActivity(intent);
            }
        });

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchEditText = findViewById(R.id.searchEditText);
                String searchQuery = searchEditText.getText().toString().trim();

                if (!searchQuery.isEmpty()) {

                    Intent intent = new Intent(MenuPrincipalActivity.this, RotasActivity.class);
                    intent.putExtra("searchQuery", searchQuery);
                    startActivity(intent);
                } else {
                    Toast.makeText(MenuPrincipalActivity.this, "Por favor, insira um texto de pesquisa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        Button validateTicketButton = findViewById(R.id.validateTicketButton);
        validateTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, MeusBilhetesActivity.class);
                startActivity(intent);
            }
        });

        Button menuItemRoutes = findViewById(R.id.menuItemRoutes);
        menuItemRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, RotasActivity.class);
                startActivity(intent);
            }
        });


        Button news2Button = findViewById(R.id.news2Button);
        news2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, NoticiasActivity.class);
                startActivity(intent);
            }
        });

        Button menuItemNotifications = findViewById(R.id.menuItemNotifications);
        menuItemNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, NotificacoesActivity.class);
                startActivity(intent);
            }
        });


        Button recompensasButton = findViewById(R.id.rewardsButton);
        recompensasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Intent intent = new Intent(MenuPrincipalActivity.this, RecompensaActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MenuPrincipalActivity.this, "Faça login para ter acesso às recompensas.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenuPrincipalActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


        carregarNoticiasFirebase();
    }

    private void adicionarNoticiasFirebase() {
        // Notícia 1
        String tituloNoticia1 = "Rampa da Falperra- Alteração percurso Linha 23";
        String textoNoticia1 = "Informam-se todos os clientes dos TUB que, devido à realização do evento Rampa da Falperra, nos dias 18 e 19 de maio, a linha 23 fará percurso alternativo. A partir da Av. da Liberdade, no sentido ida-volta, circula pela Av. João XXI, Av. João Paulo II, Estrada do Bom Jesus, Rua do Rio Mau, fazendo ponto de horário em Espinho Cruzamento.";
        int imagemResource1 = R.drawable.im_noticia1;

        // Notícia 2
        String tituloNoticia2 = "Novo local de paragem: Rua São Martinho (viaduto)";
        String textoNoticia2 = "Informam-se todos os clientes dos TUB que, a partir de hoje, 16 de maio, disponibilizamos aos nossos clientes, uma nova paragem na Rua de São Martinho, localizada debaixo do viaduto, para as linhas 5, 50, 52, 85, 94 e 949.\n" +
                "\n";
        int imagemResource2 = R.drawable.im_noticia2;

        // Notícia 3
        String tituloNoticia3 = "Transporte para o futebol: S.C.Braga - FC Porto, sábado, 18 maio, 20h30";
        String textoNoticia3 = "Partidas às 19:30 de:\tRegresso no final do jogo para:\n" +
                " \t \n" +
                "1 - S. Mamede d'Este (Junta de Freguesia)- 19:30\t1 - S. Mamede d'Este (Junta de Freguesia)\n" +
                "2 - Monsenhor Airosa - 19:15\t2 - Cruzamento de Esporões (Farmácia)\n" +
                "Cruz.to de Esporões (Farmácia) – 19:30\t3 - Santa Lucrécia de Algeriz\n" +
                "3 – Santa Lucrécia de Algeriz- 19:00\t4 – Cabreiros\n" +
                "4 – Cabreiros-19:30\t5 – Avenida da Liberdade\n" +
                "5 – Avenida Central IV– 19:45\t \n" +
                "Títulos Válidos:\t \n" +
                "Tarifário em Vigor\n" +
                "Bilhete Especial Estádio – 1,00€ (válido para ida e volta)\n" +
                "Importante: Conservar o título até ao final da viagem de regresso.\t ";
        int imagemResource3 = R.drawable.im_noticia3;

        // Notícia 4
        String tituloNoticia4 = "12 de abril (Dia Nacional do Ar)- Transporte gratuito em toda a rede dos TUB";
        String textoNoticia4 = "De Quarta-feira, 10 abril 2024 a Sexta-feira, 12 abril 2024";
        int imagemResource4 = R.drawable.im_noticia4;

        // Notícia 5
        String tituloNoticia5 = "Sexta-feira Santa- Horário Postos de Venda";
        String textoNoticia5 = "Informam-se os clientes dos TUB que no dia 29 de março de 2024 (feriado), estão em funcionamento os seguintes Pontos de Venda: Rechicho- 09h00 às 12h30 e 14h30 às 18h00 - TUB / Agentes (Payshop).";
        int imagemResource5 = R.drawable.im_noticia5;

        // Notícia 6
        String tituloNoticia6 = "23 e 24 de março- Transporte especial para a AGRO";
        String textoNoticia6 = "Nos dias 21 e 22 de março utilize as nossas linhas regulares e nos dias 23 e 24 (sábado e domingo) vá no nosso shutlle especial.\n" +
                "\n" +
                "Shuttle de 20 em 20 minutos, a partir do Eleclerc e Minho Center.\n" +
                "\n" +
                "Apresente ainda o seu bilhete TUB e usufrua de 50% do valor da entrada na AGRO.\n" +
                "\n" +
                "*oferta exclusiva apenas para os dias 23 e 24 de março.\n" +
                "\n";
        int imagemResource6 = R.drawable.im_noticia6;



        noticiasRef.child("noticia1").child("titulo").setValue(tituloNoticia1);
        noticiasRef.child("noticia1").child("texto").setValue(textoNoticia1);
        noticiasRef.child("noticia1").child("imagemResource").setValue(imagemResource1);

        noticiasRef.child("noticia2").child("titulo").setValue(tituloNoticia2);
        noticiasRef.child("noticia2").child("texto").setValue(textoNoticia2);
        noticiasRef.child("noticia2").child("imagemResource").setValue(imagemResource2);

        noticiasRef.child("noticia3").child("titulo").setValue(tituloNoticia3);
        noticiasRef.child("noticia3").child("texto").setValue(textoNoticia3);
        noticiasRef.child("noticia3").child("imagemResource").setValue(imagemResource3);

        noticiasRef.child("noticia4").child("titulo").setValue(tituloNoticia4);
        noticiasRef.child("noticia4").child("texto").setValue(textoNoticia4);
        noticiasRef.child("noticia4").child("imagemResource").setValue(imagemResource4);

        noticiasRef.child("noticia5").child("titulo").setValue(tituloNoticia5);
        noticiasRef.child("noticia5").child("texto").setValue(textoNoticia5);
        noticiasRef.child("noticia5").child("imagemResource").setValue(imagemResource5);

        noticiasRef.child("noticia6").child("titulo").setValue(tituloNoticia6);
        noticiasRef.child("noticia6").child("texto").setValue(textoNoticia6);
        noticiasRef.child("noticia6").child("imagemResource").setValue(imagemResource6);
    }

    private void carregarNoticiasFirebase() {
        noticiasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int count = 0;


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (count >= 2) break;

                        String titulo = snapshot.child("titulo").getValue(String.class);
                        String texto = snapshot.child("texto").getValue(String.class);
                        int imagemResource = snapshot.child("imagemResource").getValue(Integer.class);

                        if (count == 0) {
                            TextView tituloNoticia1TextView = findViewById(R.id.tituloNoticia1TextView);
                            tituloNoticia1TextView.setText(titulo);

                            TextView textoNoticia1TextView = findViewById(R.id.textoNoticia1TextView);
                            textoNoticia1TextView.setText(texto);

                            ImageView imagemNoticia1ImageView = findViewById(R.id.imagemNoticia1ImageView);
                            Drawable drawable = getResources().getDrawable(imagemResource);
                            imagemNoticia1ImageView.setImageDrawable(drawable);
                        } else if (count == 1) {
                            TextView tituloNoticia2TextView = findViewById(R.id.tituloNoticia2TextView);
                            tituloNoticia2TextView.setText(titulo);

                            TextView textoNoticia2TextView = findViewById(R.id.textoNoticia2TextView);
                            textoNoticia2TextView.setText(texto);

                            ImageView imagemNoticia2ImageView = findViewById(R.id.imagemNoticia2ImageView);
                            Drawable drawable = getResources().getDrawable(imagemResource);
                            imagemNoticia2ImageView.setImageDrawable(drawable);
                        }

                        count++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MenuPrincipalActivity", "Erro ao carregar as notícias: " + databaseError.getMessage());
            }
        });
    }
}
