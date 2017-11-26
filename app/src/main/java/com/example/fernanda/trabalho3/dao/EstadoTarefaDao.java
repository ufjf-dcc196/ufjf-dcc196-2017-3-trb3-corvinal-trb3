package com.example.fernanda.trabalho3.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.fernanda.trabalho3.model.EstadoTarefa;

import java.util.ArrayList;
import java.util.List;

import static com.example.fernanda.trabalho3.dao.EstadoTarefaConstantes.COLUNA_GRAU_DIFICULDADE;
import static com.example.fernanda.trabalho3.dao.EstadoTarefaConstantes.COLUNA_ID;
import static com.example.fernanda.trabalho3.dao.EstadoTarefaConstantes.COLUNA_NOME;
import static com.example.fernanda.trabalho3.dao.EstadoTarefaConstantes.TABELA_ESTADO_TAREFA;

public class EstadoTarefaDao {

    private final SQLiteDatabase db;

    public EstadoTarefaDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<EstadoTarefa> getEstados() {
        String querySelect = "SELECT * FROM " + TABELA_ESTADO_TAREFA + ";";
        final Cursor c = db.rawQuery(querySelect, null);
        c.moveToFirst();
        List<EstadoTarefa> estados = new ArrayList<>(c.getCount());
        if (c.moveToFirst()){
            do {
                EstadoTarefa estado = getEstadoFromCursor(c);
                estados.add(estado);
            } while(c.moveToNext());
        }
        c.close();
        return estados;
    }

    @NonNull
    static EstadoTarefa getEstadoFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUNA_ID));
        int grauDificuldade = c.getInt(c.getColumnIndex(COLUNA_GRAU_DIFICULDADE));
        String nome = c.getString(c.getColumnIndex(COLUNA_NOME));
        return new EstadoTarefa(id, grauDificuldade, nome);
    }

    public void inserirEstado(EstadoTarefa estado) {
        ContentValues contentValues = getContentValuesFromEstadoTarefa(estado);
        db.insert(TABELA_ESTADO_TAREFA, null, contentValues);
    }

    @NonNull
    private ContentValues getContentValuesFromEstadoTarefa(EstadoTarefa estado) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_GRAU_DIFICULDADE, estado.getGrauDificuldade());
        contentValues.put(COLUNA_NOME, estado.getNome());
        return contentValues;
    }
}
