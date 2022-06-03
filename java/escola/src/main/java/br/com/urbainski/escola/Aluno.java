package br.com.urbainski.escola;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Aluno {

    private final String nome;
    private final CPF cpf;
    private final Email email;
    private final List<Telefone> telelefones;

    public Aluno(String nome, String cpf, String email, List<Telefone> telelefones) {
        this.nome = nome;
        this.cpf = new CPF(cpf);
        this.email = new Email(email);
        this.telelefones = Objects.requireNonNullElse(telelefones, new ArrayList<>());
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

    public void addTelefone(String ddd, String numero) {
        this.telelefones.add(new Telefone(ddd, numero));
    }
}
