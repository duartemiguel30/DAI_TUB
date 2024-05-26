package com.example.dai_tub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RotaHorarioAdapter extends ArrayAdapter<Rota> {

    private Context mContext;
    private int mResource;

    public RotaHorarioAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Rota> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the layout for each list item
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        // Get the rota object for the current position
        Rota rota = getItem(position);

        // Set the text for each TextView in the layout
        TextView descricaoRota = convertView.findViewById(R.id.descricaoRota);
        TextView pontoPartida = convertView.findViewById(R.id.pontoPartida);
        TextView pontoChegada = convertView.findViewById(R.id.pontoChegada);
        TextView horarios = convertView.findViewById(R.id.horarios);

        descricaoRota.setText(rota.getDescricao());
        pontoPartida.setText("Ponto de Partida: " + rota.getPontoPartida());
        pontoChegada.setText("Ponto de Chegada: " + rota.getPontoChegada());

        StringBuilder horariosBuilder = new StringBuilder();
        for (Horario horario : rota.getHorarios()) {
            String horaPartida = String.format("%02d:%02d", horario.getHoraPartida(), horario.getMinutoPartida());
            String horaChegada = String.format("%02d:%02d", horario.getHoraChegada(), horario.getMinutoChegada());
            horariosBuilder.append(horaPartida).append(" - ").append(horaChegada).append("\n");
        }

        // Remove the last newline character
        if (horariosBuilder.length() > 0) {
            horariosBuilder.setLength(horariosBuilder.length() - 1);
        }

        horarios.setText(horariosBuilder.toString());

        return convertView;
    }
}

