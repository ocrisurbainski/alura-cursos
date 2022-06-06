package br.com.urbainski.escola.dominio.aluno.builder;

import br.com.urbainski.escola.dominio.aluno.Aluno;
import br.com.urbainski.escola.dominio.aluno.CPF;
import br.com.urbainski.escola.dominio.aluno.Email;
import br.com.urbainski.escola.dominio.aluno.Telefone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlunoFluentBuilder implements
        AlunoBuilder.AlunoNomeBuilder,
        AlunoBuilder.AlunoCpfBuilder,
        AlunoBuilder.AlunoEmailBuilder,
        AlunoBuilder.AlunoTelefoneBuilder {

    private final Integer idAluno;
    private final List<Telefone> telefones;
    private String nome;
    private CPF cpf;
    private Email email;

    public AlunoFluentBuilder(Integer id) {
        this.idAluno = id;
        this.telefones = new ArrayList<>();
    }

    @Override
    public AlunoBuilder.AlunoCpfBuilder withNome(String nome) {
        Objects.requireNonNull(nome, "Nome deve ser informado");
        this.nome = nome;
        return this;
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
        return withTelefone(null, ddd, telefone);
    }

    @Override
    public AlunoBuilder.AlunoTelefoneBuilder withTelefone(Integer id, String ddd, String telefone) {
        Objects.requireNonNull(ddd, "DDD deve ser informado");
        Objects.requireNonNull(telefone, "Número telefone deve ser informado");
        this.telefones.add(new Telefone(id, idAluno, ddd, telefone));
        return this;
    }

    @Override
    public AlunoBuilder.AlunoTelefoneBuilder withTelefones(List<Telefone> telefones) {
        Objects.requireNonNull(telefones, "Telefones deve ser informado");
        this.telefones.addAll(telefones);
        return this;
    }

    @Override
    public Aluno build() {
        return new Aluno(this.idAluno, this.nome, this.cpf, this.email, this.telefones);
    }

}
