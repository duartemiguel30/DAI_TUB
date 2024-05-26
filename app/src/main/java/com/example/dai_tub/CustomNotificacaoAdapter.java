package com.example.dai_tub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomNotificacaoAdapter extends RecyclerView.Adapter<CustomNotificacaoAdapter.CustomNotificacaoViewHolder> {

    private List<CustomNotificacao> mNotificacoesList;

    public CustomNotificacaoAdapter(List<CustomNotificacao> notificacoesList) {
        mNotificacoesList = notificacoesList;
    }

    @NonNull
    @Override
    public CustomNotificacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notificacao_item, parent, false);
        return new CustomNotificacaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomNotificacaoViewHolder holder, int position) {
        CustomNotificacao notificacao = mNotificacoesList.get(position);
        holder.bind(notificacao);
    }

    @Override
    public int getItemCount() {
        return mNotificacoesList.size();
    }

    public class CustomNotificacaoViewHolder extends RecyclerView.ViewHolder {

        private TextView mTituloTextView;
        private TextView mTextoTextView;

        public CustomNotificacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            mTituloTextView = itemView.findViewById(R.id.tituloTextView);
            mTextoTextView = itemView.findViewById(R.id.textoTextView);
        }

        public void bind(CustomNotificacao notificacao) {
            mTituloTextView.setText(notificacao.getTitulo());
            mTextoTextView.setText(notificacao.getTexto());
        }
    }
}
