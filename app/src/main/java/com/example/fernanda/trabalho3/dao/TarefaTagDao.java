package com.example.fernanda.trabalho3.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

class TarefaTagDao {

    private final SQLiteDatabase db;

    TarefaTagDao(SQLiteDatabase db) {
        this.db = db;
    }

    void inserirTarefaTag(int tarefaId, int tagId) {
        ContentValues contentValues = getContentValuesFromIds(tarefaId, tagId);
        db.insert(TarefaTagConstantes.TABELA_TAREFA_TAG, null, contentValues);
    }

    void deletarTaregaTags(int tarefaId) {
        db.delete(TarefaTagConstantes.TABELA_TAREFA_TAG, TarefaTagConstantes.COLUNA_ID_TAREFA + "=?",
                new String[] { String.valueOf(tarefaId) });
    }

    @NonNull
    private ContentValues getContentValuesFromIds(int tarefaId, int tagId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TarefaTagConstantes.COLUNA_ID_TAREFA, tarefaId);
        contentValues.put(TarefaTagConstantes.COLUNA_ID_TAG, tagId);
        return contentValues;
    }
}
