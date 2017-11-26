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

import static com.example.fernanda.trabalho3.ui.MainActivity.NOME_BD;

public class CadastroTarefaActivity extends AppCompatActivity {

    private MultiSelectSpinner mspTags;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);

        db = openOrCreateDatabase(NOME_BD, MODE_PRIVATE, null);

        final EditText etTitulo = (EditText) findViewById(R.id.et_tarefa_titulo);
        final EditText etDescricao = (EditText) findViewById(R.id.et_tarefa_descricao);
        final Spinner spEstado = (Spinner) findViewById(R.id.sp_tarefa_estado);
        final Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_livro);
        mspTags = (MultiSelectSpinner) findViewById(R.id.msp_tarefa_tags);

        ArrayAdapter<EstadoTarefa> estadosAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, new EstadoTarefaDao(db).getEstados());
        spEstado.setAdapter(estadosAdapter);

        ArrayAdapter<Tag> tagsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, new TagDao(db).getTags());
        mspTags.setListAdapter(tagsAdapter);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = etTitulo.getText().toString();
                String descricao = etDescricao.getText().toString();
                EstadoTarefa estado = (EstadoTarefa) spEstado.getSelectedItem();

                if(titulo.isEmpty()){
                    ViewUtils.showToast(CadastroTarefaActivity.this, "O titulo é obrigatório!");
                } else if (descricao.isEmpty()){
                    ViewUtils.showToast(CadastroTarefaActivity.this, "A descricao é obrigatório!");
                } else if(estado == null){
                    ViewUtils.showToast(CadastroTarefaActivity.this, "O estado da tarefa é obrigatório!");
                } else {
                    Tarefa tarefa = new Tarefa(0, titulo, descricao, estado, getTagsSelecionadas());
                    new TarefaDao(db).inserirTarefa(tarefa);
                    finish();
                }
            }
        });
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
