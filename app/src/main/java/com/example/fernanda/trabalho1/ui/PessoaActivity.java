package com.example.fernanda.trabalho1.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.dao.PessoaDao;
import com.example.fernanda.trabalho1.model.Pessoa;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.example.fernanda.trabalho1.ui.MainActivity.NOME_BD;

public class PessoaActivity extends AppCompatActivity {

    static final String PESSOA_KEY = "pessoa_key";

    private EditText etNome;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        Pessoa pessoa = getIntent().getParcelableExtra(PESSOA_KEY);

        setupViews(pessoa.getId());
        showData(pessoa);
    }

    private void setupViews(final int pessoaId) {
        etNome = (EditText) findViewById(R.id.et_pessoa_nome);
        etEmail = (EditText) findViewById(R.id.et_pessoa_email);

        Button btnSalvar = (Button) findViewById(R.id.btn_pessoa_salvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = etNome.getText().toString();
                String email = etEmail.getText().toString();

                if(nome.isEmpty()){
                    ViewUtils.showToast(PessoaActivity.this, "O nome é obrigatório!");
                } else if (email.isEmpty()){
                    ViewUtils.showToast(PessoaActivity.this, "O email é obrigatório!");
                } else {
                    Pessoa pessoaEditada = new Pessoa(pessoaId, nome, email);
                    SQLiteDatabase db = openOrCreateDatabase(NOME_BD, MODE_PRIVATE, null);
                    new PessoaDao(db).updatePessoa(pessoaEditada);
                    finish();
                }
            }
        });
    }

    private void showData(Pessoa pessoa) {
        TextView tvEntrada = (TextView) findViewById(R.id.tv_entrada);
        TextView tvSaida = (TextView) findViewById(R.id.tv_saida);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.US);
        String horarioEntrada = pessoa.getHorarioEntrada() == null ?
                getString(R.string.pessoa_data_inexistente) :
                sdf.format(pessoa.getHorarioEntrada());
        String horarioSaida = pessoa.getHorarioSaida() == null ?
                getString(R.string.pessoa_data_inexistente) :
                sdf.format(pessoa.getHorarioSaida());

        etNome.setText(pessoa.getNome());
        etEmail.setText(pessoa.getEmail());
        tvEntrada.setText(getString(R.string.pessoa_format_entrada, horarioEntrada));
        tvSaida.setText(getString(R.string.pessoa_format_saida, horarioSaida));
    }
}