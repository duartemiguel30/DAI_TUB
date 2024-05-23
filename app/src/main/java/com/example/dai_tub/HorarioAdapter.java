// HorarioAdapter.java
package com.example.dai_tub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.HorarioViewHolder> {

    private List<Horario> mHorarios;

    public HorarioAdapter(List<Horario> horarios) {
        mHorarios = horarios;
    }

    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horario_item, parent, false);
        return new HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioViewHolder holder, int position) {
        Horario horario = mHorarios.get(position);
        holder.bind(horario);
    }

    @Override
    public int getItemCount() {
        return mHorarios.size();
    }

    public class HorarioViewHolder extends RecyclerView.ViewHolder {

        private TextView mHorarioTextView;

        public HorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            mHorarioTextView = itemView.findViewById(R.id.horarioTextView);
        }

        public void bind(Horario horario) {
            mHorarioTextView.setText("Partida: " + horario.getHoraPartida() + ":" + horario.getMinutoPartida() +
                    " - Chegada: " + horario.getHoraChegada() + ":" + horario.getMinutoChegada());
        }
    }
}
