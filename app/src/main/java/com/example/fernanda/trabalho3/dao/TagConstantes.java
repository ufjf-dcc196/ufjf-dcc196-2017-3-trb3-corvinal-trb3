package com.example.fernanda.trabalho3.dao;

class TagConstantes {

    private TagConstantes() { }

    static final String TABELA_TAG = "tb_tag";
    static final String COLUNA_ID = "id_tag";
    static final String COLUNA_NOME = "nome";

    static final String CREATE_TABLE_TAG = "CREATE TABLE IF NOT EXISTS " + TABELA_TAG + " ( " +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_NOME + " TEXT NOT NULL );";
}
