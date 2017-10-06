package com.example.fernanda.trabalho1.model;

public class Reserva {
    private final Pessoa pessoa;
    private final Livro livro;

    public Reserva(Pessoa pessoa, Livro livro) {
        this.pessoa = pessoa;
        this.livro = livro;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Livro getLivro() {
        return livro;
    }
}
