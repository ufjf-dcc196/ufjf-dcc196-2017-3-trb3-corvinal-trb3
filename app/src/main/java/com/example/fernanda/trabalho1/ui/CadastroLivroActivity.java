package com.example.fernanda.trabalho1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.model.Livro;


/**
 * Created by fernanda on 13/10/17.
 */

public class CadastroLivroActivity extends AppCompatActivity {

    static final String CADASTRO_LIVRO_KEY = "cadastro_livro_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        final EditText etTitulo = (EditText) findViewById(R.id.et_livro_titulo);
        final EditText etEditora = (EditText) findViewById(R.id.et_livro_editora);
        final EditText etAnoPublicacao = (EditText) findViewById(R.id.et_livro_ano_publicacao);
        final Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_livro);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = etTitulo.getText().toString();
                String editora = etEditora.getText().toString();
                String anoPublicacao = etAnoPublicacao.getText().toString();

                if(titulo.isEmpty()){
                    ViewUtils.showToast(CadastroLivroActivity.this, "O titulo é obrigatório!");
                } else if (editora.isEmpty()){
                    ViewUtils.showToast(CadastroLivroActivity.this, "A editora é obrigatório!");
                } else if(anoPublicacao.isEmpty()){
                    ViewUtils.showToast(CadastroLivroActivity.this, "O ano de publicação é obrigatório!");
                } else {
                    Livro livro = new Livro(titulo, editora , anoPublicacao);
                    Intent intent = new Intent();
                    intent.putExtra(CADASTRO_LIVRO_KEY, livro);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
