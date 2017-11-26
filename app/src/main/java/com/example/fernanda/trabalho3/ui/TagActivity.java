package com.example.fernanda.trabalho3.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.fernanda.trabalho3.R;
import com.example.fernanda.trabalho3.dao.TagDao;
import com.example.fernanda.trabalho3.dao.TarefaDao;
import com.example.fernanda.trabalho3.model.Tag;
import com.example.fernanda.trabalho3.model.Tarefa;

import java.util.List;

public class TagActivity
        extends AppCompatActivity {

    static final String TAG_KEY = "tag_key";

    private EditText etNome;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        Tag tag = getIntent().getParcelableExtra(TAG_KEY);
        db = openOrCreateDatabase(MainActivity.NOME_BD, MODE_PRIVATE, null);

        setupViews(tag.getId());
        showData(tag);
        setupListView(tag);
    }

    private void setupViews(final int tagId) {
        etNome = (EditText) findViewById(R.id.et_tag_nome);

        Button btnSalvar = (Button) findViewById(R.id.btn_tag_salvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = etNome.getText().toString();

                if(nome.isEmpty()){
                    ViewUtils.showToast(TagActivity.this, "O nome é obrigatório!");
                } else {
                    Tag tagEditada = new Tag(tagId, nome);
                    new TagDao(db).updateTag(tagEditada);
                    finish();
                }
            }
        });
    }

    private void setupListView(Tag tag){
        final List<Tarefa> tarefas = new TarefaDao(db).getTarefasComTag(tag);

        final ListAdapter reservasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tarefas);
        ListView lvTarefas = (ListView) findViewById(R.id.lv_tag_tarefas);
        lvTarefas.setAdapter(reservasAdapter);
    }

    private void showData(Tag tag) {
        etNome.setText(tag.getNome());
    }
}