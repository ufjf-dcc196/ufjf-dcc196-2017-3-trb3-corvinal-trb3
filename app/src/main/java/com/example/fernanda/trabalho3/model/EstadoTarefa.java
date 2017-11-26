package com.example.fernanda.trabalho3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EstadoTarefa implements Parcelable{

    private final int id;
    private final int grauDificuldade;
    private final String nome;

    public EstadoTarefa(int id, int grauDificuldade, String nome) {
        this.id = id;
        this.grauDificuldade = grauDificuldade;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public int getGrauDificuldade() {
        return grauDificuldade;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EstadoTarefa) {
            EstadoTarefa that = (EstadoTarefa) obj;
            return id == that.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }

    protected EstadoTarefa(Parcel in) {
        id = in.readInt();
        grauDificuldade = in.readInt();
        nome = in.readString();
    }

    public static final Creator<EstadoTarefa> CREATOR = new Creator<EstadoTarefa>() {
        @Override
        public EstadoTarefa createFromParcel(Parcel in) {
            return new EstadoTarefa(in);
        }

        @Override
        public EstadoTarefa[] newArray(int size) {
            return new EstadoTarefa[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(grauDificuldade);
        dest.writeString(nome);
    }
}
