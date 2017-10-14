package com.example.fernanda.trabalho1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.model.Livro;
import com.example.fernanda.trabalho1.model.Pessoa;
import com.example.fernanda.trabalho1.model.Reserva;

import java.util.List;

public class CadastroReservaActivity extends AppCompatActivity {

    static final String CADASTRO_RESERVA_KEY = "cadastro_reserva_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_reserva);

        final Spinner spLivro = (Spinner)findViewById(R.id.et_livro);
        final Spinner spPessoa = (Spinner)findViewById(R.id.et_pessoa);
        final Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_reserva);

        List<Livro> livros = MainActivity.RetornaLivros();
        List<Pessoa> pessoas = MainActivity.RetornaPessoas();

        ArrayAdapter<Livro> livroArrayAdapter = new ArrayAdapter<Livro>(this, android.R.layout.simple_spinner_dropdown_item, livros);
        livroArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spLivro.setAdapter(livroArrayAdapter);

        ArrayAdapter<Pessoa> pessoaArrayAdapter = new ArrayAdapter<Pessoa>(this, android.R.layout.simple_spinner_dropdown_item, pessoas);
        pessoaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spPessoa.setAdapter(pessoaArrayAdapter);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int livroIndice = spLivro.getSelectedItemPosition();
                int pessoaIndice = spPessoa.getSelectedItemPosition();

                String livroString = spLivro.getSelectedItem().toString();
                String pessoaString =  spPessoa.getSelectedItem().toString();

                if(livroString.isEmpty()){
                    ViewUtils.showToast(CadastroReservaActivity.this, "O livro é obrigatório!");
                } else if (pessoaString.isEmpty()){
                    ViewUtils.showToast(CadastroReservaActivity.this, "A pessoa é obrigatório!");
                } else {
                    Livro livro = MainActivity.RetornaLivroPosicao(livroIndice);
                    Pessoa pessoa = MainActivity.RetornaPessoaPosicao(pessoaIndice);

                    Reserva reserva = new Reserva(pessoa , livro);
                    Intent intent = new Intent();
                    intent.putExtra(CADASTRO_RESERVA_KEY, reserva);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
        });
    }
}
