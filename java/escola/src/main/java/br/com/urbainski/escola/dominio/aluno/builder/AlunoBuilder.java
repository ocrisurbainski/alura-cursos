package br.com.urbainski.escola.dominio.aluno.builder;

import br.com.urbainski.escola.dominio.aluno.Aluno;
import br.com.urbainski.escola.dominio.aluno.Telefone;

import java.util.List;

public interface AlunoBuilder {

    interface AlunoNomeBuilder {
        AlunoCpfBuilder withNome(String nome);
    }

    interface AlunoCpfBuilder {
        AlunoEmailBuilder withCpf(String cpf);
    }

    interface AlunoEmailBuilder {
        AlunoTelefoneBuilder withEmail(String email);
    }

    interface AlunoTelefoneBuilder {
        AlunoTelefoneBuilder withTelefone(String ddd, String telefone);

        AlunoTelefoneBuilder withTelefone(Integer id, String ddd, String telefone);

        AlunoTelefoneBuilder withTelefones(List<Telefone> telefones);

        Aluno build();
    }

    static AlunoNomeBuilder withId(Integer id) {
        return new AlunoFluentBuilder(id);
    }

    static AlunoNomeBuilder withoutId() {
        return new AlunoFluentBuilder(null);
    }

}
