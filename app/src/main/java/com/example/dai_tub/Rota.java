package com.example.dai_tub;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class Rota implements Parcelable {

    private String id;
    private String numero;
    private String descricao;
    private String pontoPartida;
    private String pontoChegada;
    private double precoNormal; // Alterado para double
    private double precoEstudante; // Alterado para double
    private List<Horario> horarios;
    private Horario primeiroHorario;
    private Horario ultimoHorario;

    public Rota() {
        // Construtor vazio
    }

    public Rota(String numero, String descricao, String pontoPartida, String pontoChegada, double precoNormal, double precoEstudante, List<Horario> horarios) {
        this.numero = numero;
        this.descricao = descricao;
        this.pontoPartida = pontoPartida;
        this.pontoChegada = pontoChegada;
        this.precoNormal = precoNormal;
        this.precoEstudante = precoEstudante;
        this.horarios = horarios;
        configurarHorarios(); // Configura os horários de partida e chegada ao criar a instância
    }

    // Getters e setters para todos os atributos

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

    public List<Horario> getHorarios() {
        return horarios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
        configurarHorarios(); // Configura os horários de partida e chegada ao definir os horários
    }

    public Horario getPrimeiroHorario() {
        return primeiroHorario;
    }

    public Horario getUltimoHorario() {
        return ultimoHorario;
    }

    public String getHorarioPartida() {
        if (primeiroHorario != null) {
            return primeiroHorario.getHoraPartida() + ":" + primeiroHorario.getMinutoPartida();
        }
        return "Não disponível";
    }

    public String getHorarioChegada() {
        if (ultimoHorario != null) {
            return ultimoHorario.getHoraChegada() + ":" + ultimoHorario.getMinutoChegada();
        }
        return "Não disponível";
    }

    // Método para configurar os horários de partida e chegada
    private void configurarHorarios() {
        if (horarios != null && !horarios.isEmpty()) {
            // Ordena os horários pelo horário de partida
            Collections.sort(horarios, Comparator.comparingInt(Horario::getHoraPartida).thenComparingInt(Horario::getMinutoPartida));
            primeiroHorario = horarios.get(0);
            ultimoHorario = horarios.get(horarios.size() - 1);
        }
    }

    // Implementações Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numero);
        dest.writeString(descricao);
        dest.writeString(pontoPartida);
        dest.writeString(pontoChegada);
        dest.writeDouble(precoNormal);
        dest.writeDouble(precoEstudante);
        dest.writeTypedList(horarios);
    }

    protected Rota(Parcel in) {
        numero = in.readString();
        descricao = in.readString();
        pontoPartida = in.readString();
        pontoChegada = in.readString();
        precoNormal = in.readDouble();
        precoEstudante = in.readDouble();
        horarios = in.createTypedArrayList(Horario.CREATOR);
        configurarHorarios(); // Configura os horários de partida e chegada ao ler os valores
    }

    public static final Creator<Rota> CREATOR = new Creator<Rota>() {
        @Override
        public Rota createFromParcel(Parcel in) {
            return new Rota(in);
        }

        @Override
        public Rota[] newArray(int size) {
            return new Rota[size];
        }
    };
}