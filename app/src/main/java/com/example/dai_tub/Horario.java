package com.example.dai_tub;

import android.os.Parcel;
import android.os.Parcelable;

public class Horario implements Parcelable {

    private int horaPartida;
    private int minutoPartida;
    private int horaChegada;
    private int minutoChegada;

    public Horario() {
    }

    public Horario(int horaPartida, int minutoPartida, int horaChegada, int minutoChegada) {
        this.horaPartida = horaPartida;
        this.minutoPartida = minutoPartida;
        this.horaChegada = horaChegada;
        this.minutoChegada = minutoChegada;
    }

    public int getHoraPartida() {
        return horaPartida;
    }

    public void setHoraPartida(int horaPartida) {
        this.horaPartida = horaPartida;
    }

    public int getMinutoPartida() {
        return minutoPartida;
    }

    public void setMinutoPartida(int minutoPartida) {
        this.minutoPartida = minutoPartida;
    }

    public int getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(int horaChegada) {
        this.horaChegada = horaChegada;
    }

    public int getMinutoChegada() {
        return minutoChegada;
    }

    public void setMinutoChegada(int minutoChegada) {
        this.minutoChegada = minutoChegada;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(horaPartida);
        dest.writeInt(minutoPartida);
        dest.writeInt(horaChegada);
        dest.writeInt(minutoChegada);
    }

    protected Horario(Parcel in) {
        horaPartida = in.readInt();
        minutoPartida = in.readInt();
        horaChegada = in.readInt();
        minutoChegada = in.readInt();
    }

    public static final Creator<Horario> CREATOR = new Creator<Horario>() {
        @Override
        public Horario createFromParcel(Parcel in) {
            return new Horario(in);
        }

        @Override
        public Horario[] newArray(int size) {
            return new Horario[size];
        }
    };}