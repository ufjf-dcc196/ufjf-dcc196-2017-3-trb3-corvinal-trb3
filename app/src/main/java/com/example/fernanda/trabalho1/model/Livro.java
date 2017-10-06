package com.example.fernanda.trabalho1.model;

public class Livro {
    private final String titulo;
    private final String editora;
    private final int anoPublicacao;

    public Livro(String titulo, String editora, int anoPublicacao) {
        this.titulo = titulo;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditora() {
        return editora;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }
}
