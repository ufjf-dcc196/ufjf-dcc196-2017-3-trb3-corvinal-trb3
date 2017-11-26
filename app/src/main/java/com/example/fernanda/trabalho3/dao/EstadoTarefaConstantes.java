package com.example.fernanda.trabalho3.dao;

class EstadoTarefaConstantes {

    private EstadoTarefaConstantes() { }

    static final String TABELA_ESTADO_TAREFA = "tb_estado_tarefa";
    static final String COLUNA_ID = "id_estado";
    static final String COLUNA_GRAU_DIFICULDADE = "grau_dificuldade";
    static final String COLUNA_NOME = "nome_estado";

    static final String CREATE_TABLE_ESTADO_TAREFA = "CREATE TABLE IF NOT EXISTS " + TABELA_ESTADO_TAREFA + " ( " +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_GRAU_DIFICULDADE + " INTEGER NOT NULL, " +
            COLUNA_NOME + " TEXT NOT NULL );";
}
