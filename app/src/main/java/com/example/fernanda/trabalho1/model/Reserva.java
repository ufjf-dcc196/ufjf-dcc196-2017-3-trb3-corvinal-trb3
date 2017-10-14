package com.example.fernanda.trabalho1.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

public class Reserva implements Parcelable{
    private final Pessoa pessoa;
    private final Livro livro;

    public Reserva(Pessoa pessoa, Livro livro) {
        this.pessoa = pessoa;
        this.livro = livro;
    }

    protected Reserva(Parcel in) {
        pessoa = in.readParcelable(Pessoa.class.getClassLoader());
        livro = in.readParcelable(Livro.class.getClassLoader());
    }

    public static final Creator<Reserva> CREATOR = new Creator<Reserva>() {
        @Override
        public Reserva createFromParcel(Parcel in) {
            return new Reserva(in);
        }

        @Override
        public Reserva[] newArray(int size) {
            return new Reserva[size];
        }
    };

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Livro getLivro() {
        return livro;
    }

    @Override
    public String toString() {
        String nome = pessoa.getNome();
        return nome;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(pessoa, i);
        parcel.writeParcelable(livro, i);
    }
}
