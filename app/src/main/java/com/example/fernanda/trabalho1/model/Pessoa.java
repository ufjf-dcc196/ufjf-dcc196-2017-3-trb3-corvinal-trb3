package com.example.fernanda.trabalho1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Pessoa implements Parcelable {

    private int id;
    private final String nome;
    private final String email;
    private Date horarioEntrada;
    private Date horarioSaida;

    public Pessoa(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Date getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(Date horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public Date getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(Date horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    @Override
    public String toString() {
        return nome;
    }

    Pessoa(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        email = in.readString();

        long timeEntrada = in.readLong();
        if(timeEntrada != -1) horarioEntrada = new Date(timeEntrada);
        long timeSaida = in.readLong();
        if(timeSaida != -1) horarioSaida = new Date(timeSaida);
    }

    public static final Creator<Pessoa> CREATOR = new Creator<Pessoa>() {
        @Override
        public Pessoa createFromParcel(Parcel in) {
            return new Pessoa(in);
        }

        @Override
        public Pessoa[] newArray(int size) {
            return new Pessoa[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeString(email);
        parcel.writeLong(horarioEntrada == null ? -1 : horarioEntrada.getTime());
        parcel.writeLong(horarioSaida == null ? -1 : horarioSaida.getTime());
    }
}