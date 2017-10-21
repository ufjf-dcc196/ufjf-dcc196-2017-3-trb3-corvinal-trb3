package com.example.fernanda.trabalho1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.model.Livro;
import com.example.fernanda.trabalho1.model.Reserva;

import java.util.ArrayList;
import java.util.List;

public class LivroActivity extends AppCompatActivity {

    static final String LIVRO_KEY = "livro_key";
    static final String RESERVAS_KEY = "reservas_key";

    private EditText etTitulo;
    private EditText etEditora;
    private EditText etAnoPublicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro);

        Livro livro = getIntent().getParcelableExtra(LIVRO_KEY);

        setupViews(livro.getId());
        showData(livro);
        setupListView(livro);
    }

    private void setupViews(final int livroId) {
        etTitulo = (EditText) findViewById(R.id.et_livro_titulo);
        etEditora = (EditText) findViewById(R.id.et_livro_editora);
        etAnoPublicacao = (EditText) findViewById(R.id.et_livro_ano_publicacao);

        Button btnSalvar = (Button) findViewById(R.id.btn_livro_salvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = etTitulo.getText().toString();
                String editora = etEditora.getText().toString();
                String anoPublicacao = etAnoPublicacao.getText().toString();

                if(titulo.isEmpty()){
                    ViewUtils.showToast(LivroActivity.this, "O titulo é obrigatório!");
                } else if (editora.isEmpty()){
                    ViewUtils.showToast(LivroActivity.this, "A editora é obrigatório!");
                } else if(anoPublicacao.isEmpty()){
                    ViewUtils.showToast(LivroActivity.this, "O ano de publicação é obrigatório!");
                } else {
                    Livro livroEditado = new Livro(livroId, titulo, editora, anoPublicacao);
                    Intent intent = new Intent();
                    intent.putExtra(LIVRO_KEY, livroEditado);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void setupListView(Livro livro){
        final List<Reserva> reservas = getIntent().getParcelableArrayListExtra(RESERVAS_KEY);
        final List<Reserva> reservasLivro = new ArrayList<>();

        for (Reserva r : reservas) {
            if(r.getLivro().getId() == livro.getId()){
                reservasLivro.add(r);
            }
        }

        final ListAdapter reservasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservasLivro);
        ListView lvPessoas = (ListView) findViewById(R.id.lv_pessoas_reserva);
        lvPessoas.setAdapter(reservasAdapter);
    }

    private void showData(Livro livro) {
        etTitulo.setText(livro.getTitulo());
        etEditora.setText(livro.getEditora());
        etAnoPublicacao.setText(livro.getAnoPublicacao());
    }
}