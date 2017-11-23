package com.example.fernanda.trabalho1.dao;

class ReservaConstantes {

    private ReservaConstantes() { }

    static final String TABELA_RESERVA = "tr_reserva";
    static final String COLUNA_ID_PESSOA = "id_pessoa";
    static final String COLUNA_ID_LIVRO = "id_livro";

    static final String CREATE_TABLE_RESERVA = "CREATE TABLE IF NOT EXISTS " + TABELA_RESERVA + " ( " +
            COLUNA_ID_PESSOA + " INTEGER REFERENCES " + PessoaConstantes.TABELA_PESSOA +"(" + PessoaConstantes.COLUNA_ID+ "), " +
            COLUNA_ID_LIVRO + " INTEGER REFERENCES " + LivroConstantes.TABELA_LIVRO +"(" + LivroConstantes.COLUNA_ID+ ") " +
            ");";
}
