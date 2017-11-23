package com.example.fernanda.trabalho1.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.fernanda.trabalho1.model.Reserva;

import java.util.ArrayList;
import java.util.List;

import static com.example.fernanda.trabalho1.dao.LivroConstantes.TABELA_LIVRO;
import static com.example.fernanda.trabalho1.dao.PessoaConstantes.COLUNA_NOME;
import static com.example.fernanda.trabalho1.dao.PessoaConstantes.TABELA_PESSOA;
import static com.example.fernanda.trabalho1.dao.ReservaConstantes.COLUNA_ID_LIVRO;
import static com.example.fernanda.trabalho1.dao.ReservaConstantes.COLUNA_ID_PESSOA;
import static com.example.fernanda.trabalho1.dao.ReservaConstantes.TABELA_RESERVA;

public class ReservaDao {

    private final SQLiteDatabase db;

    public ReservaDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Reserva> getReservas() {
        String selectQuery = "SELECT  * FROM " + TABELA_RESERVA + " tr " +
                "INNER JOIN " + TABELA_LIVRO + " tl ON tr." + COLUNA_ID_LIVRO + " = tl." +
                LivroConstantes.COLUNA_ID + " INNER JOIN " + TABELA_PESSOA + " tp ON tr." +
                COLUNA_ID_PESSOA + " = tp." + PessoaConstantes.COLUNA_ID + ";";
        final Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        List<Reserva> reservas = new ArrayList<>(c.getCount());
        if (c.moveToFirst()){
            do {
                System.out.println(c.getInt(c.getColumnIndex(COLUNA_ID_PESSOA)));
                System.out.println(c.getInt(c.getColumnIndex(COLUNA_NOME)));
                Reserva reserva = new Reserva(
                        PessoaDao.getPessoaFromCursor(c), LivroDao.getLivroFromCursor(c));
                reservas.add(reserva);
            } while(c.moveToNext());
        }
        c.close();
        return reservas;
    }

    public void inserirReserva(Reserva reserva) {
        ContentValues contentValues = getContentValuesFromReserva(reserva);
        db.insert(TABELA_RESERVA, null, contentValues);
    }

    public void updateReserva(Reserva reserva) {
        ContentValues contentValues = getContentValuesFromReserva(reserva);
        db.update(TABELA_RESERVA, contentValues, COLUNA_ID_PESSOA + "=? AND " + COLUNA_ID_LIVRO + "=?",
                new String[]{ String.valueOf(reserva.getPessoa().getId()), String.valueOf(reserva.getLivro().getId()) });
    }

    public void deletarReserva(int id) {
        db.delete(TABELA_RESERVA, "id=?", new String[] { String.valueOf(id) });
    }

    @NonNull
    private ContentValues getContentValuesFromReserva(Reserva reserva) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_ID_PESSOA, reserva.getPessoa().getId());
        contentValues.put(COLUNA_ID_LIVRO, reserva.getLivro().getId());
        return contentValues;
    }
}
