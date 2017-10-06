package com.example.fernanda.trabalho1.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.model.Pessoa;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PessoaActivity extends AppCompatActivity {

    static final String PESSOA_KEY = "pessoa_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        Pessoa pessoa = getIntent().getParcelableExtra(PESSOA_KEY);
        showData(pessoa);
    }

    private void showData(Pessoa pessoa) {
        TextView tvNome = (TextView) findViewById(R.id.tv_nome);
        TextView tvEmail = (TextView) findViewById(R.id.tv_email);
        TextView tvEntrada = (TextView) findViewById(R.id.tv_entrada);
        TextView tvSaida = (TextView) findViewById(R.id.tv_saida);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.US);
        String horarioEntrada = pessoa.getHorarioEntrada() == null ?
                getString(R.string.pessoa_data_inexistente) :
                sdf.format(pessoa.getHorarioEntrada());
        String horarioSaida = pessoa.getHorarioSaida() == null ?
                getString(R.string.pessoa_data_inexistente) :
                sdf.format(pessoa.getHorarioSaida());

        tvNome.setText(getString(R.string.pessoa_format_nome, pessoa.getNome()));
        tvEmail.setText(getString(R.string.pessoa_format_email, pessoa.getEmail()));
        tvEntrada.setText(getString(R.string.pessoa_format_entrada, horarioEntrada));
        tvSaida.setText(getString(R.string.pessoa_format_saida, horarioSaida));
    }
}
