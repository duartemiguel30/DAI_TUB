package com.example.dai_tub;

public class Noticia {
    private String titulo;
    private String texto;
    private String urlImagem;

    public Noticia() {
        // Construtor vazio necessário para o Firebase
    }

    public Noticia(String titulo, String texto, String urlImagem) {
        this.titulo = titulo;
        this.texto = texto;
        this.urlImagem = urlImagem;
    }

    // getters e setters
}
