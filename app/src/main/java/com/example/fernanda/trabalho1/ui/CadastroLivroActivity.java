package com.example.fernanda.trabalho1.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.dao.LivroDao;
import com.example.fernanda.trabalho1.model.Livro;

import static com.example.fernanda.trabalho1.ui.MainActivity.NOME_BD;

public class CadastroLivroActivity extends AppCompatActivity {

    static final String CADASTRO_LIVRO_KEY = "cadastro_livro_key";
    static final String ID_LIVRO_KEY = "id_livro_key";

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
                    Livro livro = new Livro(0, titulo, editora , anoPublicacao);
                    SQLiteDatabase db = openOrCreateDatabase(NOME_BD, MODE_PRIVATE, null);
                    new LivroDao(db).inserirLivro(livro);
                    finish();
                }
            }
        });
    }
}
