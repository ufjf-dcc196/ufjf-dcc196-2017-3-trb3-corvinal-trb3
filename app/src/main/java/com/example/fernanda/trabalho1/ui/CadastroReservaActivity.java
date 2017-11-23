package com.example.fernanda.trabalho1.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.dao.LivroDao;
import com.example.fernanda.trabalho1.dao.PessoaDao;
import com.example.fernanda.trabalho1.dao.ReservaDao;
import com.example.fernanda.trabalho1.model.Livro;
import com.example.fernanda.trabalho1.model.Pessoa;
import com.example.fernanda.trabalho1.model.Reserva;

import java.util.List;

import static com.example.fernanda.trabalho1.ui.MainActivity.NOME_BD;

public class CadastroReservaActivity extends AppCompatActivity {

    static final String CADASTRO_RESERVA_KEY = "cadastro_reserva_key";
    static final String LIVROS_KEY = "livros_key";
    static final String PESSOAS_KEY = "pessoas_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_reserva);

        final Spinner spLivro = (Spinner)findViewById(R.id.et_livro);
        final Spinner spPessoa = (Spinner)findViewById(R.id.et_pessoa);
        final Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_reserva);

        final SQLiteDatabase db = openOrCreateDatabase(NOME_BD, MODE_PRIVATE, null);
        final List<Livro> livros = new LivroDao(db).getLivros();
        final List<Pessoa> pessoas = new PessoaDao(db).getPessoas();

        ArrayAdapter<Livro> livroArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, livros);
        livroArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spLivro.setAdapter(livroArrayAdapter);

        ArrayAdapter<Pessoa> pessoaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pessoas);
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
                    Livro livro = livros.get(livroIndice);
                    Pessoa pessoa = pessoas.get(pessoaIndice);
                    Reserva reserva = new Reserva(pessoa , livro);
                    new ReservaDao(db).inserirReserva(reserva);
                    finish();
                }
            }
        });
    }
}
