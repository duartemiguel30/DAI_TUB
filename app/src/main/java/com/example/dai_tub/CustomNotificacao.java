package com.example.dai_tub;

public class CustomNotificacao {
    private String titulo;
    private String texto;

    public CustomNotificacao(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }
}
