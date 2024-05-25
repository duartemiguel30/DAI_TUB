package com.example.dai_tub;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DetalhesRotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_rota_activity);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("rota")) {
            Rota rota = intent.getParcelableExtra("rota");

            RecyclerView recyclerView = findViewById(R.id.horariosRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            HorarioAdapter adapter = new HorarioAdapter(rota.getHorarios());
            recyclerView.setAdapter(adapter);
        }
    }
}