package com.example.fernanda.trabalho3.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.fernanda.trabalho3.R;
import com.example.fernanda.trabalho3.dao.ContratoBanco;
import com.example.fernanda.trabalho3.dao.EstadoTarefaDao;
import com.example.fernanda.trabalho3.dao.TarefaDao;
import com.example.fernanda.trabalho3.dao.TagDao;
import com.example.fernanda.trabalho3.model.EstadoTarefa;
import com.example.fernanda.trabalho3.model.Tag;
import com.example.fernanda.trabalho3.model.Tarefa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.fernanda.trabalho3.ui.TarefaActivity.TAREFA_KEY;
import static com.example.fernanda.trabalho3.ui.TagActivity.TAG_KEY;

public class MainActivity
        extends AppCompatActivity {

    public static final String NOME_BD = "bd_trab3";

    private List<Tag> tags;
    private List<Tarefa> tarefas;

    private TagDao tagDao;
    private TarefaDao tarefaDao;
    private EstadoTarefaDao estadoTarefaDao;

    private ListView lvTags;
    private ListView lvTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarDb();
        popularDados();
        setupListView();
        setupButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTags();
        updateTarefas();
    }

    private void updateTags() {
        tags.clear();
        tags.addAll(tagDao.getTags());
        updateAdapter(lvTags);
    }

    private void updateTarefas() {
        tarefas.clear();
        tarefas.addAll(tarefaDao.getTarefas());
        updateAdapter(lvTarefas);
    }

    private void updateAdapter(ListView listView) {
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    private void criarDb() {
        SQLiteDatabase db = openOrCreateDatabase(NOME_BD, MODE_PRIVATE, null);
        ContratoBanco.criarTabelas(db);
        tagDao = new TagDao(db);
        tarefaDao = new TarefaDao(db);
        estadoTarefaDao = new EstadoTarefaDao(db);
    }

    private void popularDados() {
        List<EstadoTarefa> estados = Arrays.asList(new EstadoTarefa(0, 1, "Muito fácil"),
                new EstadoTarefa(0, 2, "Fácil"), new EstadoTarefa(0, 3, "Médio"),
                new EstadoTarefa(0, 4, "Difícil"), new EstadoTarefa(0, 5, "Muito difícil"));

        for (EstadoTarefa estado : estados) estadoTarefaDao.inserirEstado(estado);
        estados = estadoTarefaDao.getEstados();

        tags = new ArrayList<>(Arrays.asList(new Tag(0, "Estudo"),
                new Tag(0, "Trabalho"), new Tag(0, "Importante")));
        tarefas = new ArrayList<>(Arrays.asList(
                new Tarefa(0, "TRB1 Android", "Desc 1", estados.get(0), Collections.<Tag>emptyList()),
                new Tarefa(0, "TRB2 Android", "Desc 2", estados.get(1), Collections.singletonList(tags.get(1))),
                new Tarefa(0, "TRB3 Android", "Desc 3", estados.get(4), Collections.singletonList(tags.get(0)))));

        for (Tag tag : tags) tagDao.inserirTag(tag);

        for (Tarefa tarefa : tarefas) tarefaDao.inserirTarefa(tarefa);
    }

    private void setupListView() {
        setupListViewTags();
        setupListViewTarefas();
    }

    private void setupListViewTags() {
        final ListAdapter tarefasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tags);
        lvTags = (ListView) findViewById(R.id.lv_tags);
        lvTags.setAdapter(tarefasAdapter);

        lvTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TagActivity.class);
                intent.putExtra(TAG_KEY, tags.get(i));
                startActivity(intent);
            }
        });
    }

    private void setupListViewTarefas() {
        final ListAdapter livrosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tarefas);
        lvTarefas = (ListView) findViewById(R.id.lv_tarefas);
        lvTarefas.setAdapter(livrosAdapter);

        lvTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                intent.putExtra(TAREFA_KEY, tarefas.get(i));
                startActivity(intent);
            }
        });
    }

    private void setupButtons() {
        Button btnCadastrarTag = (Button) findViewById(R.id.btn_main_cadastrar_tag);
        btnCadastrarTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroTagActivity.class);
                startActivity(intent);
            }
        });

        Button btnCadastrarTarefa = (Button) findViewById(R.id.btn_main_cadastrar_tarefa);
        btnCadastrarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroTarefaActivity.class);
                startActivity(intent);
            }
        });
    }
}
