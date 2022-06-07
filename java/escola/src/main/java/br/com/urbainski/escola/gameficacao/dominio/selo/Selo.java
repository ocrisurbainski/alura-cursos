package br.com.urbainski.escola.gameficacao.dominio.selo;

import br.com.urbainski.escola.shared.dominio.CPF;

public class Selo {

    private Integer id;
    private final String nome;
    private final CPF cpf;

    public Selo(String nome, CPF cpf) {
        this(null, nome, cpf);
    }

    public Selo(Integer id, String nome, CPF cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
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

}
