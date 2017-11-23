package com.example.fernanda.trabalho1.dao;

import android.database.sqlite.SQLiteDatabase;

import static com.example.fernanda.trabalho1.dao.LivroConstantes.TABELA_LIVRO;
import static com.example.fernanda.trabalho1.dao.PessoaConstantes.TABELA_PESSOA;
import static com.example.fernanda.trabalho1.dao.ReservaConstantes.TABELA_RESERVA;

public class ContratoBanco {

    static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public static void criarTabelas(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PESSOA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_LIVRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_RESERVA);
        db.execSQL(PessoaConstantes.CREATE_TABLE_PESSOA);
        db.execSQL(LivroConstantes.CREATE_TABLE_LIVRO);
        db.execSQL(ReservaConstantes.CREATE_TABLE_RESERVA);
    }
}
