package com.example.fernanda.trabalho3.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fernanda.trabalho3.R;
import com.example.fernanda.trabalho3.dao.TagDao;
import com.example.fernanda.trabalho3.model.Tag;

import static com.example.fernanda.trabalho3.ui.MainActivity.NOME_BD;

public class CadastroTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tag);

        final EditText etNome = (EditText) findViewById(R.id.et_tag_nome);
        final Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_tag);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = etNome.getText().toString();

                if(nome.isEmpty()){
                    ViewUtils.showToast(CadastroTagActivity.this, "O nome é obrigatório!");
                } else {
                    Tag tag = new Tag(0, nome);
                    SQLiteDatabase db = openOrCreateDatabase(NOME_BD, MODE_PRIVATE, null);
                    new TagDao(db).inserirTag(tag);
                    finish();
                }
            }
        });
    }
}