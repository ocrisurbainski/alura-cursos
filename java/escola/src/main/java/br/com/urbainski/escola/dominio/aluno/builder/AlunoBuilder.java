package br.com.urbainski.escola.dominio.aluno.builder;

import br.com.urbainski.escola.dominio.aluno.Aluno;

public interface AlunoBuilder {

    interface AlunoCpfBuilder {
        AlunoEmailBuilder withCpf(String cpf);
    }

    interface AlunoEmailBuilder {
        AlunoTelefoneBuilder withEmail(String email);
    }

    interface AlunoTelefoneBuilder {
        AlunoTelefoneBuilder withTelefone(String ddd, String telefone);
        Aluno build();
    }

    static AlunoCpfBuilder withNome(String nome) {
        return new AlunoFluentBuilder(nome);
    }

}
