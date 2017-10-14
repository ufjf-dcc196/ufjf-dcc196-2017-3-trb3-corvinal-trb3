package com.example.fernanda.trabalho1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.model.Livro;
import com.example.fernanda.trabalho1.model.Pessoa;
import com.example.fernanda.trabalho1.model.Reserva;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.fernanda.trabalho1.ui.PessoaActivity.PESSOA_KEY;

public class LivroActivity extends AppCompatActivity {

    private static ListView lvPessoas;
    static final String LIVRO_KEY = "livro_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro);

        Livro livro = getIntent().getParcelableExtra(LIVRO_KEY);
        showData(livro);
        setupListView(livro);
    }

    private void setupListView(Livro livro){
        final List<Reserva> reservas = MainActivity.RetornaReservas();
        final List<Reserva> reservasLivro = new ArrayList<>();

        for (Reserva r:reservas) {
            if(r.getLivro().getTitulo().equals(livro.getTitulo())){
                reservasLivro.add(r);
            }
        }

        final ListAdapter reservasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservasLivro);
        lvPessoas = (ListView) findViewById(R.id.lv_pessoas_reserva);
        lvPessoas.setAdapter(reservasAdapter);

    }

    private void showData(Livro livro) {
        TextView tvTitulo = (TextView) findViewById(R.id.tv_titulo);
        TextView tvEditora = (TextView) findViewById(R.id.tv_editora);
        TextView tvAnoPublicacao = (TextView) findViewById(R.id.tv_anoPublicacao);

        tvTitulo.setText(getString(R.string.livro_format_titulo, livro.getTitulo()));
        tvEditora.setText(getString(R.string.livro_format_editora, livro.getEditora()));
        tvAnoPublicacao.setText(getString(R.string.livro_format_anoPublicacao, livro.getAnoPublicacao()));

    }
}
