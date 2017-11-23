package com.example.fernanda.trabalho1.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.fernanda.trabalho1.R;
import com.example.fernanda.trabalho1.dao.ContratoBanco;
import com.example.fernanda.trabalho1.dao.LivroDao;
import com.example.fernanda.trabalho1.dao.PessoaDao;
import com.example.fernanda.trabalho1.model.Livro;
import com.example.fernanda.trabalho1.model.Pessoa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.fernanda.trabalho1.ui.LivroActivity.LIVRO_KEY;
import static com.example.fernanda.trabalho1.ui.PessoaActivity.PESSOA_KEY;



public class MainActivity extends AppCompatActivity {

    public static final String NOME_BD = "bd_bienal";

    private List<Pessoa> pessoas;
    private List<Livro> livros;

    private PessoaDao pessoaDao;
    private LivroDao livroDao;

    private ListView lvPessoas;
    private ListView lvLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarDb();
        popularDados();
        setupListView();
        setupButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePessoas();
        updateLivros();
    }

    private void updatePessoas() {
        pessoas.clear();
        pessoas.addAll(pessoaDao.getPessoas());
        updateAdapter(lvPessoas);
    }

    private void updateLivros() {
        livros.clear();
        livros.addAll(livroDao.getLivros());
        updateAdapter(lvLivros);
    }

    private void updateAdapter(ListView listView) {
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    private void criarDb() {
        SQLiteDatabase db = openOrCreateDatabase(NOME_BD, MODE_PRIVATE, null);
        ContratoBanco.criarTabelas(db);
        pessoaDao = new PessoaDao(db);
        livroDao = new LivroDao(db);
    }

    private void popularDados() {
        pessoas = new ArrayList<>(Arrays.asList(new Pessoa(4, "Gabriel2.0", "gabriel@a.b"),
                new Pessoa(1, "Fernanda", "fernanda@a.b"), new Pessoa(2, "Thassya", "thassya@a.b")));
        livros = new ArrayList<>(Arrays.asList(new Livro(0, "Harry Potter", "Rocco", "1996"),
                new Livro(1, "Game of Thrones", "Leya", "2005"),
                new Livro(2, "Percy Jackson", "Abril", "2002")));

        for(Pessoa pessoa : pessoas) pessoaDao.inserirPessoa(pessoa);

        for(Livro livro: livros) livroDao.inserirLivro(livro);
    }

    private void setupListView(){
        setupListViewPessoa();
        setupListViewLivro();
    }

    private void setupListViewPessoa() {
        final ListAdapter pessoasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pessoas);
        lvPessoas = (ListView) findViewById(R.id.lv_pessoas);
        lvPessoas.setAdapter(pessoasAdapter);
        lvPessoas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pessoa pessoa = pessoas.get(i);
                if(pessoa.getHorarioEntrada() == null) {
                    pessoa.setHorarioEntrada(new Date());
                    ViewUtils.showToast(MainActivity.this,
                            "Horário de entrada atualizado com sucesso");
                } else if(pessoa.getHorarioSaida() == null){
                    pessoa.setHorarioSaida(new Date());
                    ViewUtils.showToast(MainActivity.this,
                            "Horário de saída atualizado com sucesso");
                } else {
                    pessoa.setHorarioEntrada(null);
                    pessoa.setHorarioSaida(null);
                    ViewUtils.showToast(MainActivity.this, "Horários resetados com sucesso");
                }
                pessoaDao.updatePessoa(pessoa);
                return true;
            }
        });

        lvPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PessoaActivity.class);
                intent.putExtra(PESSOA_KEY, pessoas.get(i));
                startActivity(intent);
            }
        });
    }

    private void setupListViewLivro() {
        final ListAdapter livrosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,livros);
        lvLivros = (ListView) findViewById(R.id.lv_livros);
        lvLivros.setAdapter(livrosAdapter);

        lvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, LivroActivity.class);
                intent.putExtra(LIVRO_KEY, livros.get(i));
                startActivity(intent);
            }
        });
    }

    private void setupButtons() {
        Button btnCadastrarPessoa = (Button) findViewById(R.id.btn_main_cadastrar_pessoa);
        btnCadastrarPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroPessoaActivity.class);
                startActivity(intent);
            }
        });

        Button btnCadastrarLivro = (Button) findViewById(R.id.btn_main_cadastrar_livro);
        btnCadastrarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroLivroActivity.class);
                startActivity(intent);
            }
        });

        Button btnCadastrarReserva = (Button) findViewById(R.id.btn_main_reservar_livro);
        btnCadastrarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroReservaActivity.class);
                startActivity(intent);
            }
        });
    }
}
