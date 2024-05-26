package com.example.dai_tub;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class VisualizarRotasHorariosActivity extends AppCompatActivity {

    private ListView listView;
    private RotaHorarioAdapter adapter;
    private ArrayList<Rota> listData;
    private DatabaseReference databaseReference;
    private static final String TAG = "VisualizarRotasHorarios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotas_horarios);

        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        adapter = new RotaHorarioAdapter(this, R.layout.list_item_rota, listData);
        listView.setAdapter(adapter);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("rotas");

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listData.clear();
                for (DataSnapshot rotaSnapshot : dataSnapshot.getChildren()) {
                    String descricaoRota = rotaSnapshot.child("descricao").getValue(String.class);
                    String pontoPartida = rotaSnapshot.child("pontoPartida").getValue(String.class);
                    String pontoChegada = rotaSnapshot.child("pontoChegada").getValue(String.class);
                    ArrayList<Horario> horarios = new ArrayList<>();

                    DataSnapshot horariosSnapshot = rotaSnapshot.child("horarios");
                    for (DataSnapshot horarioSnapshot : horariosSnapshot.getChildren()) {
                        Long horaPartida = horarioSnapshot.child("horaPartida").getValue(Long.class);
                        Long minutoPartida = horarioSnapshot.child("minutoPartida").getValue(Long.class);
                        Long horaChegada = horarioSnapshot.child("horaChegada").getValue(Long.class);
                        Long minutoChegada = horarioSnapshot.child("minutoChegada").getValue(Long.class);

                        if (horaPartida != null && minutoPartida != null && horaChegada != null && minutoChegada != null) {
                            Horario horario = new Horario(horaPartida.intValue(), minutoPartida.intValue(), horaChegada.intValue(), minutoChegada.intValue());
                            horarios.add(horario);
                        }
                    }

                    if (descricaoRota != null && pontoPartida != null && pontoChegada != null) {
                        // Aqui está a correção
                        Rota rota = new Rota("", descricaoRota, pontoPartida, pontoChegada, 0.0, 0.0, horarios);
                        listData.add(rota);
                    }
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }
}
