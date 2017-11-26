package com.example.fernanda.trabalho3.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.fernanda.trabalho3.model.Tag;

import java.util.ArrayList;
import java.util.List;

import static com.example.fernanda.trabalho3.dao.TagConstantes.COLUNA_ID;
import static com.example.fernanda.trabalho3.dao.TagConstantes.COLUNA_NOME;
import static com.example.fernanda.trabalho3.dao.TagConstantes.TABELA_TAG;
import static com.example.fernanda.trabalho3.dao.TarefaTagConstantes.TABELA_TAREFA_TAG;

public class TagDao {

    private final SQLiteDatabase db;

    public TagDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Tag> getTags() {
        String querySelect = "SELECT * FROM " + TABELA_TAG + ";";
        final Cursor c = db.rawQuery(querySelect, null);
        c.moveToFirst();
        List<Tag> tags = new ArrayList<>(c.getCount());
        if (c.moveToFirst()){
            do {
                Tag tag = getTagFromCursor(c);
                tags.add(tag);
            } while(c.moveToNext());
        }
        c.close();
        return tags;
    }

    @NonNull
    private Tag getTagFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUNA_ID));
        String nome = c.getString(c.getColumnIndex(COLUNA_NOME));
        return new Tag(id, nome);
    }

    public void inserirTag(Tag tag) {
        ContentValues contentValues = getContentValuesFromTag(tag);
        tag.setId((int) db.insert(TABELA_TAG, null, contentValues));
    }

    public void updateTag(Tag tag) {
        ContentValues contentValues = getContentValuesFromTag(tag);
        db.update(TABELA_TAG, contentValues, TagConstantes.COLUNA_ID + "=?",
                new String[]{ String.valueOf(tag.getId()) });
    }

    @NonNull
    private ContentValues getContentValuesFromTag(Tag tag) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_NOME, tag.getNome());
        return contentValues;
    }

    @NonNull
    private String getJoinSelectQuery(int tarefaId) {
        return "SELECT * FROM " + TABELA_TAREFA_TAG + " ttt INNER JOIN " + TABELA_TAG + " tag " +
                "ON ttt." + TarefaTagConstantes.COLUNA_ID_TAG + " = tag." + TagConstantes.COLUNA_ID +
                " WHERE ttt." + TarefaTagConstantes.COLUNA_ID_TAREFA + " = " + tarefaId;
    }

    List<Tag> getTagsOfTarefa(int tarefaId) {
        String selectQuery = getJoinSelectQuery(tarefaId);
        final Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        List<Tag> tags = new ArrayList<>(c.getCount());
        if (c.moveToFirst()){
            do {
                Tag tag = getTagFromCursor(c);
                tags.add(tag);
            } while(c.moveToNext());
        }
        c.close();
        return tags;
    }
}
