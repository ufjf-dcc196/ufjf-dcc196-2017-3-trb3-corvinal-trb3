package com.example.fernanda.trabalho3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag
        implements Parcelable {

    private int id;
    private final String nome;

    public Tag(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tag) {
            Tag that = (Tag) obj;
            return this.id == that.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return nome;
    }

    Tag(Parcel in) {
        id = in.readInt();
        nome = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
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
    }
}