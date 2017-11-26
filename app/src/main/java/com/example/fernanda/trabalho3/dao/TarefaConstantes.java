package com.example.fernanda.trabalho3.dao;

class TarefaConstantes {

    private TarefaConstantes() { }

    static final String TABELA_TAREFA = "tb_tarefa";
    static final String COLUNA_ID = "id_tarefa";
    static final String COLUNA_TITULO = "titulo";
    static final String COLUNA_DESCRICAO = "descricao";
    static final String COLUNA_ESTADO = "estado";

    static final String CREATE_TABLE_TAREFA = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFA + " ( " +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_TITULO + " TEXT NOT NULL, " +
            COLUNA_DESCRICAO + " TEXT NOT NULL, " +
            COLUNA_ESTADO + " INTEGER REFERENCES " + EstadoTarefaConstantes.TABELA_ESTADO_TAREFA +
            "(" + EstadoTarefaConstantes.COLUNA_ID + ") );";
}
