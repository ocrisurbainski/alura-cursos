package br.com.urbainski.escola.dominio.aluno.builder;

import br.com.urbainski.escola.dominio.aluno.Aluno;
import br.com.urbainski.escola.dominio.aluno.CPF;
import br.com.urbainski.escola.dominio.aluno.Email;
import br.com.urbainski.escola.dominio.aluno.Telefone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlunoFluentBuilder implements
        AlunoBuilder.AlunoCpfBuilder,
        AlunoBuilder.AlunoEmailBuilder,
        AlunoBuilder.AlunoTelefoneBuilder {

    private final String nome;
    private final List<Telefone> telefones;
    private CPF cpf;
    private Email email;

    public AlunoFluentBuilder(String nome) {
        Objects.requireNonNull(nome, "Nome deve ser informado");
        this.nome = nome;
        this.telefones = new ArrayList<>();
    }

    @Override
    public AlunoBuilder.AlunoEmailBuilder withCpf(String cpf) {
        Objects.requireNonNull(cpf, "CPF deve ser informado");
        this.cpf = new CPF(cpf);
        return this;
    }

    @Override
    public AlunoBuilder.AlunoTelefoneBuilder withEmail(String email) {
        Objects.requireNonNull(email, "Email deve ser informado");
        this.email = new Email(email);
        return this;
    }

    @Override
    public AlunoBuilder.AlunoTelefoneBuilder withTelefone(String ddd, String telefone) {
        Objects.requireNonNull(ddd, "DDD deve ser informado");
        Objects.requireNonNull(telefone, "Número telefone deve ser informado");
        this.telefones.add(new Telefone(ddd, telefone));
        return this;
    }

    @Override
    public Aluno build() {
        return new Aluno(this.nome, this.cpf, this.email, this.telefones);
    }

}
