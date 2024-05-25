package com.example.dai_tub;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NoticiasActivity extends AppCompatActivity {

    DatabaseReference noticiasRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        noticiasRef = FirebaseDatabase.getInstance().getReference().child("noticias");

        carregarNoticiasFirebase();
    }

    private void carregarNoticiasFirebase() {
        noticiasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int count = 0;

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (count >= 6) break;

                        String titulo = snapshot.child("titulo").getValue(String.class);
                        String texto = snapshot.child("texto").getValue(String.class);
                        int imagemResource = snapshot.child("imagemResource").getValue(Integer.class);

                        int tituloId = getResources().getIdentifier("tituloNoticia" + (count + 1) + "TextView", "id", getPackageName());
                        int textoId = getResources().getIdentifier("textoNoticia" + (count + 1) + "TextView", "id", getPackageName());
                        int imagemId = getResources().getIdentifier("imagemNoticia" + (count + 1) + "ImageView", "id", getPackageName());

                        TextView tituloTextView = findViewById(tituloId);
                        TextView textoTextView = findViewById(textoId);
                        ImageView imagemImageView = findViewById(imagemId);

                        tituloTextView.setText(titulo);
                        textoTextView.setText(texto);
                        Drawable drawable = getResources().getDrawable(imagemResource);
                        imagemImageView.setImageDrawable(drawable);

                        count++;
                    }
                } else {
                    Log.d("NoticiasActivity", "Nenhuma notícia encontrada na base de dados.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("NoticiasActivity", "Erro ao carregar as notícias: " + databaseError.getMessage());
            }
        });
    }
}
