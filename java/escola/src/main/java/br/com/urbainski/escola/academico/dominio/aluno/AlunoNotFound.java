package br.com.urbainski.escola.academico.dominio.aluno;

import br.com.urbainski.escola.shared.dominio.CPF;

public class AlunoNotFound extends RuntimeException {

    public AlunoNotFound(CPF cpf) {
        super(String.format("Aluno não encontrado com CPF: %s", cpf.getNumero()));
    }

}
