package com.example.fernanda.trabalho1.dao;

class LivroConstantes {

    private LivroConstantes() { }

    static final String TABELA_LIVRO = "tb_livro";
    static final String COLUNA_ID = "id";
    static final String COLUNA_TITULO = "titulo";
    static final String COLUNA_EDITORA = "editora";
    static final String COLUNA_ANO_PUBLICACAO = "ano_publicacao";

    static final String CREATE_TABLE_LIVRO = "CREATE TABLE IF NOT EXISTS " + TABELA_LIVRO + " ( " +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_TITULO + " TEXT NOT NULL, " +
            COLUNA_EDITORA + " TEXT NOT NULL, " +
            COLUNA_ANO_PUBLICACAO + " TEXT NOT NULL " +
            ");";
}
