package com.example.fernanda.trabalho3.dao;

class TarefaTagConstantes {

    private TarefaTagConstantes() { }

    static final String TABELA_TAREFA_TAG = "tr_tabela_tag";
    static final String COLUNA_ID_TAREFA = "id_tarefa";
    static final String COLUNA_ID_TAG = "id_tag";

    static final String CREATE_TABLE_TAREFA_TAG = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFA_TAG + " ( " +
            COLUNA_ID_TAREFA + " INTEGER REFERENCES " + TarefaConstantes.TABELA_TAREFA +"(" + TarefaConstantes.COLUNA_ID + "), " +
            COLUNA_ID_TAG + " INTEGER REFERENCES " + TagConstantes.TABELA_TAG +"(" + TagConstantes.COLUNA_ID + ") " +
            ");";
}
