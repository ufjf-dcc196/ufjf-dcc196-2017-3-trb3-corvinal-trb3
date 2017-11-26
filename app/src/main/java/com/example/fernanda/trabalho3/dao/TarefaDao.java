package com.example.fernanda.trabalho3.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.fernanda.trabalho3.model.EstadoTarefa;
import com.example.fernanda.trabalho3.model.Tag;
import com.example.fernanda.trabalho3.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

import static com.example.fernanda.trabalho3.dao.EstadoTarefaConstantes.TABELA_ESTADO_TAREFA;
import static com.example.fernanda.trabalho3.dao.TarefaConstantes.COLUNA_DESCRICAO;
import static com.example.fernanda.trabalho3.dao.TarefaConstantes.COLUNA_ESTADO;
import static com.example.fernanda.trabalho3.dao.TarefaConstantes.COLUNA_ID;
import static com.example.fernanda.trabalho3.dao.TarefaConstantes.COLUNA_TITULO;
import static com.example.fernanda.trabalho3.dao.TarefaConstantes.TABELA_TAREFA;

public class TarefaDao {

    private final SQLiteDatabase db;

    private final TarefaTagDao tarefaTagDao;

    private final TagDao tagDao;

    public TarefaDao(SQLiteDatabase db) {
        this.db = db;
        tarefaTagDao = new TarefaTagDao(db);
        tagDao = new TagDao(db);
    }

    public List<Tarefa> getTarefas() {
        String querySelect = getEstadoJoinSelectQuery();
        final Cursor c = db.rawQuery(querySelect, null);
        c.moveToFirst();
        List<Tarefa> tarefas = new ArrayList<>(c.getCount());
        if (c.moveToFirst()){
            do {
                Tarefa tarefa = getTarefaFromCursor(c);
                tarefas.add(tarefa);
            } while(c.moveToNext());
        }
        c.close();
        return tarefas;
    }

    public List<Tarefa> getTarefasComTag(Tag tag) {
        String tagSelectQuery = getEstadoTagSelectJoinQuery(tag.getId());
        final Cursor c = db.rawQuery(tagSelectQuery, null);
        c.moveToFirst();
        List<Tarefa> tarefas = new ArrayList<>(c.getCount());
        if (c.moveToFirst()){
            do {
                Tarefa tarefa = getTarefaFromCursor(c);
                tarefas.add(tarefa);
            } while(c.moveToNext());
        }
        c.close();
        return tarefas;
    }

    @NonNull
    private String getEstadoJoinSelectQuery() {
        return "SELECT * FROM " + TABELA_TAREFA + " tt INNER JOIN " + TABELA_ESTADO_TAREFA + " tet " +
                "ON tt." + COLUNA_ESTADO + " = tet." + EstadoTarefaConstantes.COLUNA_ID +
                " ORDER BY tt." + COLUNA_ESTADO + ";";
    }

    @NonNull
    private String getEstadoTagSelectJoinQuery(int tagId) {
        return "SELECT  * FROM " + TarefaTagConstantes.TABELA_TAREFA_TAG + " ttt " +
                "INNER JOIN " + TarefaConstantes.TABELA_TAREFA + " tar ON ttt." +
                TarefaTagConstantes.COLUNA_ID_TAREFA + " = tar." + TarefaConstantes.COLUNA_ID +
                " INNER JOIN " + TagConstantes.TABELA_TAG + " tag ON ttt." +
                TarefaTagConstantes.COLUNA_ID_TAG + " = tag." + TagConstantes.COLUNA_ID +
                " INNER JOIN " + TABELA_ESTADO_TAREFA + " tet " +
                "ON tar." + COLUNA_ESTADO + " = tet." + EstadoTarefaConstantes.COLUNA_ID +
                " WHERE ttt." + TarefaTagConstantes.COLUNA_ID_TAG + " = " + tagId +
                " ORDER BY tar." + COLUNA_ESTADO + ";";
    }

    @NonNull
    private Tarefa getTarefaFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUNA_ID));
        String titulo = c.getString(c.getColumnIndex(COLUNA_TITULO));
        String editora = c.getString(c.getColumnIndex(COLUNA_DESCRICAO));
        EstadoTarefa estado = EstadoTarefaDao.getEstadoFromCursor(c);
        return new Tarefa(id, titulo, editora, estado, tagDao.getTagsOfTarefa(id));
    }

    public void inserirTarefa(Tarefa tarefa) {
        ContentValues contentValues = getContentValuesFromTarefa(tarefa);
        tarefa.setId((int) db.insert(TABELA_TAREFA, null, contentValues));
        inserirTagsRelacionadas(tarefa);
    }

    public void updateTarefa(Tarefa tarefa) {
        ContentValues contentValues = getContentValuesFromTarefa(tarefa);
        db.update(TABELA_TAREFA, contentValues, TarefaConstantes.COLUNA_ID + "=?",
                new String[]{ String.valueOf(tarefa.getId()) });
        deletarTagsRelacionadas(tarefa);
        inserirTagsRelacionadas(tarefa);
    }

    private void inserirTagsRelacionadas(Tarefa tarefa) {
        for(Tag tag : tarefa.getTags()) {
            tarefaTagDao.inserirTarefaTag(tarefa.getId(), tag.getId());
        }
    }

    private void deletarTagsRelacionadas(Tarefa tarefa) {
        tarefaTagDao.deletarTaregaTags(tarefa.getId());
    }

    @NonNull
    private ContentValues getContentValuesFromTarefa(Tarefa tarefa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_TITULO, tarefa.getTitulo());
        contentValues.put(COLUNA_DESCRICAO, tarefa.getDescricao());
        contentValues.put(COLUNA_ESTADO, tarefa.getEstado().getId());
        return contentValues;
    }
}
