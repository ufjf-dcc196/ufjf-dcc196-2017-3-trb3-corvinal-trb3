package com.example.fernanda.trabalho1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.model.Pessoa;

public class CadastroPessoaActivity extends AppCompatActivity {

    static final String CADASTRO_PESSOA_KEY = "cadastro_pessoa_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        final EditText etNome = (EditText) findViewById(R.id.et_pessoa_nome);
        final EditText etEmail = (EditText) findViewById(R.id.et_pessoa_email);
        final Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_pessoa);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = etNome.getText().toString();
                String email = etEmail.getText().toString();
                if(nome.isEmpty()){
                    ViewUtils.showToast(CadastroPessoaActivity.this, "O nome é obrigatório!");
                } else if (email.isEmpty()){
                    ViewUtils.showToast(CadastroPessoaActivity.this, "O email é obrigatório!");
                } else {
                    Pessoa pessoa = new Pessoa(nome, email);
                    Intent intent = new Intent();
                    intent.putExtra(CADASTRO_PESSOA_KEY, pessoa);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}