
package com.example.dai_tub;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import java.util.List;

public class BilheteAdapter extends RecyclerView.Adapter<BilheteAdapter.BilheteViewHolder> {

    private List<Bilhete> bilhetesList;

    public BilheteAdapter(List<Bilhete> bilhetesList) {
        this.bilhetesList = bilhetesList;
    }

    @NonNull
    @Override
    public BilheteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bilhete, parent, false);
        return new BilheteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BilheteViewHolder holder, int position) {
        Bilhete bilhete = bilhetesList.get(position);
        holder.bind(bilhete);
    }

    @Override
    public int getItemCount() {
        return bilhetesList.size();
    }

    public static class BilheteViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNomeUsuario;
        private TextView textViewDataCompra;
        private TextView textViewValidade;
        private TextView textViewPontoPartida;
        private TextView textViewPontoChegada;
        private Button buttonShowQRCode;

        public BilheteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeUsuario = itemView.findViewById(R.id.textViewNomeUsuario);
            textViewDataCompra = itemView.findViewById(R.id.textViewDataCompra);
            textViewValidade = itemView.findViewById(R.id.textViewValidade);
            textViewPontoPartida = itemView.findViewById(R.id.textViewPontoPartida);
            textViewPontoChegada = itemView.findViewById(R.id.textViewPontoChegada);
            buttonShowQRCode = itemView.findViewById(R.id.buttonShowQRCode);
        }

        public void bind(Bilhete bilhete) {
            textViewNomeUsuario.setText("Nome do Utilizador: " + bilhete.getNomeUsuario());
            textViewDataCompra.setText("Data de Compra: " + bilhete.getDataCompra());
            textViewValidade.setText("Validade: " + bilhete.getValidade());
            textViewPontoPartida.setText("Ponto de Partida: " + bilhete.getPontoPartida());
            textViewPontoChegada.setText("Ponto de Chegada: " + bilhete.getPontoChegada());


            buttonShowQRCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exibirQRCode(bilhete);
                }
            });
        }

        private void exibirQRCode(Bilhete bilhete) {
            Intent intent = new Intent(itemView.getContext(), QRCodeGenerator.class);
            intent.putExtra("bilhete", bilhete);
            itemView.getContext().startActivity(intent);
        }
    }
}