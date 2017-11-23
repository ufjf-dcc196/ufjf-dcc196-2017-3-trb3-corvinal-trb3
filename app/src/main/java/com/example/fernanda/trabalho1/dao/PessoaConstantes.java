package com.example.fernanda.trabalho1.dao;

public class PessoaConstantes {

    private PessoaConstantes() { }

    static final String TABELA_PESSOA = "tb_pessoa";
    static final String COLUNA_ID = "id";
    static final String COLUNA_NOME = "nome";
    static final String COLUNA_EMAIL = "email";
    static final String COLUNA_ENTRADA = "entrada";
    static final String COLUNA_SAIDA = "saida";

    static final String CREATE_TABLE_PESSOA = "CREATE TABLE IF NOT EXISTS " + TABELA_PESSOA + " ( " +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_NOME + " TEXT NOT NULL, " +
            COLUNA_EMAIL + " TEXT NOT NULL, " +
            COLUNA_ENTRADA + " DATE, " +
            COLUNA_SAIDA+ " DATE " +
            ");";
}
