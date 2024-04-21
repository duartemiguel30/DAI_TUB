package com.example.dai_tub;

import android.os.Parcel;
import android.os.Parcelable;

public class Bilhete implements Parcelable {
    private String bilheteId;
    private String nomeUsuario;
    private String dataCompra;
    private String validade;
    private String pontoPartida;
    private String pontoChegada;

    // Construtor padrão sem argumentos (necessário para Firebase)
    public Bilhete() {
    }

    // Construtor com argumentos
    public Bilhete(String bilheteId, String nomeUsuario, String dataCompra, String validade, String pontoPartida, String pontoChegada) {
        this.bilheteId = bilheteId;
        this.nomeUsuario = nomeUsuario;
        this.dataCompra = dataCompra;
        this.validade = validade;
        this.pontoPartida = pontoPartida;
        this.pontoChegada = pontoChegada;
    }

    // Métodos getter e setter

    protected Bilhete(Parcel in) {
        bilheteId = in.readString();
        nomeUsuario = in.readString();
        dataCompra = in.readString();
        validade = in.readString();
        pontoPartida = in.readString();
        pontoChegada = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bilheteId);
        dest.writeString(nomeUsuario);
        dest.writeString(dataCompra);
        dest.writeString(validade);
        dest.writeString(pontoPartida);
        dest.writeString(pontoChegada);
    }
}
