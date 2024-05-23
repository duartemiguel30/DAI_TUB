package com.example.dai_tub;

import android.os.Parcel;
import android.os.Parcelable;

public class Bilhete implements Parcelable {
    private String bilheteId;
    private String userId;
    private String nomeUsuario;
    private String dataCompra;
    private String validade;
    private String pontoPartida;
    private String pontoChegada;
    private Rota rota; // Adicionando o atributo rota

    // Construtor padrão sem argumentos (necessário para Firebase)
    public Bilhete() {
    }

    // Construtor com argumentos
    public Bilhete(String bilheteId, String userId, String nomeUsuario, String dataCompra, String validade, String pontoPartida, String pontoChegada, Rota rota) {
        this.bilheteId = bilheteId;
        this.userId = userId;
        this.nomeUsuario = nomeUsuario;
        this.dataCompra = dataCompra;
        this.validade = validade;
        this.pontoPartida = pontoPartida;
        this.pontoChegada = pontoChegada;
        this.rota = rota; // Atribuindo o objeto rota
    }

    // Métodos getter e setter

    protected Bilhete(Parcel in) {
        bilheteId = in.readString();
        userId = in.readString();
        nomeUsuario = in.readString();
        dataCompra = in.readString();
        validade = in.readString();
        pontoPartida = in.readString();
        pontoChegada = in.readString();
        rota = in.readParcelable(Rota.class.getClassLoader()); // Lendo o objeto Rota do Parcel
    }

    public static final Creator<Bilhete> CREATOR = new Creator<Bilhete>() {
        @Override
        public Bilhete createFromParcel(Parcel in) {
            return new Bilhete(in);
        }

        @Override
        public Bilhete[] newArray(int size) {
            return new Bilhete[size];
        }
    };

    public String getBilheteId() {
        return bilheteId;
    }

    public String getUserId() {
        return userId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public String getValidade() {
        return validade;
    }

    public String getPontoPartida() {
        return pontoPartida;
    }

    public String getPontoChegada() {
        return pontoChegada;
    }

    public Rota getRota() {
        return rota; // Retornando o objeto Rota
    }

    public void setRota(Rota rota) {
        this.rota = rota; // Definindo o objeto Rota
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bilheteId);
        dest.writeString(userId);
        dest.writeString(nomeUsuario);
        dest.writeString(dataCompra);
        dest.writeString(validade);
        dest.writeString(pontoPartida);
        dest.writeString(pontoChegada);
        dest.writeParcelable(rota, flags); // Escrever o objeto Rota no Parcel
    }
}