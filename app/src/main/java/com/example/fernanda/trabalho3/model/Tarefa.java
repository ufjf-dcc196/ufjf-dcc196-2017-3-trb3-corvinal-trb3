package com.example.fernanda.trabalho3.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Tarefa
        implements Parcelable {

    private int id;
    private final String titulo;
    private final String descricao;
    private final EstadoTarefa estado;
    private final List<Tag> tags;

    public Tarefa(int id, String titulo, String descricao, EstadoTarefa estado, List<Tag> tags) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.estado = estado;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public EstadoTarefa getEstado() {
        return estado;
    }

    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        if(tags.isEmpty()) return titulo;
        StringBuilder sb = new StringBuilder(titulo + " (");
        for(Tag tag : tags) {
            sb.append(tag.getNome());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");
        return sb.toString();
    }

    Tarefa(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        descricao = in.readString();
        estado = in.readParcelable(EstadoTarefa.class.getClassLoader());

        tags = new ArrayList<>();
        in.readList(tags, Tag.class.getClassLoader());
    }

    public static final Creator<Tarefa> CREATOR = new Creator<Tarefa>() {
        @Override
        public Tarefa createFromParcel(Parcel in) {
            return new Tarefa(in);
        }

        @Override
        public Tarefa[] newArray(int size) {
            return new Tarefa[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(titulo);
        parcel.writeString(descricao);
        parcel.writeParcelable(estado, 0);
        parcel.writeList(tags);
    }
}
