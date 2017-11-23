package com.example.fernanda.trabalho1.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.fernanda.trabalho1.model.Livro;

import java.util.ArrayList;
import java.util.List;

import static com.example.fernanda.trabalho1.dao.LivroConstantes.COLUNA_ANO_PUBLICACAO;
import static com.example.fernanda.trabalho1.dao.LivroConstantes.COLUNA_EDITORA;
import static com.example.fernanda.trabalho1.dao.LivroConstantes.COLUNA_ID;
import static com.example.fernanda.trabalho1.dao.LivroConstantes.COLUNA_TITULO;
import static com.example.fernanda.trabalho1.dao.LivroConstantes.TABELA_LIVRO;

public class LivroDao {

    private final SQLiteDatabase db;

    public LivroDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Livro> getLivros() {
        String querySelect = "SELECT * FROM " + TABELA_LIVRO + ";";
        final Cursor c = db.rawQuery(querySelect, null);
        c.moveToFirst();
        List<Livro> livros = new ArrayList<>(c.getCount());
        if (c.moveToFirst()){
            do {
                Livro livro = getLivroFromCursor(c);
                livros.add(livro);
            } while(c.moveToNext());
        }
        c.close();
        return livros;
    }

    @NonNull
    static Livro getLivroFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUNA_ID));
        String titulo = c.getString(c.getColumnIndex(COLUNA_TITULO));
        String editora = c.getString(c.getColumnIndex(COLUNA_EDITORA));
        String anoPublicacao = c.getString(c.getColumnIndex(COLUNA_ANO_PUBLICACAO));
        return new Livro(id, titulo, editora, anoPublicacao);
    }

    public void inserirLivro(Livro livro) {
        ContentValues contentValues = getContentValuesFromLivro(livro);
        db.insert(TABELA_LIVRO, null, contentValues);
    }

    public void updateLivro(Livro livro) {
        ContentValues contentValues = getContentValuesFromLivro(livro);
        db.update(TABELA_LIVRO, contentValues, "id=?", new String[]{ String.valueOf(livro.getId()) });
    }

    public void deletarLivro(int id) {
        db.delete(TABELA_LIVRO, "id=?", new String[] { String.valueOf(id) });
    }

    @NonNull
    private ContentValues getContentValuesFromLivro(Livro livro) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_TITULO, livro.getTitulo());
        contentValues.put(COLUNA_EDITORA, livro.getEditora());
        contentValues.put(COLUNA_ANO_PUBLICACAO, livro.getAnoPublicacao());
        return contentValues;
    }
}
