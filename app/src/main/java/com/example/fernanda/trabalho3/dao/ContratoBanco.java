package com.example.fernanda.trabalho3.dao;

import android.database.sqlite.SQLiteDatabase;

import static com.example.fernanda.trabalho3.dao.EstadoTarefaConstantes.TABELA_ESTADO_TAREFA;
import static com.example.fernanda.trabalho3.dao.TagConstantes.TABELA_TAG;
import static com.example.fernanda.trabalho3.dao.TarefaConstantes.TABELA_TAREFA;
import static com.example.fernanda.trabalho3.dao.TarefaTagConstantes.TABELA_TAREFA_TAG;

public class ContratoBanco {

    public static void criarTabelas(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_TAREFA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_TAREFA_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ESTADO_TAREFA);
        db.execSQL(TagConstantes.CREATE_TABLE_TAG);
        db.execSQL(EstadoTarefaConstantes.CREATE_TABLE_ESTADO_TAREFA);
        db.execSQL(TarefaConstantes.CREATE_TABLE_TAREFA);
        db.execSQL(TarefaTagConstantes.CREATE_TABLE_TAREFA_TAG);
    }
}
