package br.com.urbainski.escola;

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
