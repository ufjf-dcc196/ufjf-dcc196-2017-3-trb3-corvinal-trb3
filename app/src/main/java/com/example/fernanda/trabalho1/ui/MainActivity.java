package com.example.fernanda.trabalho1.ui;

import android.content.Intent;
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
import com.example.fernanda.trabalho1.model.Livro;
import com.example.fernanda.trabalho1.model.Pessoa;
import com.example.fernanda.trabalho1.model.Reserva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.fernanda.trabalho1.ui.CadastroLivroActivity.CADASTRO_LIVRO_KEY;
import static com.example.fernanda.trabalho1.ui.CadastroPessoaActivity.CADASTRO_PESSOA_KEY;
import static com.example.fernanda.trabalho1.ui.CadastroReservaActivity.CADASTRO_RESERVA_KEY;
import static com.example.fernanda.trabalho1.ui.LivroActivity.LIVRO_KEY;
import static com.example.fernanda.trabalho1.ui.PessoaActivity.PESSOA_KEY;



public class MainActivity extends AppCompatActivity {

    private static final int CADASTRO_PESSOA_REQUEST_CODE = 10;
    private static final int CADASTRO_LIVRO_REQUEST_CODE = 20;
    private static final int CADASTRO_RESERVA_REQUEST_CODE = 30;
    private static final int LIVRO_REQUEST_CODE = 40;
    private static final int PESSOA_REQUEST_CODE = 50;

    private static List<Pessoa> pessoas;
    private static List<Livro> livros;
    private static List<Reserva> reservas;

    private ListView lvPessoas;
    private ListView lvLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularDados();
        setupListView();
        setupButtons();
    }

    private void popularDados() {
        pessoas = new ArrayList<>(Arrays.asList(new Pessoa(0, "Gabriel", "gabriel@a.b"),
                new Pessoa(1, "Fernanda", "fernanda@a.b"), new Pessoa(2, "Thassya", "thassya@a.b")));
        livros = new ArrayList<>(Arrays.asList(new Livro(0, "Harry Potter", "Rocco", "1996"),
                new Livro(1, "Game of Thrones", "Leya", "2005"),
                new Livro(2, "Percy Jackson", "Abril", "2002")));
        reservas = new ArrayList<>();
    }

    private void setupListView(){

        setupListViewPessoa();

        setupListViewLivro();
    }

    private void setupListViewPessoa() {
        final ListAdapter pessoasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,pessoas);
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
                return true;
            }
        });

        lvPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PessoaActivity.class);
                intent.putExtra(PESSOA_KEY, pessoas.get(i));
                startActivityForResult(intent, PESSOA_REQUEST_CODE);
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
                intent.putParcelableArrayListExtra(LivroActivity.RESERVAS_KEY, (ArrayList<Reserva>) reservas);
                startActivityForResult(intent, LIVRO_REQUEST_CODE);
            }
        });
    }

    private void setupButtons() {
        Button btnCadastrarPessoa = (Button) findViewById(R.id.btn_main_cadastrar_pessoa);
        btnCadastrarPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroPessoaActivity.class);
                intent.putExtra(CadastroPessoaActivity.ID_PESSOA_KEY, pessoas.size());
                startActivityForResult(intent, CADASTRO_PESSOA_REQUEST_CODE);
            }
        });

        Button btnCadastrarLivro = (Button) findViewById(R.id.btn_main_cadastrar_livro);
        btnCadastrarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroLivroActivity.class);
                intent.putExtra(CadastroLivroActivity.ID_LIVRO_KEY, livros.size());
                startActivityForResult(intent, CADASTRO_LIVRO_REQUEST_CODE);
            }
        });

        Button btnCadastrarReserva = (Button) findViewById(R.id.btn_main_reservar_livro);
        btnCadastrarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroReservaActivity.class);
                intent.putParcelableArrayListExtra(CadastroReservaActivity.LIVROS_KEY,
                        (ArrayList<Livro>) livros);
                intent.putParcelableArrayListExtra(CadastroReservaActivity.PESSOAS_KEY,
                        (ArrayList<Pessoa>) pessoas);
                startActivityForResult(intent, CADASTRO_RESERVA_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case CADASTRO_PESSOA_REQUEST_CODE:
                    Pessoa pessoa = data.getParcelableExtra(CADASTRO_PESSOA_KEY);
                    pessoas.add(pessoa);
                    ((BaseAdapter) lvPessoas.getAdapter()).notifyDataSetChanged();
                    break;
                case CADASTRO_LIVRO_REQUEST_CODE:
                    Livro livro = data.getParcelableExtra(CADASTRO_LIVRO_KEY);
                    livros.add(livro);
                    ((BaseAdapter) lvLivros.getAdapter()).notifyDataSetChanged();
                    break;
                case CADASTRO_RESERVA_REQUEST_CODE:
                    Reserva reserva = data.getParcelableExtra(CADASTRO_RESERVA_KEY);
                    reservas.add(reserva);
                    break;
                case LIVRO_REQUEST_CODE:
                    Livro livroEditado = data.getParcelableExtra(LIVRO_KEY);
                    livros.set(livroEditado.getId(), livroEditado);
                    ((BaseAdapter) lvLivros.getAdapter()).notifyDataSetChanged();
                    break;
                case PESSOA_REQUEST_CODE:
                    Pessoa pessoaEditada = data.getParcelableExtra(PESSOA_KEY);
                    pessoas.set(pessoaEditada.getId(), pessoaEditada);
                    ((BaseAdapter) lvPessoas.getAdapter()).notifyDataSetChanged();
                    break;
            }
        }
    }
}
