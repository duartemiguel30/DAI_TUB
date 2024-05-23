package com.example.dai_tub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RotaAdapter extends RecyclerView.Adapter<RotaAdapter.RotaViewHolder> {

    private List<Rota> listaRotas;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private Context context;

    public RotaAdapter(Context context, List<Rota> listaRotas, OnItemClickListener listener) {
        this.context = context;
        this.listaRotas = listaRotas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rota, parent, false);
        return new RotaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RotaViewHolder holder, int position) {
        Rota rota = listaRotas.get(position);
        holder.bind(rota, listener);

        holder.radioButton.setChecked(selectedPosition == position);

        holder.btnVerPrecos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém os preços da rota
                double precoNormal = rota.getPrecoNormal();
                double precoEstudante = rota.getPrecoEstudante();

                // Constrói a mensagem com os preços
                String mensagem = "Preço Normal: €" + precoNormal + "\nPreço Estudante: €" + precoEstudante;

                // Mostra a mensagem
                Toast.makeText(holder.itemView.getContext(), mensagem, Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnVerHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega para a atividade de detalhes da rota, passando a rota inteira
                Intent intent = new Intent(context, DetalhesRotaActivity.class);
                intent.putExtra("rota", rota); // Passa a rota inteira
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaRotas.size();
    }

    public static class RotaViewHolder extends RecyclerView.ViewHolder {

        private TextView numeroTextView;
        private TextView descricaoTextView;
        private TextView pontoPartidaTextView;
        private TextView pontoChegadaTextView;
        private RadioButton radioButton;
        private Button btnVerPrecos;
        private Button btnVerHorarios;

        public RotaViewHolder(@NonNull View itemView) {
            super(itemView);
            numeroTextView = itemView.findViewById(R.id.numeroTextView);
            descricaoTextView = itemView.findViewById(R.id.descricaoTextView);
            pontoPartidaTextView = itemView.findViewById(R.id.pontoPartidaTextView);
            pontoChegadaTextView = itemView.findViewById(R.id.pontoChegadaTextView);
            radioButton = itemView.findViewById(R.id.radioButtonSelecao);
            btnVerPrecos = itemView.findViewById(R.id.btnVerPrecos);
            btnVerHorarios = itemView.findViewById(R.id.btnVerHorarios);
        }

        public void bind(final Rota rota, final OnItemClickListener listener) {
            numeroTextView.setText(rota.getNumero());
            descricaoTextView.setText(rota.getDescricao());
            pontoPartidaTextView.setText("Ponto de Partida: " + rota.getPontoPartida());
            pontoChegadaTextView.setText("Ponto de Chegada: " + rota.getPontoChegada());

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(rota, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Rota rota, int position);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }
}
