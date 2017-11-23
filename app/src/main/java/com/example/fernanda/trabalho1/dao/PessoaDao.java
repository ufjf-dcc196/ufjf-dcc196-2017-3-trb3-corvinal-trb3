package com.example.fernanda.trabalho1.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.fernanda.trabalho1.model.Pessoa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.fernanda.trabalho1.dao.ContratoBanco.DATE_FORMAT;
import static com.example.fernanda.trabalho1.dao.PessoaConstantes.COLUNA_EMAIL;
import static com.example.fernanda.trabalho1.dao.PessoaConstantes.COLUNA_ENTRADA;
import static com.example.fernanda.trabalho1.dao.PessoaConstantes.COLUNA_ID;
import static com.example.fernanda.trabalho1.dao.PessoaConstantes.COLUNA_NOME;
import static com.example.fernanda.trabalho1.dao.PessoaConstantes.COLUNA_SAIDA;
import static com.example.fernanda.trabalho1.dao.PessoaConstantes.TABELA_PESSOA;

public class PessoaDao {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

    private final SQLiteDatabase db;

    public PessoaDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Pessoa> getPessoas() {
        String querySelect = "SELECT * FROM " + TABELA_PESSOA + ";";
        final Cursor c = db.rawQuery(querySelect, null);
        c.moveToFirst();
        List<Pessoa> pessoas = new ArrayList<>(c.getCount());
        if (c.moveToFirst()){
            do {
                Pessoa pessoa = getPessoaFromCursor(c);
                pessoas.add(pessoa);
            } while(c.moveToNext());
        }
        c.close();
        return pessoas;
    }

    @NonNull
    static Pessoa getPessoaFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUNA_ID));
        String nome = c.getString(c.getColumnIndex(COLUNA_NOME));
        String email = c.getString(c.getColumnIndex(COLUNA_EMAIL));
        Pessoa pessoa = new Pessoa(id, nome, email);
        try {
            pessoa.setHorarioEntrada(getData(c.getString(c.getColumnIndex(COLUNA_ENTRADA))));
            pessoa.setHorarioSaida(getData(c.getString(c.getColumnIndex(COLUNA_SAIDA))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pessoa;
    }

    public void inserirPessoa(Pessoa pessoa) {
        ContentValues contentValues = getContentValuesFromPessoa(pessoa);
        db.insert(TABELA_PESSOA, null, contentValues);
    }

    public void updatePessoa(Pessoa pessoa) {
        ContentValues contentValues = getContentValuesFromPessoa(pessoa);
        db.update(TABELA_PESSOA, contentValues, "id=?", new String[]{ String.valueOf(pessoa.getId()) });
    }

    public void deletarPessoa(int id) {
        db.delete(TABELA_PESSOA, "id=?", new String[] { String.valueOf(id) });
    }

    @NonNull
    private ContentValues getContentValuesFromPessoa(Pessoa pessoa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_NOME, pessoa.getNome());
        contentValues.put(COLUNA_EMAIL, pessoa.getEmail());
        if(pessoa.getHorarioEntrada() != null) {
            contentValues.put(COLUNA_ENTRADA, dateFormat.format(pessoa.getHorarioEntrada()));
        }
        if(pessoa.getHorarioSaida() != null) {
            contentValues.put(COLUNA_SAIDA, dateFormat.format(pessoa.getHorarioSaida()));
        }
        return contentValues;
    }

    private static Date getData(String data) throws ParseException {
        return data == null ? null : dateFormat.parse(data);
    }
}
