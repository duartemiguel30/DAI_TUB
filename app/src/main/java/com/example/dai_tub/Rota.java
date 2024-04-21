package com.example.dai_tub;

import java.io.Serializable;

public class Rota implements Serializable {
    private String numero;
    private String descricao;
    private String pontoPartida;
    private String pontoChegada;
    private double precoNormal;
    private double precoEstudante;

    public Rota() {
        // Construtor vazio requerido pelo Firebase Realtime Database
    }

    public Rota(String numero, String descricao, String pontoPartida, String pontoChegada, double precoNormal, double precoEstudante) {
        this.numero = numero;
        this.descricao = descricao;
        this.pontoPartida = pontoPartida;
        this.pontoChegada = pontoChegada;
        this.precoNormal = precoNormal;
        this.precoEstudante = precoEstudante;
    }


    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPontoPartida() {
        return pontoPartida;
    }

    public void setPontoPartida(String pontoPartida) {
        this.pontoPartida = pontoPartida;
    }

    public String getPontoChegada() {
        return pontoChegada;
    }

    public void setPontoChegada(String pontoChegada) {
        this.pontoChegada = pontoChegada;
    }

    public double getPrecoNormal() {
        return precoNormal;
    }

    public void setPrecoNormal(double precoNormal) {
        this.precoNormal = precoNormal;
    }

    public double getPrecoEstudante() {
        return precoEstudante;
    }

    public void setPrecoEstudante(double precoEstudante) {
        this.precoEstudante = precoEstudante;
    }
}
