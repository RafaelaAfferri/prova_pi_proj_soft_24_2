package br.insper.provapi;


import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class Projeto {
    @Id
    private String id;

    private String nome;

    private String descricao;

    private String status;

    private String gerente;

    private ArrayList<String> pessoas = new ArrayList<>();

    public Projeto() {
    }

    public Projeto(String id, String nome, String descricao, String status, String gerente, ArrayList<String> pessoas) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.gerente = gerente;
        this.pessoas = pessoas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public ArrayList<String> getPessoas() {
        return pessoas;
    }

    public void setPessoas(ArrayList<String> pessoas) {
        this.pessoas = pessoas;
    }
}
