package br.com.urbainski.escola.aplicacao.aluno.matricular;

import br.com.urbainski.escola.dominio.aluno.Aluno;
import br.com.urbainski.escola.dominio.aluno.builder.AlunoBuilder;

public class MatricularAlunoDTO {

    private final String nome;
    private final String cpf;
    private final String email;

    public MatricularAlunoDTO(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Aluno criarAluno() {
        return AlunoBuilder.withoutId()
                .withNome(this.nome)
                .withCpf(this.cpf)
                .withEmail(this.email)
                .build();
    }

}
