package br.com.urbainski.escola.dominio.aluno;

public class AlunoNotFound extends RuntimeException {

    public AlunoNotFound(CPF cpf) {
        super(String.format("Aluno não encontrado com CPF: %s", cpf.getNumero()));
    }

}
