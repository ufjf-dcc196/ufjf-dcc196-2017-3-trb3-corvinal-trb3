package com.example.fernanda.trabalho1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Livro implements Parcelable {
    private final String titulo;
    private final String editora;
    private final String anoPublicacao;

    public Livro(String titulo, String editora, String anoPublicacao) {
        this.titulo = titulo;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditora() {
        return editora;
    }

    public String getAnoPublicacao() {
        return anoPublicacao;
    }

    @Override
    public String toString() {
        return titulo;
    }

    Livro(Parcel in) {
        titulo = in.readString();
        editora = in.readString();
        anoPublicacao = in.readString();
    }

    public static final Creator<Livro> CREATOR = new Creator<Livro>() {
        @Override
        public Livro createFromParcel(Parcel in) {
            return new Livro(in);
        }

        @Override
        public Livro[] newArray(int size) {
            return new Livro[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(titulo);
        parcel.writeString(editora);
        parcel.writeString(anoPublicacao);
    }
}
