package br.com.urbainski.escola.dominio.aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Aluno {

    private Integer id;
    private final String nome;
    private final CPF cpf;
    private final Email email;
    private final List<Telefone> telelefones;

    public Aluno(Integer id, String nome, CPF cpf, Email email, List<Telefone> telelefones) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telelefones = Objects.requireNonNullElse(telelefones, new ArrayList<>());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public CPF getCpf() {
        return cpf;
    }

    public Email getEmail() {
        return email;
    }

    public List<Telefone> getTelelefones() {
        return telelefones;
    }

}
