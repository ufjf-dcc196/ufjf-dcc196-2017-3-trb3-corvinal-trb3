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

import static com.example.fernanda.trabalho1.ui.CadastroPessoaActivity.CADASTRO_PESSOA_KEY;
import static com.example.fernanda.trabalho1.ui.PessoaActivity.PESSOA_KEY;

public class MainActivity extends AppCompatActivity {

    private static final int CADASTRO_PESSOA_REQUEST_CODE = 10;

    private ListView lvPessoas;

    private List<Pessoa> pessoas;

    private List<Livro> livros;

    private List<Reserva> reservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularDados();
        setupListView();
        setupButtons();
    }

    private void popularDados() {
        pessoas = new ArrayList<>(Arrays.asList(new Pessoa("Gabriel", "gabriel@a.b"),
                new Pessoa("Fernanda", "fernanda@a.b"), new Pessoa("Thassya", "thassya@a.b")));
        livros = new ArrayList<>(Arrays.asList(new Livro("Harry Potter", "Rocco", 1996),
                new Livro("Game of Thrones", "Leya", 2005),
                new Livro("Percy Jackson", "Abril", 2002)));
        reservas = new ArrayList<>();
    }

    private void setupListView(){
        lvPessoas = (ListView) findViewById(R.id.lv_pessoas);
        final ListAdapter pessoasAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,pessoas);
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
                startActivityForResult(intent, CADASTRO_PESSOA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == CADASTRO_PESSOA_REQUEST_CODE){
                Pessoa pessoa = data.getParcelableExtra(CADASTRO_PESSOA_KEY);
                pessoas.add(pessoa);
                ((BaseAdapter) lvPessoas.getAdapter()).notifyDataSetChanged();
            }
        }
    }
}
