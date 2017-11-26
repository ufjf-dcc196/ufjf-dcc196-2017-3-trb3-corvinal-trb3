package com.example.fernanda.trabalho3.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fernanda.trabalho3.R;
import com.example.fernanda.trabalho3.dao.EstadoTarefaDao;
import com.example.fernanda.trabalho3.dao.TagDao;
import com.example.fernanda.trabalho3.dao.TarefaDao;
import com.example.fernanda.trabalho3.model.EstadoTarefa;
import com.example.fernanda.trabalho3.model.Tag;
import com.example.fernanda.trabalho3.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

import io.apptik.widget.multiselectspinner.MultiSelectSpinner;

public class TarefaActivity
        extends AppCompatActivity {

    static final String TAREFA_KEY = "tarefa_key";

    private EditText etTitulo;
    private EditText etEditora;
    private Spinner spEstado;
    private MultiSelectSpinner mspTags;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        db = openOrCreateDatabase(MainActivity.NOME_BD, MODE_PRIVATE, null);

        Tarefa tarefa = getIntent().getParcelableExtra(TAREFA_KEY);
        final List<Tag> tags = new TagDao(db).getTags();
        final List<EstadoTarefa> estados = new EstadoTarefaDao(db).getEstados();

        setupViews(tarefa.getId(), tags, estados);
        showData(tarefa, tags, estados);
    }

    private void setupViews(final int tarefaId, List<Tag> tags, List<EstadoTarefa> estados) {
        etTitulo = (EditText) findViewById(R.id.et_tarefa_titulo);
        etEditora = (EditText) findViewById(R.id.et_tarefa_descricao);
        spEstado = (Spinner) findViewById(R.id.sp_tarefa_estado);
        mspTags = (MultiSelectSpinner) findViewById(R.id.msp_tarefa_tags);

        ArrayAdapter<EstadoTarefa> estadosAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, estados);
        spEstado.setAdapter(estadosAdapter);


        ArrayAdapter<Tag> tagsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, tags);
        mspTags.setListAdapter(tagsAdapter);

        Button btnSalvar = (Button) findViewById(R.id.btn_tarefa_salvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = etTitulo.getText().toString();
                String descricao = etEditora.getText().toString();
                EstadoTarefa estado = (EstadoTarefa) spEstado.getSelectedItem();

                if(titulo.isEmpty()){
                    ViewUtils.showToast(TarefaActivity.this, "O titulo é obrigatório!");
                } else if (descricao.isEmpty()){
                    ViewUtils.showToast(TarefaActivity.this, "A descricao é obrigatório!");
                } else if(estado == null){
                    ViewUtils.showToast(TarefaActivity.this, "O estado é obrigatório!");
                } else {
                    Tarefa tarefaEditado =
                            new Tarefa(tarefaId, titulo, descricao, estado, getTagsSelecionadas());
                    new TarefaDao(db).updateTarefa(tarefaEditado);
                    finish();
                }
            }
        });
    }

    private void showData(Tarefa tarefa, List<Tag> tags, List<EstadoTarefa> estados) {
        etTitulo.setText(tarefa.getTitulo());
        etEditora.setText(tarefa.getDescricao());

        for(int i = 0; i < estados.size(); ++i) {
            if(estados.get(i).equals(tarefa.getEstado())) {
                spEstado.setSelection(i);
                break;
            }
        }

        for(int i = 0; i < tags.size(); ++i) {
            mspTags.selectItem(i, tarefa.getTags().contains(tags.get(i)));
        }
    }

    private List<Tag> getTagsSelecionadas() {
        boolean[] selecionados = mspTags.getSelected();
        List<Tag> tags = new ArrayList<>(selecionados.length);

        for(int i = 0; i < selecionados.length; ++i) {
            if(selecionados[i]) tags.add((Tag) mspTags.getListAdapter().getItem(i));
        }

        return tags;
    }
}